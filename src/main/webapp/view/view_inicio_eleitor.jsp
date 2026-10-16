<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<title>Sistema Urna eletrônica</title>

</head>

<body>

    <main class="pagina-inicial">
        <h1>Urna eletrônica</h1>
        <nav class="opcoes-inicio" aria-label="Navegação principal">
            <a href="<%= request.getContextPath() %>/router?controller=Eleitor&acao=view_login_eleitor">Votar</a>
	        <a href="<%= request.getContextPath() %>/router?controller=ADM&acao=index">Sair</a>
        </nav>
    </main>

</body>
</html>
