import React, {useEffect, useState} from 'react';
import './TaskModal.css';

const TaskModal = ({isOpen, closeModal, createTask, updateTask, task}) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [code, setCode] = useState('');
    const [tests, setTests] = useState([{input: '', output: ''}]);
    const [activePage, setActivePage] = useState(0);
    const [language, setLanguage] = useState('java')
    const [duration, setDuration] = useState('');

    useEffect(() => {
        if (task) {
            setTitle(task.title);
            setDescription(task.description);
            setCode(task.code);
            setTests(task.tests);
        } else {
            setTitle('');
            setDescription('');
            setCode('');
            setTests([{input: '', output: ''}]);
        }
    }, [task]);

    const handleCreateTask = () => {
        if (!task) {
            const newTask = {
                id: Date.now(),
                title,
                description,
                code,
                tests,
                language,
                duration,
            };
            createTask(newTask);
        } else {
            const updatedTask = {
                ...task,
                title,
                description,
                code,
                tests,
                language,
                duration,
            };
            updateTask(updatedTask);
        }
        closeModal();
    };

    const handleAddTest = () => {
        setTests([...tests, {input: '', output: ''}]);
    };

    const handleRemoveTest = (index) => {
        setTests(tests.filter((test, i) => i !== index));
    };

    const handleTestChange = (index, field, value) => {
        const newTests = [...tests];
        newTests[index][field] = value;
        setTests(newTests);
    };

    const handlePageChange = (pageIndex) => {
        setActivePage(pageIndex);
    };

    return (
        <div className={`modal-task ${isOpen ? '' : 'hidden'}`} onClick={closeModal}>
            <div className="modal-content-task" onClick={(e) => e.stopPropagation()}>
                <div className="modal-header-task">
                    <h2>{task ? 'Редактировать задание' : 'Создать новое задание'}</h2>
                    <span className="close" onClick={closeModal}>
                        &times;
                    </span>
                </div>
                <div className="modal-body-task">
                    {activePage === 0 && (
                        <div className="content-task">
                            <label>
                                Название:
                                <input type="text" value={title} onChange={e => setTitle(e.target.value)}/>
                            </label>
                            <label>
                                Описание:
                                <textarea value={description} onChange={e => setDescription(e.target.value)}/>
                            </label>
                            <label>
                                Язык:
                                <select className="select-language" value={language}
                                        onChange={e => setLanguage(e.target.value)}>
                                    <option value="java">Java</option>
                                    <option value="javascript">JavaScript</option>
                                </select>
                            </label>
                            <label>
                                Длительность экзамена:
                                <input type="text" value={duration} onChange={e => setDuration(e.target.value)}/>
                            </label>
                            <label>
                                Код задания:
                                <textarea className="code" value={code} onChange={e => setCode(e.target.value)}/>
                            </label>
                        </div>
                    )}
                    {activePage === 1 && (
                        <div className="content-task-modal">
                        {tests.map((test, index) => (
                                <div key={index} className="test-label">
                                    <label>
                                        Ввод:
                                        <input type="text" value={test.input}
                                               onChange={e => handleTestChange(index, 'input', e.target.value)}/>
                                    </label>
                                    <label>
                                        Вывод:
                                        <input type="text" value={test.output}
                                               onChange={e => handleTestChange(index, 'output', e.target.value)}/>
                                    </label>
                                    <button onClick={() => handleRemoveTest(index)}>Удалить тест</button>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
                <div className="modal-footer-task">
                    {activePage === 0 ? (
                        <button onClick={() => handlePageChange(1)}>Перейти к тестам</button>
                    ) : (
                        <>
                            <button onClick={() => handlePageChange(0)}>Вернуться к заданию</button>
                            <button onClick={handleAddTest}>Добавить тест</button>
                        </>
                    )}
                    <button onClick={handleCreateTask}>{task ? 'Сохранить изменения' : 'Создать задание'}</button>
                </div>
            </div>
        </div>
    );
};

export default TaskModal;