import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TaskModal from './TaskModal';
import TaskItem from './TaskItem';
import './TaskList.css';

const TaskList = () => {
    const navigate = useNavigate();
    const [tasks, setTasks] = useState([]);
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);

    const openModal = () => setModalIsOpen(true);
    const closeModal = () => {
        setModalIsOpen(false);
        setSelectedTask(null); // сброс выбранного задания при закрытии модального окна
    };

    const createTask = newTask => {
        setTasks([...tasks, newTask]);
        closeModal();
    };

    const updateTask = updatedTask => {
        setTasks(tasks.map(task => task.id === updatedTask.id ? updatedTask : task));
    };

    const assignTask = task => {
        // Логика для назначения задания пользователю
        console.log(`Задание "${task.title}" назначено пользователю`);
    };

    const handleTaskClick = task => {
        setSelectedTask(task);
        openModal();
    };

    const handleTaskSelect = (task) => {
        if (selectedTasks.includes(task)) {
            setSelectedTasks(selectedTasks.filter(t => t !== task));
        } else {
            setSelectedTasks([...selectedTasks, task]);
        }
    };

    const handleUserSelect = (event) => {
        setSelectedUser(event.target.value);
    };

    const assignTasks = () => {
        console.log(`Задания "${selectedTasks.map(task => task.title).join(', ')}" назначены пользователю ${selectedUser}`);
        setSelectedTasks([]);
        setSelectedUser('');
        navigate('/main');
    };

    return (
        <div className="task-list-container">
            <div className="task-list-header">Список заданий</div>
            {tasks.map(task => (
                <TaskItem key={task.id} task={task} onTaskSelect={handleTaskSelect} onTaskClick={handleTaskClick} selectedTasks={selectedTasks} />
            ))}
            <select value={selectedUser} onChange={handleUserSelect}>
                <option value="">Выберите пользователя</option>
                <option value="User1">User1</option>
                <option value="User2">User2</option>
                <option value="User3">User3</option>
            </select>
            <button className="assign-button" onClick={assignTasks}>
                Назначить выбранные задания
            </button>
            <button className="assign-button" onClick={openModal}>
                Создать новое задание
            </button>
            <TaskModal isOpen={modalIsOpen} closeModal={closeModal} createTask={createTask} updateTask={updateTask} task={selectedTask} />
        </div>
    );
};

export default TaskList;