<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map, java.text.NumberFormat, java.util.Locale, model.ADM" %>
<%

List<ADM> resultadosValidos = (List<ADM>) request.getAttribute("resultadosValidos");
Map<String, Integer> totaisBcosNulos = (Map<String, Integer>) request.getAttribute("totaisBcosNulos");

int totalVotosValidos = (Integer) request.getAttribute("totalVotosValidos");
int totalVotosBrancos = (Integer) request.getAttribute("totalVotosBrancos");
int totalVotosNulos = (Integer) request.getAttribute("totalVotosNulos");
int totalVotos = (Integer) request.getAttribute("totalVotos");
NumberFormat formatoPercentual = NumberFormat.getPercentInstance(new Locale("pt", "BR"));
formatoPercentual.setMinimumFractionDigits(2);
formatoPercentual.setMaximumFractionDigits(2);

%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<title>Resultados da votação</title>

</head>

<body>

	<nav class="navegacao" aria-label="Navegação">
		<a href="<%= request.getContextPath() %>/router?controller=ADM&acao=logout_adm">Sair</a>
	</nav>

	<main class="pagina-resultados">
		<h1>Resultados da votação</h1>

		<section class="resumo-resultados" aria-label="Resumo">
			<p>
				Votos válidos: <strong><%= totalVotosValidos %></strong>
			</p>
			<p>
				Votos brancos: <strong><%= totalVotosBrancos %></strong>
			</p>
			<p>
				Votos nulos: <strong><%= totalVotosNulos %></strong>
			</p>
			<p>
				Total de votos registrados: <strong><%= totalVotos %></strong>
			</p>
		</section>

		<table class="tabela-resultados">
			<thead>
				<tr>
					<th>Número</th>
					<th>Candidato / Tipo</th>
					<th>Quantidade</th>
					<th>Percentual dos votos válidos</th>
				</tr>
			</thead>

			<tbody>
				<% if (resultadosValidos != null) { %>
					<% for (ADM adm : resultadosValidos) { %>
					<tr>
						<td><%= adm.getNumero_voto() %></td>
						<td><%= adm.getNome_candidato() %></td>
						<td><%= adm.getTotal_votos_validos() %></td>
						<td><%= formatoPercentual.format(totalVotosValidos == 0 ? 0 : adm.getTotal_votos_validos() / (double) totalVotosValidos) %></td>
					</tr>
					<% } %>
				<% } %>

			</tbody>
		</table>
	</main>

</body>
</html>