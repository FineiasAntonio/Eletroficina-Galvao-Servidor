const url_OS = "http://localhost:8080/ordensdeservicos"
const url_Funcionario = "http://localhost:8080/funcionarios"



window.addEventListener("load", function() {
    
    const button_abrir = document.getElementById("abrir_modal")
    const button_fechar = document.getElementById("fechar_modal")

    const modal = document.getElementById("modal")
    const form = document.querySelector("form");

    const tbody = document.querySelector("tbody")
    
    tbody.addEventListener("dblclick", function(event){
        const row = event.target.closest("tr")
        var os_id = row.cells[0].innerText
        
        getById_OS(os_id)
    })

    modal.onclose = function(){
        const inputs = document.querySelectorAll("input")
        
        inputs.forEach(element => {
            element.value = ""
        })
    }
    button_abrir.onclick = function(){
        modal.showModal()
        getAll_Funcionarios()
    }
    button_fechar.onclick = function(){
        modal.close()
    }

    form.addEventListener('submit', function(event) {
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
    })

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
