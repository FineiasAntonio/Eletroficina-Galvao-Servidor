import { OrdemServico } from "../Service/Entities/OS";
import ReactModal from 'react-modal';
import { FaTrash } from "react-icons/fa";
import { FaPencil } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";
import { deleteOS } from "../Service/api/OSapi";
import { format } from "date-fns/format";
import { toast } from "react-toastify";
import { FiPrinter } from "react-icons/fi";

interface ModalOSProps {
    OS?: OrdemServico;
    isOpen: boolean;
    onClose: () => void;
}

const ModalOS: React.FC<ModalOSProps> = ({ OS, isOpen, onClose }) => {

    const navigate = useNavigate();

    const imprimir = (id: number | undefined) => {
        window.open(`http://localhost:8080/ordensdeservicos/print/${id}`)
    }

    return (
        <ReactModal
            isOpen={isOpen}
            onRequestClose={onClose}
            contentLabel="Detalhes da Ordem de Serviço"
        >
            <div>
                <div className="px-4 sm:px-0 flex justify-between">
                    <div>
                        <h1 className="text-base font-semibold leading-7 text-gray-900">Ordem de Serviço {OS?.id}</h1>
                        <h3>{OS?.situacao} {OS?.subSituacao ? (<>({OS?.subSituacao})</>) : (<></>)}</h3>
                    </div>
                    <div className="flex h-1/2 mt-2">
                        <button
                            onClick={() => {imprimir(OS?.id)}}
                            className="mr-3 flex rounded-md bg-gray-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        ><FiPrinter className="mr-2" /> Imprimir</button>
                        <button
                            onClick={() => navigate(`/update/${OS?.id}`)}
                            className="mr-3 flex rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        ><FaPencil className="mr-2" /> Editar</button>
                        <button
                            className="flex rounded-md bg-red-700 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            onClick={() => {
                                deleteOS(OS?.id)
                                window.location.reload()
                                toast.success("OS apagada com sucesso")
                            }}
                        ><FaTrash className="mr-2" /> Excluir</button>
                    </div>
                </div>
                <div className="mt-6 border-t border-gray-100">
                    <dl className="divide-y divide-gray-100">
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Cliente</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.nome}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">CPF/CNPJ</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.cpf}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Telefone</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.telefone}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Endereço</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.endereco}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Equipamento</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.equipamento}</dd>
                        </div>
                        {OS?.numeroSerie ? (
                            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                                <dt className="text-sm font-medium leading-6 text-gray-900">Número de série</dt>
                                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.numeroSerie}</dd>
                            </div>
                        ) : (<></>)
                        }
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Serviço</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.servico}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Data de entrada</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.dataEntrada ? format(OS.dataEntrada, "dd/MM/yyyy", undefined) : ''}</dd>

                            <dt className="text-sm font-medium leading-6 text-gray-900">Data de saída</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.dataSaida ? format(OS.dataSaida, "dd/MM/yyyy", undefined) : ''}</dd>

                            {OS?.dataConclusao ? (
                                <>
                                    <dt className="text-sm font-medium leading-6 text-gray-900">Data de conclusão</dt>
                                    <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.dataConclusao ? format(OS.dataConclusao, "dd/MM/yyyy", undefined) : ''}</dd>
                                </>
                            ) : (
                                ''
                            )}

                            {OS?.dataEntrega ? (
                                <>
                                    <dt className="text-sm font-medium leading-6 text-gray-900">Data de entrega</dt>
                                    <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.dataEntrega ? format(OS.dataEntrega, "dd/MM/yyyy", undefined) : ''}</dd>
                                </>
                            ) : (
                                ''
                            )}
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Técnico responsável</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{OS?.funcionario.nome}</dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Observações do cliente</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                                {OS?.observacao}
                            </dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            <dt className="text-sm font-medium leading-6 text-gray-900">Comentários</dt>
                            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                                {OS?.comentarios}
                            </dd>
                        </div>
                        <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                            {/* <dt className="text-sm font-medium leading-6 text-gray-900">Attachments</dt>
                            <dd className="mt-2 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                                <ul role="list" className="divide-y divide-gray-100 rounded-md border border-gray-200">
                                    <li className="flex items-center justify-between py-4 pl-4 pr-5 text-sm leading-6">
                                        <div className="flex w-0 flex-1 items-center">
                                            {OS?.imagemEntrada?.map(e => (
                                                <img src={e}></img>
                                            ))}
                                        </div>
                                        <div className="ml-4 flex-shrink-0">
                                            <a href="#" className="font-medium text-indigo-600 hover:text-indigo-500">
                                                Download
                                            </a>
                                        </div>
                                    </li>
                                </ul>
                            </dd> */}
                        </div>
                    </dl>
                </div>
            </div>
        </ReactModal>
    );
}

export default ModalOS;