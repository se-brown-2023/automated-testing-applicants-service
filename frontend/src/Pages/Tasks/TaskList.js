import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {createTask, updateTask} from '../../redux/tasksSlice';
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
        navigate('/exam-session');
    };

    return (
        <div className="task-screen">
            <div className="task-list-container">
                <div className="task-list-header">Список заданий</div>
                <div className="task-list">
                    {tasks.map(task => (
                        <TaskItem key={task.id} task={task} onTaskClick={handleTaskClick}/>
                    ))}
                </div>
                <div className="task-list-footer">
                    <button onClick={handleBack}>Назад</button>
                    <button className="assign-button" onClick={openModal}>
                        Создать новое задание
                    </button>
                </div>
                <TaskModal isOpen={modalIsOpen} closeModal={closeModal} createTask={handleCreateTask}
                           updateTask={handleUpdateTask} task={selectedTask}/>
            </div>
        </div>
    );
};

export default TaskList;