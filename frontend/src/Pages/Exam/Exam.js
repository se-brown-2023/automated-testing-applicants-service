import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { createTask, updateTask } from '../../redux/tasksSlice';
import { useNavigate } from 'react-router-dom';
import { createExam } from '../../redux/examsSlice';
import './Exam.css';

const Exam = () => {
    const dispatch = useDispatch();
    const tasks = useSelector(state => state.tasks);
    const exams = useSelector(state => state.exams);
    const navigate = useNavigate();
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => setModalIsOpen(true);
    const closeModal = () => setModalIsOpen(false);

    const handleCreateExam = () => {
        const newExam = {
            id: Date.now(),
            tasks: selectedTasks,
        };

        selectedTasks.forEach(task => {
            if (!tasks.find(t => t.id === task.id)) {
                dispatch(createTask(task));
            }
        });

        dispatch(createExam(newExam));
        setSelectedTasks([]);
        closeModal();
    };

    const handleTaskSelect = (task) => {
        if (selectedTasks.includes(task)) {
            setSelectedTasks(selectedTasks.filter(t => t !== task));
        } else {
            if (tasks.find(t => t.id === task.id)) {
                dispatch(updateTask(task));
            }
            setSelectedTasks([...selectedTasks, task]);
        }
    };

    return (
        <div className="exam-screen">
            <div className="exam-list-container">
                <div className="exam-list-header">Exams</div>
                {exams.map(exam => (
                    <div key={exam.id} className="exam-item">
                        <div className="exam-title">
                            Exam {exam.id}
                        </div>
                        <div className="exam-tasks">
                            {exam.tasks.map(task => task.title).join(', ')}
                        </div>
                    </div>
                ))}
                <button className="create-exam-button" onClick={openModal}>
                    Create New Exam
                </button>
                {modalIsOpen && (
                    <div className="modal-exam" onClick={closeModal}>
                        <div className="modal-exam-content" onClick={(e) => e.stopPropagation()}>
                            <div className="modal-exam-header">
                                <h2>Create New Exam</h2>
                                <span className="close" onClick={closeModal}>
                                    &times;
                                </span>
                            </div>
                            <div className="modal-exam-body">
                                {tasks.map(task => (
                                    <div key={task.id} className="task-item">
                                        <input type="checkbox" checked={selectedTasks.includes(task)} onChange={() => handleTaskSelect(task)} />
                                        <div className="task-title">
                                            {task.title}
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <div className="modal-exam-footer">
                                <button onClick={handleCreateExam}>Create Exam</button>
                                <button onClick={() => navigate('/tasks')}>Create New Task</button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Exam;