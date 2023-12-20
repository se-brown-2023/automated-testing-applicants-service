import React, {useEffect, useState} from 'react';
import './Examinee.css';

const Examinee = () => {
    const tasks = ['Задание 1', 'Задание 2', 'Задание 3'];
    const [currentTask, setCurrentTask] = useState(0);
    const [code, setCode] = useState('');
    const [time, setTime] = useState(60 * 60);

    useEffect(() => {
        const timer = setInterval(() => {
            setTime((prevTime) => prevTime > 0 ? prevTime - 1 : 0);
        }, 1000);

        return () => clearInterval(timer);
    }, []);

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

    const formatTime = (time) => {
        const hours = Math.floor(time / 3600);
        const minutes = Math.floor((time % 3600) / 60);
        const seconds = time % 60;
        return `${hours}:${minutes < 10 ? '0' + minutes : minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
    };

    return (
        <div className="examinee-page">
            <h1>{tasks[currentTask]}</h1>
            <p>Текст задания</p>
            <p className="time">Оставшееся время: {formatTime(time)}</p>
            <textarea value={code} onChange={handleCodeChange}/>
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