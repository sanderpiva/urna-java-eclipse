<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<title>Selecione Perfil: ADM</title>

</head>

<body class="pagina-selecao">

    <main class="container-selecao">
        
        <h2>Bem-vindo ao Sistema</h2>
        <p>Como você deseja acessar?</p>
        <br>
        <a href="<%= request.getContextPath() %>/router?controller=Eleitor&acao=view_inicio_eleitor" class="btn-selecao-eleitor">Sou Eleitor</a>
        <a href="<%= request.getContextPath() %>/router?controller=ADM&acao=view_inicio_adm" class="btn-selecao-adm">Sou Administrador</a>        
    </main>

</body>
</html>

