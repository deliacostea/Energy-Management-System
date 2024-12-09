// src/components/loginComp/LoginPage.js
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom'; 
import './LoginPage.css';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await axios.post('http://user.localhost/useri/login', {
                email,
                password,
            });

            if (response.status === 200) {
                const userData = response.data;
                localStorage.setItem('user', JSON.stringify(userData));

                
                if (userData.user.role === 'admin') {
                    navigate('/admin');
                } else {
                    navigate('/user');
                }
            }
        } catch (err) {
            setError("E-mail sau parolă incorectă");
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                <button type="submit">Login</button>
            </form>
            <p className="register-link">
                Don’t have an account? <Link to="/register">Register here</Link>
            </p>
        </div>
    );
};

export default LoginPage;
