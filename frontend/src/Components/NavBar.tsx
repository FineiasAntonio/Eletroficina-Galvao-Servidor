import { Disclosure } from "@headlessui/react";
import { BellIcon } from "@heroicons/react/24/outline";
import {useEffect, useState} from "react";
import NotificationWindow from "./NotificationWindow"
import { NotificationBody } from "../Service/Entities/Notification";
import { getNotifications } from "../Service/api/OSapi";

export default function NavBar() {

    const [notificacaoWindow, setNotificacaoWindow] = useState<boolean>(false);
    const [notificacoes, setNotificacoes] = useState<NotificationBody[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const Data = await getNotifications();
                setNotificacoes(Data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    },[]);


    const navigation = [
        { name: 'Ordens de serviço', href: '/', current: true },
        { name: 'Estoque', href: '/estoque', current: false },
    ]

    const abrirNotificacao = () => {
        setNotificacaoWindow(!notificacaoWindow);
    };

    return (
        <Disclosure as="nav" className="bg-gray-800">
                <>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <div className="flex h-20 items-center justify-between">
                            <div className="flex items-center">
                                <div className="flex-shrink-0">
                                    <img
                                        className="h-20 w-20"
                                        src="src/Assets/loja_logo.png"
                                        alt="Eletroficina Galvão Logo"
                                    />
                                </div>

                                <div className="hidden md:block">
                                    <div className="ml-10 flex items-baseline space-x-4">
                                        {navigation.map((item) => (
                                            <a
                                                key={item.name}
                                                href={item.href}
                                                className='text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium '
                                            
                                                aria-current={item.current ? 'page' : undefined}
                                            >
                                                {item.name}
                                            </a>
                                        ))}
                                    </div>
                                </div>
                            </div>

                            <div className="hidden md:block">
                                <div className="flex items-center">

                                    <button
                                    onClick={abrirNotificacao}
                                        type="button"
                                        className="mr-20 relative rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
                                    >
                                        <span className="absolute -inset-1.5" />
                                        <span className="sr-only">View notifications</span>
                                        <BellIcon className="h-6 w-6" aria-hidden="true" />
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>

                    
                        {notificacaoWindow && (
                            <NotificationWindow notifications={notificacoes} setNotificacoes={setNotificacoes}/>
                        )}
            
                </>
        </Disclosure>

    )
}