import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { createTask, updateTask } from '../../redux/tasksSlice';
import TaskModal from './TaskModal';
import TaskItem from './TaskItem';
import './TaskList.css';
import {useNavigate} from "react-router-dom";

const TaskList = () => {
    const dispatch = useDispatch();
    const tasks = useSelector(state => state.tasks);
    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);

    const openModal = () => setModalIsOpen(true);

    const closeModal = () => {
        setModalIsOpen(false);
        setSelectedTask(null);
    };

    const handleCreateTask = newTask => {
        dispatch(createTask(newTask));
        closeModal();
    };

    const handleUpdateTask = updatedTask => {
        dispatch(updateTask(updatedTask));
    };

    const handleTaskClick = task => {
        setSelectedTask(task);
        openModal();
    };

    const handleBack = () => {
        navigate('/exam-session'); // navigate back to the Main page
    };

    return (
        <div className="task-list-container">
            <div className="task-list-header">Список заданий</div>
            {tasks.map(task => (
                <TaskItem key={task.id} task={task} onTaskClick={handleTaskClick} />
            ))}
            <button className="assign-button" onClick={openModal}>
                Создать новое задание
            </button>
            <button onClick={handleBack}>Назад</button>
            <TaskModal isOpen={modalIsOpen} closeModal={closeModal} createTask={handleCreateTask} updateTask={handleUpdateTask} task={selectedTask} />
        </div>
    );
};

export default TaskList;