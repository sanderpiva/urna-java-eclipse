const btn1 = document.getElementById('btn-1');
const btn2 = document.getElementById('btn-2');
const btn3 = document.getElementById('btn-3');
const btn4 = document.getElementById('btn-4');
const btn5 = document.getElementById('btn-5');
const btn6 = document.getElementById('btn-6');
const btn7 = document.getElementById('btn-7');
const btn8 = document.getElementById('btn-8');
const btn9 = document.getElementById('btn-9');
const btn0 = document.getElementById('btn-0');
const btnbranco = document.getElementById('btn-branco');
const btncorrigir = document.getElementById('btn-corrigir');
const btnconfirmar = document.getElementById('btn-confirmar');
const conteudo = document.getElementById('conteudo');
const lista = [];

function handleButtonClick(value) {
	
	conteudo.classList.add('sem-fundo');
	
    if (value === 'branco') {
        lista.length = 0;
        lista.push('branco');
        conteudo.value = 'branco';
        return;
    }

    if (lista[0] === 'branco') {
        resetInput();
    }

    if (lista.length >= 2) {
        alert('Voto com 2 digitos!');
        resetInput();
        return;
    }

    lista.push(value);
    conteudo.value = lista.join('');
}

function resetInput() {
    lista.length = 0;
    conteudo.value = '';
    conteudo.classList.remove('sem-fundo');
}


btn1.addEventListener('click', () => {
    handleButtonClick('1');
});
btn2.addEventListener('click', () => {
    handleButtonClick('2');
});
btn3.addEventListener('click', () => {
    handleButtonClick('3');
});
btn4.addEventListener('click', () => {
    handleButtonClick('4');
});
btn5.addEventListener('click', () => {
    handleButtonClick('5');
});
btn6.addEventListener('click', () => {
    handleButtonClick('6');
});
btn7.addEventListener('click', () => {
    handleButtonClick('7');
});
btn8.addEventListener('click', () => {
    handleButtonClick('8');
});
btn9.addEventListener('click', () => {
    handleButtonClick('9');
});
btn0.addEventListener('click', () => {
    handleButtonClick('0');
});

btnbranco.addEventListener('click', () => {   
    handleButtonClick('branco');
});

btncorrigir.addEventListener('click', () => {   
    resetInput();
    conteudo.classList.remove('sem-fundo');
});


btnconfirmar.addEventListener('click', function () {
    const numeroVoto = conteudo.value.trim();

    if (!numeroVoto) {
        alert('Você não digitou nenhum número. Por favor, digite o número do candidato ou pressione "Branco" para votar em branco.');
        return; // Interrompe a execução antes do fetch
    }

    btnconfirmar.disabled = true;

    fetch(document.body.dataset.voteUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: new URLSearchParams({ numero_voto: numeroVoto })
    })
    .then(function (resposta) {
        return resposta.json().then(function (resultado) {
            return { ok: resposta.ok, status: resposta.status, resultado: resultado };
        });
    })
    .then(function (dados) {
        const resposta = dados;
        const resultado = dados.resultado;

        if (!resposta.ok) {
            alert(resultado.erro || 'Voto inválido.');

            if (resposta.status === 401 || resposta.status === 403) {
                window.location.assign(document.body.dataset.homeUrl);
                return;
            }
            btnconfirmar.disabled = false;
            return;
        }

        if (resultado.tipo === 'branco') {
            alert('Voto em branco registrado.');
        } else if (resultado.tipo === 'nulo') {
            alert('Voto nulo registrado.');
        } else {
            alert('Voto válido registrado para ' + resultado.candidato + '.');
        }

        window.setTimeout(function () {
            window.location.assign(document.body.dataset.homeUrl);
        }, 2000);
    })
    .catch(function (erro) {
        console.error('Erro na requisição:', erro);
        alert('Não foi possível registrar o voto. Tente novamente.');
        btnconfirmar.disabled = false;
    });
});