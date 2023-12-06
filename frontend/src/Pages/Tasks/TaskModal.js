import React, { useState, useEffect } from 'react';
import './TaskModal.css';

const TaskModal = ({ isOpen, closeModal, createTask, updateTask, task }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [code, setCode] = useState('');
    const [tests, setTests] = useState([{ input: '', output: '' }]);
    const [activePage, setActivePage] = useState(0);

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
            setTests([{ input: '', output: '' }]);
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
            };
            createTask(newTask);
        } else {
            const updatedTask = {
                ...task,
                title,
                description,
                code,
                tests,
            };
            updateTask(updatedTask);
        }
        closeModal();
    };

    const handleAddTest = () => {
        setTests([...tests, { input: '', output: '' }]);
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
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="modal-header">
                    <h2>{task ? 'Редактировать задание' : 'Создать новое задание'}</h2>
                    <span className="close" onClick={closeModal}>
                        &times;
                    </span>
                </div>
                <div className="modal-body">
                    {activePage === 0 && (
                        <div>
                            <label>
                                Название:
                                <input type="text" value={title} onChange={e => setTitle(e.target.value)} />
                            </label>
                            <label>
                                Описание:
                                <textarea value={description} onChange={e => setDescription(e.target.value)} />
                            </label>
                            <label>
                                Код задания:
                                <textarea value={code} onChange={e => setCode(e.target.value)} />
                            </label>
                            <button onClick={() => handlePageChange(1)}>Перейти к тестам</button>
                        </div>
                    )}
                    {activePage === 1 && (
                        <div>
                            <h2>Тесты</h2>
                            {tests.map((test, index) => (
                                <div key={index}>
                                    <label>
                                        Ввод:
                                        <input type="text" value={test.input} onChange={e => handleTestChange(index, 'input', e.target.value)} />
                                    </label>
                                    <label>
                                        Вывод:
                                        <input type="text" value={test.output} onChange={e => handleTestChange(index, 'output', e.target.value)} />
                                    </label>
                                    <button onClick={() => handleRemoveTest(index)}>Удалить тест</button>
                                </div>
                            ))}
                            <button onClick={handleAddTest}>Добавить тест</button>
                            <button onClick={() => handlePageChange(0)}>Вернуться к заданию</button>
                        </div>
                    )}
                </div>
                <div className="modal-footer">
                    <button onClick={handleCreateTask}>{task ? 'Сохранить изменения' : 'Создать задание'}</button>
                </div>
            </div>
        </div>
    );
};

export default TaskModal;