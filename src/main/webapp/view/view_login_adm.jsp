<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<title>Acesso administrativo</title>
</head>
<body>

<main class="pagina-login">
        <h1>Acesso administrativo</h1>
        <% if (request.getAttribute("erro") != null) { %>
            <p class="erro-login"><%= request.getAttribute("erro") %></p>
        <% } %>
        
        <form class="form-login" method="post" action="router?controller=ADM&acao=view_login_adm">
            <label for="nome">Nome</label>
            <input id="nome" name="nome" type="text" autocomplete="username" required>

            <label for="senha">Senha</label>
            <input id="senha" name="senha" type="password" autocomplete="current-password" required>

            <button type="submit">Entrar</button>
        </form>
        
        
        <a href="<%= request.getContextPath() %>/router?controller=Router&acao=index">Início</a>

</main>


</body>
</html>