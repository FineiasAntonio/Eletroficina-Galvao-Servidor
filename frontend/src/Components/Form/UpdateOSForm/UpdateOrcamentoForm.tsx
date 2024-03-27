import { useState, useEffect } from "react";
import { FiTrash2 } from "react-icons/fi";
import { NovoProduto, Produto } from "../../../Service/Entities/Produto";
import { produtosReservados } from "../../../Service/Entities/Reserva";
import { getAllProduto } from "../../../Service/api/ProdutoApi";
import { reserva } from "../../../Service/Entities/OS";
import "../inputs.css"

interface FormProps {
    setarReserva: (
        produtosExistentesInput: produtosReservados[],
        produtosNovosInput: NovoProduto[],
        maoDeObraInput: number
    ) => void;
    reserva?: reserva;
}

export default function UpdateOrcamentoForm({ setarReserva, reserva }: FormProps) {

    const [data, setData] = useState<Produto[]>([]);
    const [produtoSelecionado, setProdutoSelecionado] = useState<Produto>();

    const [produtoInput, setProdutoInput] = useState<string>();
    const [quantidadeInput, setQuantidadeInput] = useState<number>(0);
    const [precoUnitarioInput, setPrecoUnitarioInput] = useState<number>(0);
    const [referenciaInput, setReferenciaInput] = useState<string>();

    const [produtosExistentes, setProdutosExistentes] = useState<produtosReservados[]>([]);
    const [produtosNovos, setProdutosNovos] = useState<NovoProduto[]>([]);

    const [maoDeObra, setMaoDeObra] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const Data = await getAllProduto();
                setData(Data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    useEffect(() => {
        // Chamada para setarReserva sempre que produtosExistentes, produtosNovos ou maoDeObra mudarem
        setarReserva(produtosExistentes, produtosNovos, maoDeObra);
    }, [produtosExistentes, produtosNovos, maoDeObra]);

    const adicionarProdutoNovo = () => {

        setProdutosNovos((prevProdutosNovos) => [
            ...prevProdutosNovos,
            {
                produto: produtoInput,
                quantidade: quantidadeInput,
                precoUnitario: precoUnitarioInput,
                referencia: referenciaInput,
            },
        ]);

    }

    const adicionarProdutoExistente = (produto: Produto) => {

        setProdutosExistentes((prevProdutosExistentes) => [
            ...prevProdutosExistentes,
            {
                uuidProduto: produto.id,
                quantidade: quantidadeInput,
            },
        ]);

    }

    const handleProdutoChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const id = (event.target.value);

        if (id == "outro") {
            setProdutoSelecionado(undefined)
        } else {
            setProdutoSelecionado(data.find((produto) => produto.id === id))
        }
    };

    const handleQuantidadeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(event.target.value);
        setQuantidadeInput(isNaN(value) ? 0 : value);
    };

    const handleProdutoInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setProdutoInput(event.target.value);
    };

    const handlePrecoUnitarioChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(event.target.value);
        setPrecoUnitarioInput(isNaN(value) ? 0 : value);
    };

    const handleReferenciaChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setReferenciaInput(event.target.value);
    };

    const handleMaoDeObra = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(event.target.value);
        setMaoDeObra(isNaN(value) ? 0 : value);
    }

    const adcionarProduto = () => {
        produtoSelecionado ? adicionarProdutoExistente(produtoSelecionado) : adicionarProdutoNovo();
    }

    const apagarExistente = (identificador: string) => {
        setProdutosExistentes(produtosExistentes.filter(e => e.uuidProduto != identificador));
    }

    const apagarNovo = (identificador: string) => {
        setProdutosNovos(produtosNovos.filter(e => e.produto != identificador));
    }

    

    return (
        <div className="rounded p-2 min-h-tela">
            <div className="flex justify-center">
                <h1>Folha de Orçamento</h1>
            </div>

            <div >
                {reserva?.produtos_reservados?.map((produto) => (
                    <div key={produto.id} className="flex justify-between">
                        <p>{produto.produto}</p>
                        <p>{produto.quantidade}/{produto.quantidadeNescessaria}</p>
                        <p>{produto.precoUnitario}</p>
                        <p>{produto.referencia}</p>
                    </div>
                ))}
            </div>

            <label htmlFor="country" className="block text-sm font-medium leading-6 text-gray-900">
                Produto
            </label>

            

            <div className="sm:col-span-3 flex justify-between items-center">

                <div className="mt-2">
                    <select
                        onChange={handleProdutoChange}
                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                    >
                        {data.map((produto) => (
                            <option key={produto.id} value={produto.id}>{produto.produto} - {produto.quantidade} unidades</option>
                        ))}
                        <option key={"outro"} value={"outro"}>Outro</option>
                    </select>
                </div>

                {produtoSelecionado == undefined ? (
                    <>
                        <input onChange={handleProdutoInputChange} placeholder="produto" type="text" />
                        <input onChange={handleQuantidadeChange} placeholder="quantidade" type="number" />
                        <input onChange={handlePrecoUnitarioChange} placeholder="Preço unitário" type="number" />
                        <input onChange={handleReferenciaChange} placeholder="Referência" type="text" />
                    </>
                ) : (
                    <>
                        <input value={produtoSelecionado?.produto} placeholder="produto" type="text" disabled />
                        <input onChange={handleQuantidadeChange} placeholder="quantidade" type="number" />
                        <input value={produtoSelecionado?.precoUnitario} placeholder="Preço unitário" type="number" disabled />
                        <input value={produtoSelecionado?.referencia} placeholder="Referência" type="text" disabled />
                    </>
                )
                }

            </div>

            <button onClick={adcionarProduto} className="bg-blue-500 m-3 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">
                Adicionar
            </button>

            <div>
                {produtosExistentes.map(t => {
                    const produtoInfo = data.find(e => e.id == t.uuidProduto)

                    return (
                        <div className="flex justify-between">
                            <label>{produtoInfo?.produto}</label>
                            <label>{produtoInfo?.quantidade}</label>
                            <label>{produtoInfo?.precoUnitario}</label>
                            <label>{produtoInfo?.referencia}</label>
                            <button onClick={() => produtoInfo?.id && apagarExistente(produtoInfo?.id)}>
                                <FiTrash2 />
                            </button>
                            <br />
                        </div>
                    )
                })}
                {produtosNovos.map(t => {
                    return (
                        <div className="flex justify-between">
                            <label>{t?.produto}</label>
                            <label>{t?.quantidade}</label>
                            <label>{t?.precoUnitario}</label>
                            <label>{t?.referencia}</label>
                            <button onClick={() => t?.produto && apagarNovo(t?.produto)}>
                                <FiTrash2 />
                            </button>
                            <br />
                            <br />
                        </div>
                    )
                })}
            </div>

            <div className="h-32 mt-3 items-center flex">
                <label>Mão de obra</label>
                <input type="number" defaultValue={reserva?.maoDeObra} className="ml-5" onChange={handleMaoDeObra} />
            </div>
        </div>
    )
}