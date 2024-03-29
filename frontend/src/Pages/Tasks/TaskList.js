import React, {useEffect, useState} from 'react';
import {TaskApi} from "../../api-backend-manage";
import TaskModal from './TaskModal';
import TaskItem from './TaskItem';
import './TaskList.css';
import {useNavigate} from "react-router-dom";

const TaskList = () => {
    const [tasks, setTasks] = useState([]);
    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const taskApiInstance = new TaskApi();
                const response = await taskApiInstance.getAllTasks();
                setTasks(response.data);
                console.log('Fetched tasks:', response.data);
            } catch (error) {
                console.error('Failed to fetch tasks:', error);
            }
        };

        fetchTasks();
    }, [refresh]);

    useEffect(() => {
        const fetchTasks = async () => {
            // ...
        };

        fetchTasks();
    }, [refresh]);

    const handleRefresh = () => setRefresh(!refresh);

    const openModal = () => setModalIsOpen(true);

    const closeModal = () => {
        setModalIsOpen(false);
        setSelectedTask(null);
    };

    const handleTaskClick = task => {
        setSelectedTask(task);
        openModal();
    };

    const handleBack = () => {
        navigate('/exam-session');
    };

    return (
        <div className="task-screen">
            <div className="task-list-container">
                <div className="task-list-header">Список заданий</div>
                <div className="task-list-body">
                    {tasks.map((task, index) => (
                        <TaskItem key={index} task={task} onTaskClick={handleTaskClick}/>
                    ))}
                </div>
                <div className="task-list-footer">
                    <button onClick={handleBack}>Назад</button>
                    <button className="assign-button" onClick={openModal}>
                        Создать новое задание
                    </button>
                </div>
                <TaskModal isOpen={modalIsOpen} closeModal={closeModal} task={selectedTask} onTaskCreated={handleRefresh}/>
            </div>
        </div>
    );
};

export default TaskList;