import React, {useEffect, useState, useRef} from 'react';
import './TaskModal.css';
import {TaskApi, TestApi} from "../../api-backend-manage";
import {TestComponentImpl} from "../../component/TestComponentImpl";
import {EditorView, keymap} from "@codemirror/view";
import {HighlightStyle, syntaxHighlighting} from "@codemirror/language";
import {tags} from "@lezer/highlight";
import {EditorState} from "@codemirror/state";
import {basicSetup} from "codemirror";
import {indentWithTab} from "@codemirror/commands";
import {java} from "@codemirror/lang-java";

const TaskModal = ({isOpen, closeModal, task, onTaskCreated}) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [code, setCode] = useState('');
    const [tests, setTests] = useState([{input: '', output: ''}]);
    const [activePage, setActivePage] = useState(0);
    const [language, setLanguage] = useState('java')
    const [startState, setStartState] = useState(null);
    const [editorCodes, setEditorCodes] = useState({});
    const [currentTask, setCurrentTask] = useState(0);
    const codeEditorRef = useRef();

    let myTheme = EditorView.theme({
        "&": {
            color: "white",
            backgroundColor: "#252849",
            maxWidth: "80%",
            height: "40vh",
            overflow: "auto"
        },
        ".cm-content": {
            caretColor: "#0e9"
        },
        "&.cm-focused .cm-cursor": {
            borderLeftColor: "#0e9"
        },
        "&.cm-focused .cm-selectionBackground, ::selection": {
            backgroundColor: "#074"
        },
        ".cm-gutters": {
            backgroundColor: "#252838",
            color: "#ddd",
            border: "none"
        },
    }, {dark: true});

    const myHighlightStyle = HighlightStyle.define([
        {tag: tags.keyword, color: "#fc6"},
        {tag: tags.comment, color: "#f5d", fontStyle: "italic"},
        {tag: tags.atom, color: "#f0f"},
        {tag: tags.number, color: "#ff8"},
        {tag: tags.string, color: "#f0f"},
        {tag: tags.meta, color: "#f88"},
        {tag: tags.bracket, color: "#8f8"},
        {tag: tags.quote, color: "#8ff"},
        {tag: tags.link, color: "#f8f"},
        {tag: tags.name, color: "#f88"},
        {tag: tags.variableName, color: "#8ff"},
        {tag: tags.typeName, color: "#f8f"},
        {tag: tags.propertyName, color: "#8f8"},
        {tag: tags.className, color: "#f0f"},
        {tag: tags.literal, color: "#ff8"},
        {tag: tags.operator, color: "#f88"},
        {tag: tags.punctuation, color: "#8ff"},
        {tag: tags.heading, color: "#f8f"},
        {tag: tags.content, color: "#8f8"},
        {tag: tags.invalid, color: "#f00"},
    ])

    useEffect(() => {
        if (task) {
            setTitle(task.name);
            setDescription(task.description);
            setCode(task.author_source_code);

            const fetchTests = async () => {
                try {
                    const testApiInstance = new TestApi();
                    const response = await testApiInstance.getTestsForTask(task.id);
                    const formattedTests = response.data.map(test => ({
                        input: test.input_data,
                        output: test.output_data
                    }));
                    setTests(formattedTests);
                } catch (error) {
                    console.error("Ошибка при загрузке тестов: ", error);
                }
            };

            fetchTests();
        } else {
            setTitle('');
            setDescription('');
            setCode('');
            setTests([{input: '', output: ''}]);
        }
    }, [task]);

    useEffect(() => {
        if (codeEditorRef.current) {
            let state = EditorState.create({
                doc: editorCodes[activePage] || '',
                extensions: [
                    basicSetup,
                    keymap.of([indentWithTab]),
                    java(),
                    myTheme,
                    syntaxHighlighting(myHighlightStyle),
                    EditorView.updateListener.of(update => {
                        if (update.docChanged) {
                            let tr = state.update({changes: update.changes});
                            state = tr.state;
                            setStartState(state);
                        }
                    })
                ]
            });

            let editor = new EditorView({
                state: state,
                parent: codeEditorRef.current
            });

            if (task) {
                editor.setState(EditorState.create({doc: code,
                    extensions: [
                        basicSetup,
                        java(),
                        myTheme,
                        syntaxHighlighting(myHighlightStyle)
                    ]
                })
                );
            }

            return () => {
                editor.destroy();
            };
        }
    }, [code, activePage]);

    useEffect(() => {
        if (startState) {
            setEditorCodes(prevCodes => ({
                ...prevCodes,
                [activePage]: startState.doc.toString()
            }));
        }
    }, [startState, activePage]);

    const handleCreateTask = async () => {
        if (startState) {
            const currentCode = startState.doc.toString();
            const apiInstance = new TestApi();
            const testComponents = [];

            for (let test of tests) {
                const testComponent = new TestComponentImpl();
                testComponent.input_data = test.input;
                testComponent.output_data = test.output;
                const response = await apiInstance.createTest(testComponent);
                testComponents.push(response.data);
            }

            const newTask = {
                name: title,
                description: description,
                author_source_code: currentCode,
                tests: testComponents,
                language: language,
            };

            const taskApiInstance = new TaskApi();

            console.log(newTask);
            await taskApiInstance.createTask(newTask);
            closeModal();
            onTaskCreated();
        }
    };

    // const handleUpdateTask = async (updatedTask) => {
    //     const apiInstance = new TaskApi();
    //     await apiInstance.updateTask(updatedTask);
    //     closeModal();
    // };

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
                                Код задания:
                                <div ref={codeEditorRef} className="code-editor"></div>
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
                    <button onClick={handleCreateTask}>
                        {task ? 'Сохранить изменения' : 'Создать задание'}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default TaskModal;