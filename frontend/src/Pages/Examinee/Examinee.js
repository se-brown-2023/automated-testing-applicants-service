import React, { useState } from 'react';
import './Examinee.css';

const Examinee = () => {
    const tasks = ['Задание 1', 'Задание 2', 'Задание 3']; // Замените на реальные задания
    const [currentTask, setCurrentTask] = useState(0);
    const [code, setCode] = useState('');

    const handleCodeChange = (event) => {
        setCode(event.target.value);
    };

    const handleSubmit = () => {
        console.log(`Задание ${currentTask + 1} сдано с кодом: ${code}`);
        setCode('');
    };

    const handleTaskChange = (index) => {
        setCurrentTask(index);
        setCode('');
    };

    return (
        <div className="examinee-page">
            <h1>{tasks[currentTask]}</h1>
            <p>Текст задания</p>
            <textarea value={code} onChange={handleCodeChange} />
            <button onClick={handleSubmit}>Сдать задание</button>
            <div className="task-switcher">
                {tasks.map((task, index) => (
                    <button key={index} onClick={() => handleTaskChange(index)}>
                        {task}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default Examinee;