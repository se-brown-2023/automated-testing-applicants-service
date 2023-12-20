import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Accept.css';

export const Accept = () => {
    const navigate = useNavigate();
    const examTime = 60;

    const handleStartExam = () => {
        navigate('/examinee');
    }

    return (
        <div className="exam-start-page">
            <h1>Начало экзамена</h1>
            <p>Время, выделенное на экзамен: {examTime} минут</p>
            <button onClick={handleStartExam}>Начать экзамен</button>
        </div>
    );
}