const url_estoque = "http://localhost:8080/estoque"
const url_reservas = "http://localhost:8080/reservas"

var produtoLista = []

window.addEventListener("load", function(){
    const cadastrarProdutoBtn = document.getElementById("cadastrarProdutoBtn")
    const novoProdutoBtn = document.getElementById("novoProdutoBtn")
    const listaProdutosModal = document.getElementById("listaProdutosModal")
    const cadastroNovoProdutoModal = document.getElementById("cadastroNovoProdutoModal")
    const produtoForm = document.getElementById("produtoForm")
    const newProdutosTable = document.getElementById("newProdutosTable")

    var produtoInput = document.getElementById('produto');
    var referenciaInput = document.getElementById('referencia');
    var quantidadeInput = document.getElementById('quantidade');
    var precoInput = document.getElementById('preco');

    cadastrarProdutoBtn.onclick = function(){
        listaProdutosModal.showModal()
    }
    novoProdutoBtn.onclick = function(){
        cadastroNovoProdutoModal.showModal()
    }
    cadastroNovoProdutoModal.onclose = function(){
        produtoForm.reset()
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

    listaProdutosModal.onclose = function(){
        newProdutosTable.innerHTML = ""
        produtoLista = []
    }

})


async function post_produto(produto) {
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

    var response = fetch(url_estoque)

}