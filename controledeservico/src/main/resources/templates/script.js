const url_OS = "http://localhost:8080/ordensdeservicos"
const url_Funcionario = "http://localhost:8080/funcionarios"

function novaOrdem(modal){
    modal.showModal()
    getAll_Funcionarios()
}

function fechar(modal){
    modal.close()
}



var controle = 1
var formsArray = []
async function abrirFolhaOrçamento(modal) {

    modal.onclose = function(){
        document.getElementById("adicionarProduto").removeEventListener("click", novoCampo)
    }

    const response = await fetch("http://localhost:8080/estoque");
    const avaiableProducts = await response.json();

    modal.showModal();

    novoCampo()

    function novoCampo() {
        var novoFormId = controle + 1
        var novoForm = document.createElement("form")

        novoForm.id = `${novoFormId}`
        novoForm.innerHTML = `
            <input id="produto${novoFormId}" placeholder="produto" disabled>
            <input id="quantidade${novoFormId}" placeholder="quantidade" type="number" disabled>
            <input id="precoUnitario${novoFormId}" placeholder="Preço unitário" disabled>
            <input id="referencia${novoFormId}" placeholder="Referência" disabled>
        `
        

        var novoSelectId = controle
        var novoSelect = document.createElement("select")
        novoSelect.id = novoSelectId

        avaiableProducts.forEach(element => {
            novoSelect.innerHTML += `
                <option value="${element.id_produto}">${element.produto} - ${element.quantidade} Unidades</option>
            `
        })

        novoSelect.innerHTML += "<option value='outro'>Outro</option>"

        novoSelect.addEventListener("change", function () {
            var value = novoSelect.value
            var campoNome = novoForm.querySelector(`#produto${novoFormId}`)
            var campoQuantidade = novoForm.querySelector(`#quantidade${novoFormId}`)
            var campoPrecoUnitario = novoForm.querySelector(`#precoUnitario${novoFormId}`)
            var campoReferencia = novoForm.querySelector(`#referencia${novoFormId}`)

            if (value == "outro") {
                novoForm.querySelector(`#produto${novoFormId}`).removeAttribute("disabled")
                novoForm.querySelector(`#quantidade${novoFormId}`).removeAttribute("disabled")
                novoForm.querySelector(`#precoUnitario${novoFormId}`).removeAttribute("disabled")
                novoForm.querySelector(`#referencia${novoFormId}`).removeAttribute("disabled")

                campoNome.value = ""
                campoQuantidade.value = ""
                campoPrecoUnitario.value = ""
                campoReferencia.value = ""
            } else {
                campoNome.setAttribute("disabled", true)
                campoQuantidade.setAttribute("disabled", true)
                campoPrecoUnitario.setAttribute("disabled", true)
                campoReferencia.setAttribute("disabled", true)

                var produtoSelecionado

                avaiableProducts.forEach(element => {
                    if (value == element.id_produto){
                        produtoSelecionado = element
                    }
                })
                
                campoNome.value = produtoSelecionado.produto
                campoQuantidade.value = produtoSelecionado.quantidade
                campoPrecoUnitario.value = formatarMoeda(produtoSelecionado.precoUnitario)
                campoReferencia.value = produtoSelecionado.referencia

            }
        });

        document.getElementById("camposOrcamento").appendChild(novoForm);
        document.getElementById("camposOrcamento").appendChild(novoSelect);

        controle++
        formsArray.push(novoForm)
    }

    document.getElementById("adicionarProduto").addEventListener("click", novoCampo)
    

}


window.addEventListener("load", function() {
    
    const novaOrdemModal = document.getElementById("novaOrdemModal")
    const form = document.querySelector("form");

    const tbody = document.querySelector("tbody")
    
    tbody.addEventListener("dblclick", function(event){
        const row = event.target.closest("tr")
        var os_id = row.cells[0].innerText
        
        getById_OS(os_id)
    })

    novaOrdemModal.onclose = function(){
        document.getElementById("camposOrcamento").innerHTML = ""
        
        controle = 1
        formsArray = []
        const inputs = document.querySelectorAll("input")
        
        inputs.forEach(element => {
            element.value = ""
        })
    }



    /* form.addEventListener('submit', function(event) {
        event.preventDefault(); 

        const os = {
            nome: document.getElementById('nome').value,
            cpf: document.getElementById('cpf').value,
            endereco: document.getElementById('endereco').value,
            telefone: document.getElementById('telefone').value,
            dataSaida: document.getElementById('dataSaida').value,
            produto: document.getElementById('produto').value,
            numeroSerie: document.getElementById('numeroSerie').value,
            servico: document.getElementById('servico').value,
            obs: document.getElementById('obs').value,
            funcionario_id: document.getElementById("funcionarioBox").value,
            coments: null,
        };

        post_OS(os)

        modal.close()
    }) */

    getAll_OS()

})


async function getAll_Funcionarios(){
    try {
        const response = await fetch(url_Funcionario)
        const data = await response.json()

        const combobox = document.getElementById("funcionarioBox")

        combobox.innerHTML = ""

        data.forEach(element => {
            var funcionario = `<option value="${element.id}">${element.nome}</option>`
            combobox.innerHTML += funcionario
        })


    } catch (error) {
        console.error("Erro durante a requisição:", error)
    }
}
async function getAll_OS() {
    try {
        const response = await fetch(url_OS)
        const data = await response.json()

        updateTable(data)
    } catch (error) {
        console.error("Erro durante a requisição:", error);
    }
}
async function getById_OS(os_id) {
    try {
        const response = await fetch(url_OS + `/${os_id}`)
        const data = await response.json()
        
        showOS(data)
    } catch (error) {
        console.error("Erro durante a requisição:", error);
    }
}
async function post_OS(os) {
    console.log(os.dataSaida)
    try{
        const response = await fetch(url_OS, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(os)
        })

        console.log(response.status)
    } catch (error) {
        console.error(error)
    }
}
function updateTable(list){
    const table = document.querySelector("tbody");

    table.innerHTML = ""

    list.forEach(element => {
        var row = `<tr>\
                    <td>${element.os}</td>\
                    <td>${element.nome}</td>\
                    <td>${element.produto}</td>\
                    <td>${element.dataSaida}</td>\
                    <td>${element.servico}</td>\
                    <td>${element.situacao}</td>\
                    <td>${element.funcionario_id.nome}</td>\
                </td>`
        
        table.innerHTML += row;

    });
}
function showOS(os_data){
    const os_selecionada = document.getElementById("os_selecionada")
    const div_data = document.getElementById("data_div")

    console.log(os_data)

    div_data.innerHTML = ""

    var data = `<div class="infos"><h2>Nome: </h2><label>${os_data.nome}</label></div>
                <div class="infos"><h2>CPF: </h2><label>${os_data.cpf}</label></div>
                <div class="infos"><h2>Endereço: </h2><label>${os_data.endereco}</label></div>
                <div class="infos"><h2>Telefone: </h2><label>${os_data.telefone}</label></div>
                <hr>
                <div class="infos"><h2>Produto: </h2><label>${os_data.produto}</label></div>
                <div class="infos"><h2>Série: </h2><label>${os_data.numeroSerie}</label></div>
                <hr>
                <div class="infos"><h2>Serviço: </h2><label>${os_data.servico}</label></div>
                <div class="infos"><h2>Data para entrega: </h2><label>${os_data.dataSaida}</label></div>
                <div class="infos"><h2>Técnico responsável: </h2><label>${os_data.funcionario_id.nome}</label></div>
                <div class="infos"><h2>Observações do cliente: </h2><label>${os_data.obs}</label></div>`

    div_data.innerHTML += data

    os_selecionada.showModal()
}

function formatarMoeda(numero) {
    return numero.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }