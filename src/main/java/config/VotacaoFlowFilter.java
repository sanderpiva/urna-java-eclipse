package config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class VotacaoFlowFilter implements Filter {
	@Override
	public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession sessao = httpRequest.getSession(false);

		if (sessao == null || sessao.getAttribute("eleitor_id") == null) {
			chain.doFilter(request, response);
			return;
		}

		String caminho = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (caminho.startsWith("/css/") || caminho.startsWith("/js/") || caminho.startsWith("/img/")) {
			chain.doFilter(request, response);
			return;
		}

		boolean rotaUrna = "/router".equals(caminho)
				&& "GET".equals(httpRequest.getMethod())
				&& "Eleitor".equals(httpRequest.getParameter("controller"))
				&& "votar".equals(httpRequest.getParameter("acao"));
		boolean registrarVoto = "/router".equals(caminho)
				&& "POST".equals(httpRequest.getMethod())
				&& "Eleitor".equals(httpRequest.getParameter("controller"))
				&& "registrar-voto".equals(httpRequest.getParameter("acao"));

		if (rotaUrna || registrarVoto) {
			chain.doFilter(request, response);
			return;
		}

		httpResponse.sendRedirect(httpRequest.getContextPath()
				+ "/router?controller=Eleitor&acao=votar");
	}
}