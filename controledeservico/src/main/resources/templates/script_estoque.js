const url_estoque = "http://localhost:8080/estoque"
const url_reservas = "http://localhost:8080/reservas"
var produtoLista = []


function enviarLista(){
    postProduto(produtoLista);
    listaProdutosModal.close();
}
function fecharModal(modal){
    modal.close()
}

window.addEventListener("load", function(){
    const listaProdutosModal = document.getElementById("listaProdutosModal")
    const cadastroNovoProdutoModal = document.getElementById("cadastroNovoProdutoModal")
    const produtoForm = document.getElementById("produtoForm")
    const newProdutosTable = document.getElementById("newProdutosTable")

    var produtoInput = document.getElementById('produto');
    var referenciaInput = document.getElementById('referencia');
    var quantidadeInput = document.getElementById('quantidade');
    var precoInput = document.getElementById('preco');

    function abrirModalNovoProduto(){
        listaProdutosModal.showModal()
    }
    function cadastrarProdutoNaLista(){
        cadastroNovoProdutoModal.showModal()
    }

    listEstoque();
    listReservas();
    
    cadastroNovoProdutoModal.onclose = function(){
        produtoForm.reset()
    }
    listaProdutosModal.onclose = function(){
        newProdutosTable.innerHTML = ""
        produtoLista = []
    }

    produtoForm.addEventListener("submit", function(event){
        event.preventDefault()
        var produto = {
            produto: produtoInput.value,
            referencia: referenciaInput.value,
            quantidade: quantidadeInput.value,
            precoUnitario: precoInput.value
        }

        produtoLista.push(produto)

        newProdutosTable.innerHTML += `
            <tr>
                <td>${produto.produto}</td>
                <td>${produto.referencia}</td>
                <td>${produto.quantidade}</td>
                <td>${produto.precoUnitario}</td>
            </tr>
        `

        cadastroNovoProdutoModal.close()

    })

})


async function listEstoque(){
    const estoqueTable = document.querySelector("tbody");

    var arrayProdutos = getAllProdutos();

    arrayProdutos.then(arrayProdutos => {
        arrayProdutos.forEach(element => {
            estoqueTable.innerHTML += `
                <tr>
                    <td>${element.produto}</td>
                    <td>${element.referencia}</td>
                    <td>${element.quantidade}</td>
                    <td>${formatarMoeda(element.precoUnitario)}</td>
                </tr>
            `;
        });
    }).catch(error => {
        console.error("Erro ao obter produtos:", error);
    });

    estoqueTable.addEventListener("dblclick", function(event){
        
        const modal = document.getElementById("showProdutoModal")
        modal.showModal()
        const data_div = document.getElementById("data")
        const row = event.target.closest("tr")
        var produto = row.cells[0].innerText
        var produto_data

        modal.onclose = function(){
            data_div.innerHTML = ""
        }
        
        getAllProdutos().then(arrayProdutos => {     
            arrayProdutos.forEach(element => {
                if (element.produto == produto) {
                    produto_data = element;
                    data_div.innerHTML += `
                    <div class="infos"><h2>Produto: </h2><label>${produto_data.produto}</label></div>
                    <div class="infos"><h2>Referência: </h2><label>${produto_data.referencia}</label></div>
                    <div class="infos"><h2>Quantidade: </h2><label>${produto_data.quantidade}</label></div>
                    <div class="infos"><h2>Preço Unitário: </h2><label>${produto_data.precoUnitario}</label></div>
                    `
                }
            })   
        }).catch(error => {
            console.error("Erro ao obter produtos:", error);
        });
        
        modal.querySelector(".editarProduto").addEventListener("click", function(){
            data_div.innerHTML = `
            <form>
                <label>Produto</label>
                <input type="text" placeholder="Produto" id="produtoF" value="${produto_data.produto}">
                <label>Referência</label>
                <input type="text" placeholder="Referência" id="referenciaF" value="${produto_data.referencia}">
                <label>Quantidade</label>
                <input type="text" placeholder="Quantidade" id="quantidadeF" value="${produto_data.quantidade}">
                <label>Preço Unitário</label>
                <input type="text" placeholder="Preço Unitário" id="precoUnitarioF" value="${produto_data.precoUnitario}">
            </form>
            <button class="submitF">Enviar</button>
            `

            data_div.querySelector(".submitF").addEventListener("click", function(){

                var produtoEditado = {
                    id_produto: produto_data.id_produto,
                    produto: data_div.querySelector("#produtoF").value,
                    referencia: data_div.querySelector("#referenciaF").value,
                    quantidade: data_div.querySelector("#quantidadeF").value,
                    precoUnitario: data_div.querySelector("#precoUnitarioF").value
                }

                updateProduto(produto_data.id_produto, produtoEditado)
            })

        })
        
        modal.querySelector(".excluirProduto").addEventListener("click", function(){
            deleteProduto(produto_data.id_produto);
        })
    })

}
async function listReservas(){
    const box = document.getElementById("divTabela2")

    var item = ""; 

    getAllReservas().then(arrayReservas => {
        arrayReservas.forEach(element => {
            if (element.ativo == true) {
                item += `<details>
                            <summary>Reserva de ${element.id_reserva}</summary>`;
                element.produtos_reservados.forEach(elementQ => {
                    var ctl = elementQ.quantidadeNescessaria + elementQ.quantidadeReservada;
                    item += `<p>${elementQ.produto} ${ctl}/${elementQ.quantidadeNescessaria}</p>`;
                });
                item += `</details>`;
            }
        });
        box.innerHTML += item;
    })

}

async function getAllProdutos() {
    try {
        var response = await fetch(url_estoque);
        var data = await response.json();

        console.log(response.status);

        return data;
    } catch (error) {
        console.error("Erro ao obter produtos:", error);
        throw error; 
    }
}
async function getProdutoById(id){
    var response = await fetch(url_estoque + `/${id}`);
    var data = await response.json();

    return data;
}
async function postProduto(produto){
    var response = await fetch(url_estoque, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(produto)
    });

    console.log(response.status);
}
async function deleteProduto(id){
    var response = await fetch(url_estoque + `/${id}`, {
        method: "DELETE"
    });

    console.log(response.status);
}
async function updateProduto(id, produto){
    const response = await fetch(url_estoque + `/${id}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(produto)
    });

    console.log(response.status);
}

async function getAllReservas(){
    var response = await fetch(url_reservas);
    var data = await response.json();

    return data;
}

function formatarMoeda(numero) {
    return numero.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}
