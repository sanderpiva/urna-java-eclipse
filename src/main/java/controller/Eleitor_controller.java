package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Eleitor_model;

public class Eleitor_controller {
	private final Eleitor_model eleitorModel = new Eleitor_model();

	public void view_eleitor(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("pagina eleitor");
		request.getRequestDispatcher("/view/view_inicio_eleitor.jsp").forward(request, response);
	}

	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		impedirCache(response);
		if (!"GET".equals(request.getMethod()) && !"POST".equals(request.getMethod())) {
			response.setHeader("Allow", "GET, POST");
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		HttpSession sessao = request.getSession(false);
		if (sessao != null && sessao.getAttribute("eleitor_id") != null) {
			// Ao retornar ao login, encerra a sessão e direciona o eleitor ao início.
			sessao.invalidate();
			response.sendRedirect(request.getContextPath() + "/router?controller=Router&acao=index");
			return;
		}

		if ("POST".equals(request.getMethod())) {
			System.out.println("[DEBUG LOGIN] Requisição POST recebida no Controller!");
			System.out.println("[DEBUG LOGIN] Nome: " + valor(request, "nome"));
			System.out.println("[DEBUG LOGIN] Data: " + valor(request, "data"));
			System.out.println("[DEBUG LOGIN] CPF: " + valor(request, "cpf"));

			int idEleitor = eleitorModel.autenticarEleitor(
					valor(request, "nome"), 
					valor(request, "data"),
					valor(request, "zona"), 
					valor(request, "municipio"), 
					valor(request, "cpf"),
					valor(request, "secao"), 
					valor(request, "filiacao")
					);

			System.out.println("[DEBUG LOGIN] Retorno do autenticarEleitor: " + idEleitor);

			if (idEleitor > 0) {
				System.out.println("[DEBUG LOGIN] Login com SUCESSO! Redirecionando para a urna...");
				request.getSession(true).setAttribute("eleitor_id", idEleitor);
				request.changeSessionId();
				response.sendRedirect(request.getContextPath() + "/router?controller=Eleitor&acao=votar");
				return;
			}

			System.out.println("[DEBUG LOGIN] Falha de autenticação. Exibindo mensagem de dados inválidos.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.setAttribute("erro", "Dados inválidos ou eleitor já votou.");
		}

		request.getRequestDispatcher("/view/view_login_eleitor.jsp").forward(request, response);
	}

	public void votar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		impedirCache(response);
		if (!"GET".equals(request.getMethod())) {
			response.setHeader("Allow", "GET");
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		HttpSession sessao = request.getSession(false);
		Object id = sessao == null ? null : sessao.getAttribute("eleitor_id");
		if (!(id instanceof Integer)) {
			response.sendRedirect(request.getContextPath() + "/router?controller=Eleitor&acao=view_login_eleitor");
			return;
		}

		try {
			if (!eleitorModel.eleitorPodeVotar((Integer) id)) {
				sessao.invalidate();
				response.sendRedirect(request.getContextPath() + "/router?controller=Eleitor&acao=view_login_eleitor");
				return;
			}
		} catch (SQLException e) {
			request.getServletContext().log("Erro ao validar eleitor", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Não foi possível validar o acesso à urna.");
			return;
		}

		request.getRequestDispatcher("/view/view_urna.jsp").forward(request, response);
	}

	public void registrarVoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!"POST".equals(request.getMethod())) {
			response.setHeader("Allow", "POST");
			responderJson(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método não permitido.");
			return;
		}

		HttpSession sessao = request.getSession(false);
		Object id = sessao == null ? null : sessao.getAttribute("eleitor_id");
		if (!(id instanceof Integer)) {
			responderJson(response, HttpServletResponse.SC_UNAUTHORIZED,
					"Acesso não autorizado. Faça login como eleitor.");
			return;
		}

		StringBuilder buffer = new StringBuilder();
		String linha;
		try (java.io.BufferedReader reader = request.getReader()) {
			while ((linha = reader.readLine()) != null) {
				buffer.append(linha);
			}
		}

		String corpo = buffer.toString();
		String numeroVoto = "";

		if (corpo.contains("\"numero_voto\"")) {
			numeroVoto = corpo.replaceAll(".*\"numero_voto\"\\s*:\\s*\"?([^\"\\}\\,]+)\"?.*", "$1").trim();
		} else {
			numeroVoto = valor(request, "numero_voto");
		}

		if (!"branco".equals(numeroVoto) && !numeroVoto.matches("\\d{2}")) {
			responderJson(response, HttpServletResponse.SC_BAD_REQUEST, "Número de voto inválido.");
			return;
		}

		try {
			Map<String, String> voto = eleitorModel.registrarVoto(numeroVoto, (Integer) id);
			if (voto == null) {
				sessao.invalidate();
				responderJson(response, HttpServletResponse.SC_FORBIDDEN,
						"Este eleitor já votou ou não está cadastrado.");
				return;
			}

			sessao.invalidate();
			String candidato = voto.containsKey("candidato")
					? "\"" + escaparJson(voto.get("candidato")) + "\"" : "null";
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"tipo\":\"" + escaparJson(voto.get("tipo"))
			+ "\",\"candidato\":" + candidato + "}");
		} catch (SQLException e) {
			request.getServletContext().log("Erro ao registrar voto", e);
			responderJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Não foi possível registrar o voto.");
		}
	}

	private String valor(HttpServletRequest request, String nome) {
		String valor = request.getParameter(nome);
		return valor == null ? "" : valor.trim();
	}

	private void impedirCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	private String escaparJson(String valor) {
		return valor.replace("\\", "\\\\").replace("\"", "\\\"")
				.replace("\n", "\\n").replace("\r", "\\r");
	}

	private void responderJson(HttpServletResponse response, int status, String erro) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"erro\":\"" + escaparJson(erro) + "\"}");
	}

}

