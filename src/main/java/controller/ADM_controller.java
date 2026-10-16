package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ADM;
import model.ADM_model;

public class ADM_controller {
	private final ADM_model admModel = new ADM_model();


	public void view_adm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("pagina adm");
		request.getRequestDispatcher("/router?controller=ADM&acao=login").forward(request, response);
	}	

	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"GET".equals(request.getMethod()) && !"POST".equals(request.getMethod())) {
			response.setHeader("Allow", "GET, POST");
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		HttpSession sessao = request.getSession(false);

		if (sessao != null && Boolean.TRUE.equals(sessao.getAttribute("admin_autenticado"))) {
			response.sendRedirect(request.getContextPath() + "/view/view_inicio_adm.jsp");
			return;
		}

		if ("POST".equals(request.getMethod())) {
			String nome = request.getParameter("nome");
			String senha = request.getParameter("senha");
			if (ADM.getNome_adm().equals(nome == null ? "" : nome.trim())
					&& ADM.getSenha_adm().equals(senha == null ? "" : senha)) {
				request.getSession(true).setAttribute("admin_autenticado", Boolean.TRUE);
				request.changeSessionId();
				response.sendRedirect(request.getContextPath() + "/router?controller=ADM&acao=listar");
				return;
			}
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.setAttribute("erro", "Nome ou senha inválidos.");
		}

		request.getRequestDispatcher("/view/view_login_adm.jsp").forward(request, response);
	}

	public void listar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!"GET".equals(request.getMethod())) {
			response.setHeader("Allow", "GET");
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		HttpSession sessao = request.getSession(false);
		if (sessao == null || !Boolean.TRUE.equals(sessao.getAttribute("admin_autenticado"))) {
			response.sendRedirect(request.getContextPath() + "/router?controller=ADM&acao=view_login_adm");
			return;
		}

		try {
			List<ADM> resultadosValidos = admModel.listarResultadosValidos();
			int totalVotosValidos = resultadosValidos.stream().mapToInt(ADM::getTotal_votos_validos).sum();

			Map<String, Integer> totais = admModel.listarBcosNulos();

			int totalVotos = admModel.contarVotosRegistrados();

			request.setAttribute("resultadosValidos", resultadosValidos);
			request.setAttribute("totalVotosValidos", totalVotosValidos);
			request.setAttribute("totalVotosBrancos", totais.get("totalVotosBrancos"));
			request.setAttribute("totalVotosNulos", totais.get("totalVotosNulos"));
			request.setAttribute("totalVotos", totalVotos);
			request.getRequestDispatcher("/view/view_listar.jsp").forward(request, response);

		} catch (SQLException e) {
			request.getServletContext().log("Erro ao consultar resultados", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Não foi possível carregar os resultados.");
		}
	}

	public void logout_adm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession sessao = request.getSession(false);
		if (sessao != null) {
			sessao.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/router?controller=Router&acao=index");
	}

}
