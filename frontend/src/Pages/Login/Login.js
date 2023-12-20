import React from "react"
import { useNavigate } from 'react-router-dom';
import "./Login.css";

export const Login = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigate('/main');
    };

    return (
        <div className="screen">
            <div className="header">
                <div className="text-wrapper">Система тестирования соискателей</div>
            </div>
            <div className="loginSection">
                <div className="login-form">
                        <div className="text-wrapper-2">Вход в систему</div>
                        <div className="login">
                            <div className="text-wrapper-3">Логин</div>
                            <input className="rectangle-2" />
                        </div>
                        <div className="password">
                            <div className="text-wrapper-4">Пароль</div>
                            <input className="rectangle-3" />
                        </div>
                        <button className="submit" onClick={handleLogin}>
                                <div className="text-wrapper-5">Вход</div>
                        </button>
                </div>
            </div>
        </div>
    );
};