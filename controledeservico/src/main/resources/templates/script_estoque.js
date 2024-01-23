const url_estoque = "http://localhost:8080/estoque"
const url_reservas = "http://localhost:8080/reservas"
var produtoLista = []

function abrirModalNovoProduto(){
    listaProdutosModal.showModal()
}
function cadastrarProdutoNaLista(){
    cadastroNovoProdutoModal.showModal()
}
function enviarLista(){
    post_produto(produtoLista)
    listaProdutosModal.close()
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

    getEstoque()
    getReservas()
    
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


async function post_produto(produto) {
    console.log(produto)
    try{
        const response = await fetch(url_estoque, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(produto)
        })

        console.log(response.status)
    } catch (error) {
        console.error(error)
    }
}

async function getEstoque(){
    const estoqueTable = document.querySelector("tbody")

    const response = await fetch(url_estoque);
    const data = await response.json(); 

    data.forEach(element => {
        
        estoqueTable.innerHTML += `
            <tr>
                <td>${element.produto}</td>
                <td>${element.referencia}</td>
                <td>${element.quantidade}</td>
                <td>${formatarMoeda(element.precoUnitario)}</td>
            </tr>
        `

    })

}
async function getReservas(){
    const box = document.getElementById("divTabela2")

    const response = await fetch(url_reservas)
    const data = await response.json()

    console.log(data)
    var item = ""
    var item = ""; // Inicialize a variável fora do loop

    data.forEach(element => {
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

}

function formatarMoeda(numero) {
    return numero.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }