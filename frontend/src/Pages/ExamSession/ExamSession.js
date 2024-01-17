import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {createExamSession} from '../../redux/examSessionsSlice';
import {createExam} from '../../redux/examsSlice';
import {useNavigate} from 'react-router-dom';
import './ExamSession.css';

const ExamSession = () => {
    const dispatch = useDispatch();
    const tasks = useSelector(state => state.tasks);
    const exams = useSelector(state => state.exams);
    const users = useSelector(state => state.users);
    const navigate = useNavigate();
    const [selectedExams, setSelectedExams] = useState([]);
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const openModal = () => setModalIsOpen(true);
    const closeModal = () => setModalIsOpen(false);

    const handleCreateExamSession = () => {
        const newExamSession = {
            id: Date.now(),
            exams: selectedExams,
            user: selectedUser,
            startTime,
            endTime,
        };
        dispatch(createExamSession(newExamSession));
        setSelectedExams([]);
        setSelectedUser(null);
        setStartTime('');
        setEndTime('');
    };

    const handleExamSelect = (exam) => {
        if (selectedExams.includes(exam)) {
            setSelectedExams(selectedExams.filter(e => e !== exam));
        } else {
            setSelectedExams([...selectedExams, exam]);
        }
    };

    const handleTaskSelect = (task) => {
        if (selectedTasks.includes(task)) {
            setSelectedTasks(selectedTasks.filter(t => t !== task));
        } else {
            setSelectedTasks([...selectedTasks, task]);
        }
    };

    const handleCreateExam = () => {
        const newExam = {
            id: Date.now(),
            tasks: selectedTasks,
        };
        dispatch(createExam(newExam));
        setSelectedTasks([]);
        closeModal();
    };

    const handleBack = () => {
        navigate('/main');
    };

    return (
        <div className="exam-session-screen">
            <div className="exam-session-container">
                <h1 className="exam-session-header">Экзаменационные сессии</h1>
                <div className="exam-session-form">
                    <h2>Создать новую экзаменационную сессию</h2>
                    <select className="user-select" onChange={e => setSelectedUser(e.target.value)}>
                        <option>Выбрать пользователя</option>
                        {users.map(user => (
                            <option key={user.id} value={user.id}>{user.name}</option>
                        ))}
                    </select>
                    <div className="exam-session-content">
                        {exams.map((exam, index) => (
                            <div key={index} className="exam-item">
                                <input type="checkbox" checked={selectedExams.includes(exam)}
                                       onChange={() => handleExamSelect(exam)}/>
                                {exam.tasks.map(task => (
                                    <div key={task.id} className="task-title">
                                        {task.title}
                                    </div>
                                ))}
                            </div>
                        ))}
                    </div>
                    <div className="date-container">
                        <div className="date">
                        Начало сессии:
                            <input type="datetime-local" value={startTime}
                                   onChange={e => setStartTime(e.target.value)}/>
                        </div>
                        <div className="date">
                            Конец сессии:
                            <input type="datetime-local" value={endTime} onChange={e => setEndTime(e.target.value)}/>
                        </div>
                    </div>
                    <div className="footer-exam-session">
                        <button onClick={handleBack}>Назад</button>
                        <button onClick={handleCreateExamSession}>Создать экзаменационную сессию</button>
                        <button onClick={openModal}>Создать экзамен</button>
                    </div>
                </div>
            </div>
            {modalIsOpen && (
                <div className="modal-exam-session" onClick={closeModal}>
                    <div className="modal-content-exam-session" onClick={(e) => e.stopPropagation()}>
                        <div className="modal-header-exam-session">
                            <h2>Создать экзамен</h2>
                            <span className="close" onClick={closeModal}>
                                &times;
                            </span>
                        </div>
                        <div className="modal-body-exam-session">
                            {tasks.map(task => (
                                <div key={task.id} className="exam-task-item">
                                    <input type="checkbox" checked={selectedTasks.includes(task)}
                                           onChange={() => handleTaskSelect(task)}/>
                                    <div className="task-title">
                                        {task.title}
                                    </div>
                                </div>
                            ))}
                        </div>
                        <div className="modal-footer-exam-session">
                            <button onClick={() => navigate('/tasks')}>Создать новое задание</button>
                            <button onClick={handleCreateExam}>Создать экзамен</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ExamSession;