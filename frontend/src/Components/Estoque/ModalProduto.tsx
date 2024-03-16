import { useState } from "react";
import ReactModal from "react-modal";
import { NovoProduto } from "../../Service/Entities/Produto";
import { createProduto } from "../../Service/api/ProdutoApi";

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
}


export default function ModalProduto({ isOpen, onClose }: ModalProps) {

    const [produto, setProduto] = useState("");
    const [referencia, setReferencia] = useState("");
    const [precoUnitario, setPrecoUnitario] = useState("");
    const [quantidade, setQuantidade] = useState("");

    const handleProdutoInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        setProduto(e.target.value);
      };
    
      const handleReferenciaInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        setReferencia(e.target.value);
      };
    
      const handlePrecoUnitarioInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPrecoUnitario(e.target.value);
      };
    
      const handleQuantidadeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
        setQuantidade(e.target.value);
      };

      const enviarProduto = async () => {
        const produtoInput: NovoProduto = {
            produto: produto,
            referencia: referencia,
            precoUnitario: parseInt(precoUnitario),
            quantidade: parseInt(quantidade)
        }

        const status = createProduto(produtoInput)

        if (await status == 201){
            window.location.reload()
        }
      }

    return (
        <ReactModal
            isOpen={isOpen}
            onRequestClose={onClose}
            contentLabel="Cadastro novo produto"
            style={{
                content: {
                    width: '60%', // Ajuste o valor conforme necessário
                    maxWidth: '800px', // Defina um valor máximo se desejar
                    margin: 'auto', // Centralize o modal horizontalmente
                    height: '70%',
                },
                overlay: {
                    backgroundColor: 'rgba(0, 0, 0, 0.7)', // Cor do fundo do overlay
                },
            }}
        >
            <div>
                <div className="px-4 sm:px-0 flex justify-between">
                    <div>
                        <h1 className="text-base text-lg font-semibold leading-7 text-gray-900">Cadastrar novo produto</h1>

                    </div>
                </div>
                <div className="mt-6 border-t border-gray-100">
                    <dl className="divide-y divide-gray-100">
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Produto</dt>
                            <input type="text" placeholder="Produto" className="w-full" onChange={handleProdutoInput} />
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Referência</dt>
                            <input type="text" placeholder="Referência" className="w-full" onChange={handleReferenciaInput} />
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Preço Unitário</dt>
                            <input type="number" placeholder="Preço unitário" className="w-full" onChange={handlePrecoUnitarioInput} />
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Quatidade</dt>
                            <input type="number" placeholder="Preço Quantidade" className="w-full" onChange={handleQuantidadeInput} />
                        </div>

                    </dl>

                    <div className="flex justify-end">
                        <button type="button" className="text-sm font-semibold leading-6 text-gray-900 mr-4" onClick={() => { onClose() }}>
                            Cancelar
                        </button>
                        <button
                            onClick={enviarProduto}
                            type="submit"
                            className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            Salvar
                        </button>
                    </div>
                </div>
            </div>
        </ReactModal>
    )
}