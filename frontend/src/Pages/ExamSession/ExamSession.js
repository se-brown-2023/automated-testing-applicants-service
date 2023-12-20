import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {createExamSession} from '../../redux/examSessionsSlice';
import {useNavigate} from 'react-router-dom';
import './ExamSession.css';

const ExamSession = () => {
    const dispatch = useDispatch();
    const tasks = useSelector(state => state.tasks);
    const examSessions = useSelector(state => state.examSessions);
    const users = useSelector(state => state.users);
    const navigate = useNavigate();
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const handleCreateExamSession = () => {
        const newExamSession = {
            id: Date.now(),
            tasks: selectedTasks,
            user: selectedUser,
            startTime,
            endTime,
        };
        dispatch(createExamSession(newExamSession));
        setSelectedTasks([]);
        setSelectedUser(null);
        setStartTime('');
        setEndTime('');
    };

    const handleCreateTask = () => {
        navigate('/tasks');
    };

    const handleBack = () => {
        navigate('/main');
    };

    return (
        <div className="exam-session-screen">
            <div className="exam-session-container">
                <h1 className="exam-session-header">Exam Sessions</h1>
                {examSessions.map(session => (
                    <div key={session.id} className="exam-session-list-item">
                        <h2>{session.user}</h2>
                        <ul className="exam-session-list">
                            {session.tasks.map(task => (
                                <li key={task.id}>{task.title}</li>
                            ))}
                        </ul>
                    </div>
                ))}
                <div className="exam-session-form">
                    <h2>Create a new exam session</h2>
                    <select onChange={e => setSelectedUser(e.target.value)}>
                        <option>Select a user</option>
                        {users.map(user => (
                            <option key={user.id} value={user.id}>{user.name}</option>
                        ))}
                    </select>
                    <select multiple
                            onChange={e => setSelectedTasks(Array.from(e.target.selectedOptions, option => tasks.find(task => task.id === Number(option.value))))}>
                        {tasks.map(task => (
                            <option key={task.id} value={task.id}>{task.title}</option>
                        ))}
                    </select>
                    <input type="datetime-local" value={startTime} onChange={e => setStartTime(e.target.value)}/>
                    <input type="datetime-local" value={endTime} onChange={e => setEndTime(e.target.value)}/>
                    <button onClick={handleCreateExamSession}>Create Exam Session</button>
                    <button onClick={handleCreateTask}>Create Task</button>
                    <button onClick={handleBack}>Назад</button>
                </div>
            </div>
        </div>
    );
};

export default ExamSession;