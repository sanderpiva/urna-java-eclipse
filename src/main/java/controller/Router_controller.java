package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/router")
public class Router_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String controller = request.getParameter("controller");
		String acao = request.getParameter("acao");
		if (controller == null || acao == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

		switch (controller) {
		case "Router":
			if ("index".equals(acao) || "view_selecao".equals(acao)) {
				HttpSession sessao = request.getSession(false);
				// Acessar o início encerra as sessões de eleitor e administrador.
				if (sessao != null && (sessao.getAttribute("eleitor_id") != null
						|| sessao.getAttribute("admin_autenticado") != null)) {
					sessao.invalidate();
				}
				response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				request.getRequestDispatcher("/view/view_selecao.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			break;
		case "Eleitor":
			Eleitor_controller eleitorController = new Eleitor_controller();
			switch (acao) {
			case "view_inicio_eleitor":
				eleitorController.view_eleitor(request, response);
				break;
			case "view_login_eleitor":
			case "login":
				eleitorController.login(request, response);
				break;
			case "votar":
				eleitorController.votar(request, response);
				break;
			case "registrar-voto":
				eleitorController.registrarVoto(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			break;
		case "ADM":
			ADM_controller admController = new ADM_controller();
			switch (acao) {
			case "view_inicio_adm":
				admController.view_adm(request, response);
				break;
			case "view_login_adm":
			case "login":
				admController.login(request, response);
				break;
			case "listar":
				admController.listar(request, response);
				break;
			case "logout_adm":
				admController.logout_adm(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String controller = request.getParameter("controller");
		String acao = request.getParameter("acao");
		if (controller == null || acao == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

		switch (controller) {
		case "Eleitor":
			Eleitor_controller eleitorController = new Eleitor_controller();
			if ("view_login_eleitor".equals(acao) || "login".equals(acao)) {
				eleitorController.login(request, response);
			} else if ("registrar-voto".equals(acao)) {
				eleitorController.registrarVoto(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			break;
		case "ADM":
			ADM_controller admController = new ADM_controller();
			if ("view_login_adm".equals(acao) || "login".equals(acao)) {
				admController.login(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

}




