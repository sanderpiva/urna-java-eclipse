<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<title>Login eleitor</title>
</head>
<body>

<main class="pagina-login">
        <h1>Login do eleitor</h1>
        <% if (request.getAttribute("erro") != null) { %>
            <p class="erro-login"><%= request.getAttribute("erro") %></p>
        <% } %>
        
        
        
        
        <form class="form-login" method="post" action="router?controller=Eleitor&acao=view_login_eleitor">
            <label for="nome">Nome eleitor</label>
            <input id="nome" name="nome" type="text" autocomplete="username" required>

            <label for="data">Data de nascimento</label>
            <input id="data" name="data" type="date" required>

            <label for="zona">Zona eleitoral</label>
            <input id="zona" name="zona" type="text" autocomplete="username" required>

            <label for="municipio">Município</label>
            <input id="municipio" name="municipio" placeholder="Cidade/UF" type="text" autocomplete="username" required>

            <label for="cpf">CPF</label>
            <input id="cpf" name="cpf" type="text"  placeholder="xxxxxxxxxxx" autocomplete="username" required>

            <label for="secao">Seção</label>
            <input id="secao" name="secao" type="text" autocomplete="username" required>

            <label for="filiacao">Filiação</label>
            <input id="filiacao" name="filiacao" type="text" placeholder="Nome pai - Nome mãe" required>

            <button type="submit">Entrar</button>
        </form>
        
        
        <a href="<%= request.getContextPath() %>/router?controller=Router&acao=index">Início</a>
        	
        
    </main>

</body>
</html>