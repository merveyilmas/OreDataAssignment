import React, { useRef } from 'react';
import { Menu } from 'primereact/menu';
import { useNavigate, Link } from "react-router-dom";


export default function LeftMenu() {

    const navigate = useNavigate();

    const itemsAdmin = [
        {
            label: 'TRANSACTIONS',
            items: [
                {
                    label: 'Accounts',
                    icon: 'pi pi-wallet',
                    command: () => {
                        navigate('/home/my-accounts');
                    }
                },
                {
                    label: 'Money Transfers',
                    icon: 'pi pi-money-bill',
                    command: () => {
                        navigate('/home/money-transfers');
                    }
                },
                {
                    label: 'Transaction History',
                    icon: 'pi pi-money-bill',
                    command: () => {
                        navigate('/home/transaction-history');
                    }
                },
                

            ]
        }
    ];   

    return (
        <div className="card flex justify-content-center ">
            <Menu model={itemsAdmin} style={{ width: "90%" }} />
        </div>
    )
}
