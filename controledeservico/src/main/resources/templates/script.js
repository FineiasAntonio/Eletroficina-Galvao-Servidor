const url_OS = "http://localhost:8080/ordensdeservicos"
const url_Funcionario = "http://localhost:8080/funcionarios"
const url_Notification = "http://localhost:8080/notifications"

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
            <input id="uuid${novoFormId}" hidden>
            <input id="produto${novoFormId}" placeholder="produto" disabled>
            <input id="quantidade${novoFormId}" placeholder="quantidade" type="number">
            <input id="precoUnitario${novoFormId}" placeholder="Preço unitário" disabled>
            <input id="referencia${novoFormId}" placeholder="Referência" disabled>
        `
        
        var campoPreco = novoForm.querySelector(`#precoUnitario${novoFormId}`)
        campoPreco.addEventListener('blur', function(){
            this.value = formatarMoeda(this.value)
        })

        var novoSelectId = controle
        var novoSelect = document.createElement("select")
        novoSelect.id = novoSelectId
        novoSelect.innerHTML += "<option hidden selected>Escolha uma opção</option>"
        avaiableProducts.forEach(element => {
            novoSelect.innerHTML += `
                <option value="${element.id_produto}">${element.produto} - ${element.quantidade} Unidades</option>
            `
        })
        
        novoSelect.innerHTML += "<option value='outro'>Outro</option>"

        novoSelect.addEventListener("change", function () {
            var value = novoSelect.value
            var campoId = novoForm.querySelector(`#uuid${novoFormId}`)
            var campoNome = novoForm.querySelector(`#produto${novoFormId}`)
            var campoQuantidade = novoForm.querySelector(`#quantidade${novoFormId}`)
            var campoPrecoUnitario = novoForm.querySelector(`#precoUnitario${novoFormId}`)
            var campoReferencia = novoForm.querySelector(`#referencia${novoFormId}`)

            if (value == "outro") {
                novoForm.querySelector(`#produto${novoFormId}`).removeAttribute("disabled")
                novoForm.querySelector(`#precoUnitario${novoFormId}`).removeAttribute("disabled")
                novoForm.querySelector(`#referencia${novoFormId}`).removeAttribute("disabled")

                campoNome.value = ""
                campoQuantidade.value = ""
                campoPrecoUnitario.value = ""
                campoReferencia.value = ""
            } else {
                campoNome.setAttribute("disabled", true)
                campoPrecoUnitario.setAttribute("disabled", true)
                campoReferencia.setAttribute("disabled", true)

                var produtoSelecionado

                avaiableProducts.forEach(element => {
                    if (value == element.id_produto){
                        produtoSelecionado = element
                    }
                })
                
                campoId.value = produtoSelecionado.id_produto
                campoNome.value = produtoSelecionado.produto
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
function submit(){

    function reservarProdutos(){
        var produtos = []
        formsArray.forEach(element => {
            var produtoIndividual = {
                id_produto: element.querySelector("input:nth-child(1)").value,
                produto: element.querySelector("input:nth-child(2)").value,
                quantidade: element.querySelector("input:nth-child(3)").value,
                precoUnitario: desformatarMoeda(element.querySelector("input:nth-child(4)").value),
                referencia: element.querySelector("input:nth-child(5)").value
            }

            produtos.push(produtoIndividual)
        })

        return produtos

    }

    const os = {
        nome: document.getElementById('nome').value,
        cpf: document.getElementById('cpf').value,
        endereco: document.getElementById('endereco').value,
        telefone: document.getElementById('telefone').value,
        dataSaida: document.getElementById('dataSaida').value,
        equipamento: document.getElementById('equipamento').value,
        numeroSerie: document.getElementById('numeroSerie').value,
        servico: document.getElementById('servico').value,
        obs: document.getElementById('obs').value,
        funcionario_id: document.getElementById("funcionarioBox").value,
        coments: document.getElementById("coments").value,
        produtosReservados: reservarProdutos(formsArray)
    };

    post_OS(os)

    modal.close()
}

window.addEventListener("load", function() {
    
    const novaOrdemModal = document.getElementById("novaOrdemModal")
    const form = document.querySelector("form");

    const tbody = document.querySelector("tbody")
    
    tbody.addEventListener("dblclick", function(event){
        const row = event.target.closest("tr")
        var os_id = row.cells[0].innerText
        
        showOS(os_id)
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

    updateTable()
    

})

async function getFuncionarioByID(id){
    const response = await fetch(url_Funcionario + `/${id}`)
    const data = await response.json()
    return data
}
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
async function deleteOS(id) {
    var response = fetch(url_OS + `/${id}`, {
        method: "DELETE"
    })

    console.log((await response).status)
}
async function updateOS(id, os){
    const response = await fetch(url_OS + `/${id}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(os)
    })

    console.log((await response).status)
    
}
function updateTable(){
    const table = document.querySelector("tbody");

    table.innerHTML = ""

    getAllOS().then(lista => {
        lista.forEach(element => {
        var row = `<tr>\
                    <td>${element.os}</td>\
                    <td>${element.nome}</td>\
                    <td>${element.equipamento}</td>\
                    <td>${element.dataSaida}</td>\
                    <td>${element.servico}</td>\
                    <td>${element.situacao}</td>\
                    <td>${element.funcionario_id.nome}</td>\
                </td>`
        
        table.innerHTML += row;

    })});
}
function showOS(os_id){
    const os_selecionada = document.getElementById("os_selecionada")
    const div_data = document.getElementById("data_div")

    os_selecionada.onclose = function(){
        div_data.innerHTML = ""
    }

    const ordem = getOSById(os_id).then(os_data => {
        os_selecionada.querySelector(".apagarOs").onclick = function() {
            deleteOS(os_data.os);
        };
        
        os_selecionada.querySelector(".editarOs").onclick = function() {
            editarOs(os_selecionada, os_data);
        };
    
    
        var data = `<div class="infos"><h2>Nome: </h2><label>${os_data.nome}</label></div>
                    <div class="infos"><h2>CPF: </h2><label>${os_data.cpf}</label></div>
                    <div class="infos"><h2>Endereço: </h2><label>${os_data.endereco}</label></div>
                    <div class="infos"><h2>Telefone: </h2><label>${os_data.telefone}</label></div>
                    <hr>
                    <div class="infos"><h2>Equipamento: </h2><label>${os_data.equipamento}</label></div>
                    <div class="infos"><h2>Série: </h2><label>${os_data.numeroSerie}</label></div>
                    <hr>
                    <div class="infos"><h2>Serviço: </h2><label>${os_data.servico}</label></div>
                    <div class="infos"><h2>Data de entrada: </h2><label>${os_data.dataEntrada}</label></div>
                    <div class="infos"><h2>Data para entrega: </h2><label>${os_data.dataSaida}</label></div>
                    <div class="infos"><h2>Situação: </h2><label>${os_data.situacao}</label></div>
                    <div class="infos"><h2>Técnico responsável: </h2><label>${os_data.funcionario_id.nome}</label></div>
                    <div class="infos"><h2>Observações do cliente: </h2><label>${os_data.obs}</label></div>
                    <div class="infos"><h2>Coments: </h2><label>${os_data.coments}</label></div>
                    
                    `
    
        div_data.innerHTML += data
    
        os_selecionada.showModal()
    });
}
async function editarOs(os_modal, os_data){
    const response = await fetch(url_Funcionario)
    const data = await response.json()

    os_modal.querySelector("#data_div").innerHTML = `<form>
    <div>
      <h2>Informações do cliente</h2>
      <input type="text" placeholder="Nome" id="nomeE" value="${os_data.nome}">
      <input type="number" placeholder="CPF/CNPJ" id="cpfE" value="${os_data.cpf}">
      <input type="text" placeholder="Endereço" id="enderecoE" value="${os_data.endereco}">
      <input type="number" placeholder="Telefone" id="telefoneE" value="${os_data.telefone}">
    </div>
    <hr>
    <div>
      <h2>Informações do equipamento</h2>
      <input type="text" placeholder="equipamento" id="equipamentoE" value="${os_data.equipamento}">
      <input type="number" placeholder="Número de série" id="numeroSerieE" value="${os_data.numeroSerie}">
    </div>
    <hr>
    <div>
      <h2>Informações do serviço</h2>
      <input type="text" placeholder="Serviço" id="servicoE" value="${os_data.servico}">
      
      <label>Data para entrega</label>
      <input type="date" placeholder="Data de entrega" id="dataSaidaE" value="${os_data.dataSaida}">

      <input type="checkbox" id="concluir"> Concluído

      <select id="situacaoBox" value="${os_data.subSituacao}" hidden>
        <option value = "1">Aguardando Retirada</option>
        <option value = "0">Entregue</option>
      </select>

      <label>Técnico responsável</label>
      <select id="funcionarioBoxEdit" value="${os_data.funcionario_id.nome}">
      ${data.map(element => `<option value="${element.id}">${element.nome}</option>`).join('')}
      </select>


      <div id="anexoEntrada">
        <p>Anexar Imagem de Entrada</p>
        <input type="file" id="imagesEntrada" accept="image/jpeg" multiple/>
        <label for="images" id="images_label">+</label>
      </div>

      <div id="anexoSaida">
        <p>Anexar Imagem de Saída</p>
        <input type="file" id="imagesSaida" accept="image/jpeg" multiple/>
        <label for="images" id="images_label">+</label>
      </div>

      <label>Observações do cliente</label>
      <input type="text" id="obsE" value="${os_data.obs}">

      <label>Comentários</label>
      <input type="text" id="comentsE" value="${os_data.coments}">
    </div>
  </form>
  
  <button class="submitUpdate">Enviar</button>

  `

    const concluirCheck = document.getElementById("concluir")
    const situacaoBox = document.getElementById("situacaoBox")

    var concluidoBoolean = null

  concluirCheck.addEventListener("change", function() {
    if(concluirCheck.checked){
        situacaoBox.removeAttribute("hidden")
        concluidoBoolean = true
    } else {
        situacaoBox.setAttribute("hidden", "true")
        concluidoBoolean = false
    }

  })

const submitBtn = os_modal.querySelector(".submitUpdate")
submitBtn.onclick = function() {
    const osE = {
        nome: document.getElementById('nomeE').value,
        cpf: document.getElementById('cpfE').value,
        endereco: document.getElementById('enderecoE').value,
        telefone: document.getElementById('telefoneE').value,
        dataSaida: document.getElementById('dataSaidaE').value,
        equipamento: document.getElementById('equipamentoE').value,
        numeroSerie: document.getElementById('numeroSerieE').value,
        servico: document.getElementById('servicoE').value,
        obs: document.getElementById('obsE').value,
        funcionario_id: document.getElementById("funcionarioBoxEdit").value,
        coments: document.getElementById("comentsE").value,
        situacao: concluidoBoolean,
        subSituacao: situacaoBox.value
        
    };

    console.log(osE)

    updateOS(os_data.os, osE)

    os_modal.close()
    os_modal.querySelector("#data_div").innerHTML = ""
}

}

function formatarMoeda(valor) {
    const valorNumerico = parseFloat(valor.toString().replace(/[^\d.]/g, ''));
    //const valorFormatado = (isNaN(valorNumerico)) ? '' : 'R$ ' + valorNumerico.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');;

    const valorFormatado = valorNumerico.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    return valorFormatado;
}
function desformatarMoeda(valorFormatado) {
    const valorNumerico = parseFloat(valorFormatado.replace(/[^\d.,]/g, '').replace(',', '.'));
    return isNaN(valorNumerico) ? 0 : valorNumerico;
}

async function getAllOS(){
    var response = await fetch(url_OS);
    var data = await response.json();

    console.log(data)

    return data;
}
async function getOSById(id) {
    try {
        const response = await fetch(url_OS + `/${id}`)
        const data = await response.json()
        
        return data
    } catch (error) {
        console.error("Erro durante a requisição:", error);
    }
}

async function verifyNewNotification(){
    var response = await fetch(url_Notification)
    
    if (response.status == 200){

    }
}

function showNotifications() {
    var notificationPopup = document.getElementById("notification-window");
    notificationPopup.style.display = "block";
  }

  function hideNotifications() {
    var notificationPopup = document.getElementById("notification-window");
    notificationPopup.style.display = "none";
  }