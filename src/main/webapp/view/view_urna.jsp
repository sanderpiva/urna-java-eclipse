<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<script src="<%= request.getContextPath() %>/js/script.js" defer></script>

<title>Urna</title>

</head>
<body data-vote-url="<%= request.getContextPath() %>/router?controller=Eleitor&acao=registrar-voto"
    data-home-url="<%= request.getContextPath() %>/router?controller=Router&acao=index">

<div class="master-container">

        <main class="container">

            <input type="text" id="conteudo" readonly aria-live="polite">
            
            <div class="btns-1-9">

             	<img src="img/1.jpg" alt="Urna" class="urna-img1">
             
                <button id="btn-1" type="button">1</button>
                <button id="btn-2" type="button">2</button>
                <button id="btn-3" type="button">3</button>
                <button id="btn-4" type="button">4</button>
                <button id="btn-5" type="button">5</button>
                <button id="btn-6" type="button">6</button>
                <button id="btn-7" type="button">7</button>
                <button id="btn-8" type="button">8</button>
                <button id="btn-9" type="button">9</button>
                <span aria-hidden="true"></span>
                <button id="btn-0" type="button">0</button>
                <span aria-hidden="true"></span>
                <button type="button" id="btn-branco">BRANCO</button>
                <button type="button" id="btn-corrigir">CORRIGIR</button>
                <button type="button" id="btn-confirmar">CONFIRMAR</button>
                
            </div>

        </main>

    </div>

</body>
</html>