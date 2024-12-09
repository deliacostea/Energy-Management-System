
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './AdminDashboard.css';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [devices, setDevices] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [selectedDevice, setSelectedDevice] = useState(null);
    const [showUserForm, setShowUserForm] = useState(false);
    const [showDeviceForm, setShowDeviceForm] = useState(false);
    const [isEditingUser, setIsEditingUser] = useState(false);
    const [isEditingDevice, setIsEditingDevice] = useState(false);
    const navigate = useNavigate();

    const fetchData = async () => {
        try {
            const userData = JSON.parse(localStorage.getItem('user'));
            if (userData.user.role !== 'admin') {
                navigate('/');
            }
            const usersResponse = await axios.get('http://user.localhost/admin/users');
            const devicesResponse = await axios.get('http://device.localhost/devices');
            setUsers(usersResponse.data);
            setDevices(devicesResponse.data);
        } catch (error) {
            console.error("Eroare la încărcarea datelor:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, [navigate]);

    const handleUserSubmit = async () => {
        try {
            const userToSave = {
                name: selectedUser.name,
                email: selectedUser.email,
                password: selectedUser.password,
            };
    
            if (isEditingUser) {
                await axios.put(`http://user.localhost/admin/user/update/${selectedUser.id}`, userToSave);
            } else {
                await axios.post(`http://user.localhost/admin/user`, userToSave);
            }
    
            setShowUserForm(false);
            setIsEditingUser(false);
            setSelectedUser(null);
            fetchData();
        } catch (error) {
            console.error("Eroare la procesarea utilizatorului:", error);
        }
    };
    

    const handleDeviceSubmit = async () => {
        try {
            if (isEditingDevice) {
                await axios.put(`http://device.localhost/devices/${selectedDevice.id}`, selectedDevice);
            } else {
                await axios.post(`http://device.localhost/devices`, selectedDevice);
            }
            setShowDeviceForm(false);
            setIsEditingDevice(false);
            setSelectedDevice(null);
            fetchData();
        } catch (error) {
            console.error("Eroare la procesarea dispozitivului:", error);
        }
    };

    const handleUserDelete = async () => {
        if (selectedUser) {
            try {
                await axios.delete(`http://user.localhost/admin/user/${selectedUser.id}`);
                await axios.put(`http://device.localhost/devices/detachUser/${selectedUser.id}`);
                setSelectedUser(null);
                fetchData(); 
            } catch (error) {
                console.error("Eroare la ștergerea utilizatorului:", error);
            }
        }
    };

    const handleDeviceDelete = async () => {
        if (selectedDevice) {
            try {
                await axios.delete(`http://device.localhost/devices/${selectedDevice.id}`);
                setSelectedDevice(null);
                fetchData();
            } catch (error) {
                console.error("Eroare la ștergerea dispozitivului:", error);
            }
        }
    };

    return (
        <div className="admin-dashboard">
           {/* <h1>Admin Dashboard</h1>*/}

            {/* Tabel utilizatori */}
            <section className="crud-section">
                <h2>Users</h2>
                <table className="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user) => (
                            <tr
                                key={user.id}
                                onClick={() => setSelectedUser(user)}
                                className={selectedUser?.id === user.id ? 'selected-row' : ''}
                            >
                                <td>{user.id}</td>
                                <td>{user.name}</td>
                                <td>{user.email}</td>
                                <td>{user.password}</td>
                                <td>{user.role}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="button-group">
                    <button onClick={() => { setShowUserForm(true); setIsEditingUser(false); }}>Create</button>
                    <button onClick={() => { if (selectedUser) { setShowUserForm(true); setIsEditingUser(true); } }}>Edit</button>
                    <button onClick={handleUserDelete}>Delete</button>
                </div>
                {showUserForm && (
    <div className="form-group">
        <input type="text" placeholder="Name" value={selectedUser?.name || ''} onChange={(e) => setSelectedUser({ ...selectedUser, name: e.target.value })} />
        <input type="email" placeholder="Email" value={selectedUser?.email || ''} onChange={(e) => setSelectedUser({ ...selectedUser, email: e.target.value })} />
        <input type="password" placeholder="Password" value={selectedUser?.password || ''} onChange={(e) => setSelectedUser({ ...selectedUser, password: e.target.value })} />
        <button onClick={handleUserSubmit}>Submit</button>
    </div>
)}

            </section>

            {/* Tabel dispozitive */}
            <section className="crud-section">
                <h2>Devices</h2>
                <table className="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                            <th>Address</th>
                            <th>Max Consumption</th>
                            <th>User ID</th>
                        </tr>
                    </thead>
                    <tbody>
                        {devices.map((device) => (
                            <tr
                                key={device.id}
                                onClick={() => setSelectedDevice(device)}
                                className={selectedDevice?.id === device.id ? 'selected-row' : ''}
                            >
                                <td>{device.id}</td>
                                <td>{device.description}</td>
                                <td>{device.address}</td>
                                <td>{device.maxHourlyConsumption}</td>
                                <td>{device.userId}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="button-group">
                    <button onClick={() => { setShowDeviceForm(true); setIsEditingDevice(false); }}>Create</button>
                    <button onClick={() => { if (selectedDevice) { setShowDeviceForm(true); setIsEditingDevice(true); } }}>Edit</button>
                    <button onClick={handleDeviceDelete}>Delete</button>
                </div>
                {showDeviceForm && (
                    <div className="form-group">
                        <input type="text" placeholder="Description" value={selectedDevice?.description || ''} onChange={(e) => setSelectedDevice({ ...selectedDevice, description: e.target.value })} />
                        <input type="text" placeholder="Address" value={selectedDevice?.address || ''} onChange={(e) => setSelectedDevice({ ...selectedDevice, address: e.target.value })} />
                        <input type="number" placeholder="Max Consumption" value={selectedDevice?.maxHourlyConsumption || ''} onChange={(e) => setSelectedDevice({ ...selectedDevice, maxHourlyConsumption: e.target.value })} />
                        <select value={selectedDevice?.userId || ''} onChange={(e) => setSelectedDevice({ ...selectedDevice, userId: e.target.value })}>
                            <option value="">Select User ID</option>
                            {users.map(user => (
                                <option key={user.id} value={user.id}>{user.id}</option>
                            ))}
                        </select>
                        <button onClick={handleDeviceSubmit}>Submit</button>
                    </div>
                )}
            </section>
        </div>
    );
};

export default AdminDashboard;
