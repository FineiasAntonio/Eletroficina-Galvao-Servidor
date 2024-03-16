import { useEffect, useState } from "react";
import { Reserva, produtosReservados } from "../../../Service/Entities/Reserva";
import { NovoProduto } from "../../../Service/Entities/Produto";
import { useNavigate, useParams } from "react-router-dom";
import { OrdemServico, UpdateOrdemServicoDTO, situacao, subSituacao } from "../../../Service/Entities/OS";
import { getOSById, updateOS } from "../../../Service/api/OSapi";
import { Funcionario } from "../../../Service/Entities/Funcionario";
import { PhotoIcon } from "@heroicons/react/24/outline";
import { getAllFuncionario } from "../../../Service/api/FuncionarioApi";
import UpdateOrcamentoForm from "./UpdateOrcamentoForm";

export default function UpdateForm() {

    const navigate = useNavigate();
    let { id } = useParams();

    const [funcionarios, setFuncionarios] = useState<Funcionario[]>();
    const [reserva, setReserva] = useState<Reserva>();
    const [OS, setOS] = useState<OrdemServico>({
        id: 0,
        nome: "",
        telefone: "",
        endereco: "",
        cpf: "",
        dataSaida: new Date(),
        equipamento: "",
        numeroSerie: "",
        observacao: "",
        servico: "",
        situacao: situacao.EM_ANDAMENTO,
        funcionario: {
            id: 0,
            nome: ""
        },
        reserva: undefined,
        comentarios: "",
        valorTotal: 0
    })
    const [updateRequest, setUpdateRequest] = useState<UpdateOrdemServicoDTO>({
        nome: "",
        cpf: "",
        telefone: "",
        endereco: "",
        dataSaida: new Date(),
        equipamento: "",
        numeroSerie: "",
        funcionarioId: 0,
        observacao: "",
        comentarios: "",
        servico: "",
        concluido: false,
        subSituacao: undefined,
        reserva: {}
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {

        if (e.target.name == "telefone") {
            e.target.value = formatTelefone(e.target.value);
        }
        if (e.target.name == "cpf") {
            e.target.value = formatCPF(e.target.value);
        }

        const { name, value, type, checked } = e.target;

        let inputValue: string | boolean

        // Se for um campo de checkbox, use 'checked' em vez de 'value'
        inputValue = type === "checkbox" ? checked : value;

        setUpdateRequest((prevData) => ({
            ...prevData,
            [name]: inputValue,
        }));
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const osData = await getOSById(id);
                osData.dataSaida = new Date(osData.dataSaida);
                setOS(osData);

                const updateRequestData: UpdateOrdemServicoDTO = {
                    nome: osData?.nome || "",
                    cpf: osData?.cpf || "",
                    endereco: osData?.endereco || "",
                    telefone: osData?.telefone || "",
                    dataSaida: osData.dataSaida,
                    equipamento: osData.equipamento,
                    numeroSerie: osData.numeroSerie,
                    funcionarioId: osData.funcionario.id,
                    observacao: osData.observacao,
                    comentarios: osData.comentarios,
                    servico: osData.servico,
                    concluido: osData.situacao === situacao.CONCLUIDO,
                    subSituacao: osData.subSituacao,
                    reserva: {

                    }
                };

                setUpdateRequest(updateRequestData);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    // Funcionários
    useEffect(() => {
        const fetchData = async () => {
            try {
                const Data = await getAllFuncionario();
                setFuncionarios(Data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    const setarReserva = (produtosExistentesInput: produtosReservados[], produtosNovosInput: NovoProduto[], maoDeObraInput: number) => {
        const Reserva = {
            produtosExistentes: produtosExistentesInput,
            produtosNovos: produtosNovosInput,
            maoDeObra: maoDeObraInput
        }
        setReserva(Reserva);
    }

    const envia = async () => {

        let osAtualizada: UpdateOrdemServicoDTO = {
            nome: updateRequest.nome,
            cpf: updateRequest.cpf,
            endereco: updateRequest.endereco,
            telefone: updateRequest.telefone,
            dataSaida: updateRequest.dataSaida,
            equipamento: updateRequest.equipamento,
            numeroSerie: updateRequest.numeroSerie,
            servico: updateRequest.servico,
            observacao: updateRequest.observacao,
            funcionarioId: updateRequest.funcionarioId,
            comentarios: updateRequest.comentarios,
            concluido: updateRequest.concluido,
            subSituacao: updateRequest.subSituacao,
            reserva: reserva
        }

        const response = await updateOS(OS.id, osAtualizada);
        if (response === 201) {
            navigate("/");
        }
    }


    return (
        <div className="w-screen-md mx-auto">

            <div className="mt-4 p-4 bg-gray-100">
                <>
                    <form>
                        <div className="space-y-12">
                            <div className="border-b border-gray-900/10 pb-12">
                                <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">

                                    {/* Nome */}
                                    <div className="sm:col-span-2">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Cliente
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="nome"
                                                    defaultValue={OS?.nome}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Cliente"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    {/* CPF/CNPJ */}
                                    <div className='sm:col-span-2'>
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            CPF/CNPJ
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="cpf"
                                                    id="cpf"
                                                    defaultValue={OS?.cpf}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="CPF/CNPJ" />
                                            </div>

                                        </div>
                                    </div>

                                    {/* Telefone */}
                                    <div className='sm:col-span-2'>
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Telefone
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="telefone"
                                                    id="telefone"
                                                    defaultValue={OS?.telefone}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Telefone"
                                                />
                                            </div>

                                        </div>
                                    </div>

                                    {/* Endereço */}
                                    <div className="col-span-full">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Endereço
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="endereco"
                                                    defaultValue={OS?.endereco}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Endereço"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    <hr className='col-span-full' />

                                    {/* Equipamento */}
                                    <div className="sm:col-span-3">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Equipamento
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="equipamento"
                                                    defaultValue={OS?.equipamento}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Equipamento"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    {/* Serial */}
                                    <div className="sm:col-span-3">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Número de série
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="serial"
                                                    defaultValue={OS?.numeroSerie}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Número de série"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    <hr className='col-span-full' />

                                    {/* Serviço */}
                                    <div className="sm:col-span-3">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Serviço
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="text"
                                                    name="servico"
                                                    defaultValue={OS?.servico}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                    placeholder="Serviço"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    {/* Data de saída */}
                                    <div className="sm:col-span-3">
                                        <label className="block text-sm font-medium leading-6 text-gray-900">
                                            Data prevista
                                        </label>
                                        <div className="mt-2">
                                            <div className="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                                                <input
                                                    type="date"
                                                    name="dataSaida"
                                                    value={new Date(OS?.dataSaida).toISOString().split('T')[0]}
                                                    onChange={handleInputChange}
                                                    className="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                                                />
                                            </div>

                                        </div>

                                    </div>

                                    {/* Funcionário */}
                                    <div className="sm:col-span-3">
                                        <label htmlFor="country" className="block text-sm font-medium leading-6 text-gray-900">
                                            Funcionário Responsável
                                        </label>
                                        <div className="mt-2">
                                            <select
                                                id="funcionario"
                                                name="funcionario"
                                                defaultValue={OS?.funcionario.nome}
                                                onChange={() => handleInputChange}
                                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                                            >
                                                {
                                                    funcionarios?.map((funcionario) => {

                                                        return (
                                                            <option key={funcionario.id} value={funcionario.id}>{funcionario.nome}</option>
                                                        )

                                                    })
                                                }
                                            </select>
                                        </div>
                                    </div>

                                    <div>
                                        <input
                                            type="checkbox"
                                            name="concluido"
                                            checked={updateRequest.concluido}
                                            onChange={handleInputChange}
                                            className="form-checkbox h-4 w-4 text-indigo-600 transition duration-150 ease-in-out"
                                        />
                                        <span className="ml-2 text-gray-600">Concluído</span>
                                    </div>

                                    <div>
                                        <select
                                            id="funcionario"
                                            name="subSituacao"
                                            defaultValue={OS?.subSituacao}
                                            onChange={() => handleInputChange}
                                            className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                                        >
                                            {updateRequest.concluido ? (
                                                <>
                                                    <option key={subSituacao.AGUARDANDO_RETIRADA} value={subSituacao.AGUARDANDO_RETIRADA}>Aguardando Retirada</option>
                                                    <option key={subSituacao.ENTREGUE} value={subSituacao.ENTREGUE}>Entregue</option>
                                                </>
                                            ) : (
                                                <>
                                                    <option key={subSituacao.APROVADO} value={subSituacao.APROVADO}>Aprovado</option>
                                                    <option key={subSituacao.MONTADO} value={subSituacao.MONTADO}>Montado</option>
                                                    <option key={subSituacao.TESTADO} value={subSituacao.TESTADO}>Montado</option>
                                                </>
                                            )


                                            }
                                        </select>
                                    </div>

                                    {/* OBS do cliente */}
                                    <div className="col-span-full">
                                        <label htmlFor="about" className="block text-sm font-medium leading-6 text-gray-900">
                                            Observações do cliente
                                        </label>
                                        <div className="mt-2">
                                            <textarea
                                                id="observacao"
                                                name="observacao"
                                                defaultValue={OS?.observacao}
                                                onChange={() => handleInputChange}
                                                rows={3}
                                                className="p-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                            />
                                        </div>
                                    </div>

                                    {/* Coments */}
                                    <div className="col-span-full">
                                        <label htmlFor="about" className="block text-sm font-medium leading-6 text-gray-900">
                                            Comentários
                                        </label>
                                        <div className="mt-2">
                                            <textarea
                                                id="observacao"
                                                name="observacao"
                                                defaultValue={OS?.comentarios}
                                                onChange={() => handleInputChange}
                                                rows={3}
                                                className="p-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                            />
                                        </div>
                                    </div>

                                    {/* Foto entrada */}
                                    <div className="col-span-full">
                                        <label htmlFor="cover-photo" className="block text-sm font-medium leading-6 text-gray-900">
                                            Cover photo
                                        </label>
                                        <div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                                            <div className="text-center">
                                                <PhotoIcon className="mx-auto h-12 w-12 text-gray-300" aria-hidden="true" />
                                                <div className="mt-4 flex text-sm leading-6 text-gray-600">
                                                    <label
                                                        htmlFor="file-upload"
                                                        className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                                                    >
                                                        <span>Upload a file</span>
                                                        <input id="file-upload" name="file-upload" type="file" className="sr-only" />
                                                    </label>
                                                    <p className="pl-1">or drag and drop</p>
                                                </div>
                                                <p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
                                            </div>
                                        </div>
                                    </div>

                                    {/* Foto saida */}
                                    <div className="col-span-full">
                                        <label htmlFor="cover-photo" className="block text-sm font-medium leading-6 text-gray-900">
                                            Cover photo
                                        </label>
                                        <div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                                            <div className="text-center">
                                                <PhotoIcon className="mx-auto h-12 w-12 text-gray-300" aria-hidden="true" />
                                                <div className="mt-4 flex text-sm leading-6 text-gray-600">
                                                    <label
                                                        htmlFor="file-upload"
                                                        className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                                                    >
                                                        <span>Upload a file</span>
                                                        <input id="file-upload" name="file-upload" type="file" className="sr-only" />
                                                    </label>
                                                    <p className="pl-1">or drag and drop</p>
                                                </div>
                                                <p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </form>
                </>
                <UpdateOrcamentoForm setarReserva={setarReserva} reserva={OS?.reserva} />
            </div>
            <hr />
            <div className=" flex items-center justify-end gap-x-6 p-8 bg-gray-100">
                <button type="button" className="text-sm font-semibold leading-6 text-gray-900" onClick={() => { navigate("/") }}>
                    Cancelar
                </button>
                <button
                    type="submit"
                    onClick={envia}
                    className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                >
                    Salvar
                </button>
            </div>
        </div>
    )
}

const formatCPF = (inputCPF: string): string => {
    const cpf = inputCPF.replace(/\D/g, ''); // Remove caracteres não numéricos
    if (cpf.length === 11) {
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    } else if (cpf.length === 14) {
        return cpf.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
    } else {
        return cpf;
    }
};

const formatTelefone = (inputTelefone: string): string => {
    const telefone = inputTelefone.replace(/\D/g, ''); // Remove caracteres não numéricos
    const formattedTelefone = telefone.replace(/(\d{2})(\d{4,5})(\d{4})/, '($1) $2-$3');
    return formattedTelefone;
};