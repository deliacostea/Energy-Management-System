import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './UserDashboard.css';

const UserDashboard = () => {
    const [devices, setDevices] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchDevices = async () => {
            try {
                const userData = JSON.parse(localStorage.getItem('user'));

                if (!userData || userData.user.role !== 'client') {
                    navigate('/'); 
                    return;
                }

               
                const response = await axios.get('http://device.localhost/devices');
                
               
                const userDevices = response.data.filter(device => device.userId === userData.user.id);
                setDevices(userDevices);
            } catch (error) {
                console.error("Eroare la încărcarea dispozitivelor:", error);
            }
        };

        fetchDevices();
    }, [navigate]);

    return (
        <div className="user-dashboard">
            <h1>Dispozitivele Mele</h1>
            <table className="device-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descriere</th>
                        <th>Adresă</th>
                        <th>Consum Maxim</th>
                    </tr>
                </thead>
                <tbody>
                    {devices.map((device) => (
                        <tr key={device.id}>
                            <td>{device.id}</td>
                            <td>{device.description}</td>
                            <td>{device.address}</td>
                            <td>{device.maxHourlyConsumption} kWh</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserDashboard;
