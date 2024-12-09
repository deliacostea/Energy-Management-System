// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './components/loginComp/LoginPage';
import AdminDashboard from './components/adminComp/AdminDashboard';
import UserDashboard from './components/userComp/UserDashboard';
import RegisterPage from './components/loginComp/RegisterPage'; // Importă RegisterPage

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} /> {/* Adaugă ruta pentru Register */}
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/user" element={<UserDashboard />} />
            </Routes>
        </Router>
    );
}

export default App;
