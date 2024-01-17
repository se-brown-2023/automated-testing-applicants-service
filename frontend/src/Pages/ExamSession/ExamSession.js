import React, {useEffect, useState} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';
import './ExamSession.css';
import {ExamApi, ExamineeApi, ExamSessionApi, TaskApi} from "../../api-backend-manage";
import {Body, Cell, Header, HeaderCell, HeaderRow, Row, Table} from "@table-library/react-table-library/table";
import {useTheme} from "@table-library/react-table-library/theme";
import {getTheme} from "@table-library/react-table-library/baseline";

const ExamSession = () => {
    const dispatch = useDispatch();
    const [tasks, setTasks] = useState([])
    const [exams, setExams] = useState([]);
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();
    const [selectedExams, setSelectedExams] = useState([]);
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [examName, setExamName] = useState("")

    const [description, setDescription] = useState("")

    const [selectedLanguage, setSelectedLanguage] = useState("")

    const [max_duration, setMax_duration] = useState(-1)


    const handleExamNameChange = (value) => {
        setExamName(value)
    };

    const handleExamDescriptionChange = (value) => {
        setDescription(value)
    };

    const [expandedRowId, setExpandedRowId] = useState(null);

    const examApi = new ExamApi();
    const examSessionApi = new ExamSessionApi();
    const examineeApi = new ExamineeApi();
    const taskApi = new TaskApi();

    const fetchExams = () => {
        examApi.getAllExams().then(r => {
            setExams(r.data)
        })
    }

    useEffect(() => {
        examineeApi.getExaminees().then(r => {
            setUsers(r.data)
        })
        fetchExams()
        taskApi.getAllTasks().then(r => {
            setTasks(r.data)
        })
    }, [])

    const handleToggleExpansion = (rowId) => {
        setExpandedRowId(rowId === expandedRowId ? null : rowId);
    };

    const theme = useTheme(getTheme());

    const openModal = () => setModalIsOpen(true);
    const closeModal = () => setModalIsOpen(false);

    const handleCreateExamSession = () => {
        if (selectedExams.length === 0) {
            alert("Необходимо выбрать экзамен")
            return;
        }
        if (!selectedUser) {
            alert("Необходимо выбрать пользователя")
            return;
        }
        selectedExams.forEach(exam => {
            examSessionApi.createExamSession(
                {
                    exam_id: exam.id,
                    status: "CREATED",
                    examinee: users.find(u => u.id == selectedUser)
                }
            )
        })

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
        console.log(new Date() + endTime)
        if (selectedLanguage === "") {
            alert("Выберите язык экзамена")
            return
        }
        if (max_duration <= 0) {
            alert('Неверная максимальная длительность')
            return;
        }
        examApi.createExam(
            {
                examiner_id: 1,
                name: examName,
                description: description,
                programming_language: selectedLanguage,
                max_duration: max_duration * 60,
                TTL: Date.parse(endTime) - new Date(),
                tasks: selectedTasks,
                creation_date: new Date().toISOString().replace("Z", "")
            }
        ).then(r => console.log("Exam created" + r.data)).catch(e => {
            console.error("Exam not created" + e)
        })
        closeModal();
        fetchExams();
    };

    const formatDuration = (seconds) => {
        let date = new Date(0);
        date.setSeconds(seconds); // specify value for SECONDS here
        return date.toISOString().substring(11, 19);
    }

    const formatDate = (seconds) => {
        return new Date(seconds).toISOString()
    }

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
                            <option key={user.id}
                                    value={user.id}>{user.first_name} {user.last_name} | {user.email}</option>
                        ))}
                    </select>
                    <div className="exam-session-content">
                        {!modalIsOpen && (<Table data={{nodes: exams}} theme={theme} className="table">
                            {(tableList) => (
                                <>
                                    <Header>
                                        <HeaderRow>
                                            <HeaderCell></HeaderCell>
                                            <HeaderCell>Название экзамена</HeaderCell>
                                            <HeaderCell>Описание экзамена</HeaderCell>
                                            <HeaderCell>Язык</HeaderCell>
                                            <HeaderCell>Макcимальная длительность</HeaderCell>
                                            <HeaderCell>Expired at</HeaderCell>
                                        </HeaderRow>
                                    </Header>
                                    <Body>
                                        {tableList.map((item) => (
                                            <React.Fragment key={item.id}>
                                                <Row
                                                    item={item}
                                                    onClick={() => handleToggleExpansion(item.id)}
                                                    className="exam-item"
                                                >
                                                    <Cell> <input type="checkbox" checked={selectedExams.includes(item)}
                                                                  onChange={() => handleExamSelect(item)}/></Cell>
                                                    <Cell>{item.name}</Cell>
                                                    <Cell>{item.description}</Cell>
                                                    <Cell>{item.programming_language}</Cell>
                                                    <Cell>{formatDuration(item.max_duration)}</Cell>
                                                    <Cell>{formatDate(Date.parse(item.creation_date) + item.TTL + 21600000)}</Cell>
                                                </Row>
                                                {item.id === expandedRowId && (
                                                    <Row item={item} className="exam-item">
                                                        <tr style={{
                                                            display: "flex",
                                                            gridColumn: "1 / -1",
                                                            margin: "0 5px 10px 5px"
                                                        }}>
                                                            <td style={{flex: "1", border: "0"}}>
                                                                <ol>
                                                                    {item.tasks.map(task => (
                                                                        <li key={task.id}>
                                                                            <div className="task-title">
                                                                                <strong>Task
                                                                                    name: </strong>{task.name}
                                                                            </div>
                                                                            <div className="task-title">
                                                                                <strong>Task
                                                                                    description: </strong>{task.description}
                                                                            </div>

                                                                        </li>
                                                                    ))}
                                                                </ol>
                                                            </td>
                                                        </tr>
                                                    </Row>
                                                )}
                                            </React.Fragment>
                                        ))}
                                    </Body>
                                </>
                            )}
                        </Table>)}
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
                        <div className="exam-name">
                            <strong>Название экзамена: </strong>
                            <input type="text" value={examName} style={{width: "200px"}}
                                   onChange={e => handleExamNameChange(e.target.value)}/>
                        </div>
                        <div className="exam-name">
                            <strong>Описание экзамена: </strong>
                            <input type="text" value={description} style={{width: "500px"}}
                                   onChange={e => handleExamDescriptionChange(e.target.value)}/>
                        </div>
                        <div className="exam-name">
                            <select className="select-language" onChange={e => setSelectedLanguage(e.target.value)}>
                                <option>Выбрать язык</option>

                                <option key="JAVA"
                                        value="JAVA">JAVA
                                </option>
                                <option key="JAVA_SCRIPT"
                                        value="JAVA_SCRIPT">JAVA_SCRIPT
                                </option>
                            </select>
                        </div>
                        <div class="tasks-list">
                            {tasks.map(task => (
                                <div key={task.id} className="exam-task-item">
                                    <input type="checkbox" checked={selectedTasks.includes(task)}
                                           onChange={() => handleTaskSelect(task)}/>
                                    <div className="task-title">
                                        {task.name}
                                    </div>
                                </div>
                            ))}
                        </div>
                        <div className="exam-name">

                        </div>
                        <div className="exam-name">
                            <div className="date">
                                Максимальная длительность (в минутах):
                                <input type="number" value={max_duration}
                                       onChange={e => setMax_duration(Number(e.target.value))}/>
                            </div>
                            <div className="date">
                                Конец сессии:
                                <input type="datetime-local" value={endTime} style={{width: "200px"}}
                                       onChange={e => setEndTime(e.target.value)}/>
                            </div>
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