import React, {useEffect, useRef, useState} from 'react';
import {basicSetup} from "codemirror"
import {EditorView, keymap} from "@codemirror/view"
import {indentWithTab} from "@codemirror/commands";
import {tags} from "@lezer/highlight"
import {HighlightStyle, syntaxHighlighting} from "@codemirror/language"
import {javascript} from "@codemirror/lang-javascript";
import {java} from "@codemirror/lang-java";
import {EditorState} from "@codemirror/state";
import './Examinee.css';
import {ExamSessionApi} from "../../api-backend-conduct";
import {toaster} from "evergreen-ui";
import {useNavigate} from "react-router-dom";

const Examinee = ({examSessionId}) => {

    const [currentTask, setCurrentTask] = useState(0);
    const [code, setCode] = useState('');
    const [time, setTime] = useState(null);
    const [tasks, setTasks] = useState([]);
    const [taskDescriptions, setTaskDescriptions] = useState([]);
    const [taskCodes, setTaskCodes] = useState([]);
    const [editorCodes, setEditorCodes] = useState({});
    const [startState, setStartState] = useState(null);
    const navigate = useNavigate();
    const [session, setSession] = useState(null);

    const apiInstance = new ExamSessionApi();
    const editorRef = useRef();

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

    let myTheme = EditorView.theme({
        "&": {
            color: "white",
            backgroundColor: "#252849",
            maxWidth: "100%",
            height: "60vh",
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
    }, {dark: true})

    useEffect(() => {
        if (editorRef.current) {
            let state = EditorState.create({
                doc: code,
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
                parent: editorRef.current
            });

            return () => {
                editor.destroy();
            };
        }
    }, [code]);

    useEffect(() => {
        apiInstance.apiExamSessionExamSessionIdGet(examSessionId)
            .then((response) => {
                console.log(response.data)
                setSession(response.data)
                setTasks(response.data.exam.tasks.map((task) => task));
                setTaskDescriptions(response.data.exam.tasks.map((task) => task.description));
                setTaskCodes(response.data.exam.tasks.map((task) => task.authorSourceCode));
                if (code === '') {
                    setCode(response.data.exam.tasks[0].authorSourceCode);
                }
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    useEffect(() => {
        let storedStartTime = localStorage.getItem('examStartTime');
        if (!storedStartTime) {
            storedStartTime = Date.now();
            localStorage.setItem('examStartTime', storedStartTime);
        }

        apiInstance.apiExamSessionExamSessionIdTimeGet(examSessionId)
            .then((response) => {
                const maxTime = response.data * 60;
                const elapsedTime = Math.floor((Date.now() - storedStartTime) / 1000);
                setTime(maxTime - elapsedTime);
            })
            .catch((error) => {
                console.error(error);
            });

        const timer = setInterval(() => {
            setTime((prevTime) => prevTime > 0 ? prevTime - 1 : 0);
        }, 1000);

        return () => {
            clearInterval(timer);
        };
    }, []);

    useEffect(() => {
        if (startState) {
            setEditorCodes(prevCodes => ({
                ...prevCodes,
                [currentTask]: startState.doc.toString()
            }));
        }
    }, [startState]);

    useEffect(() => {
        const handleUnload = (event) => {
            event.preventDefault();
            event.returnValue = '';
        };

        window.addEventListener('beforeunload', handleUnload);

        return () => {
            window.removeEventListener('beforeunload', handleUnload);
        };
    }, []);

    const handleTaskChange = (index) => {
        setCurrentTask(index);
        setCode(editorCodes[index] || taskCodes[index]);
    };

    const handleSubmit = async () => {
        if (startState) {
            const solution = startState.doc.toString();
            const taskId = tasks[currentTask].id;

            const sendTaskSolutionRequest = {
                submission: {
                    examSessionId: examSessionId,
                    taskId: taskId,
                    userSourceCode: solution
                }
            };

            try {
                await apiInstance.apiExamSessionExamSessionIdSendSolutionPut(examSessionId, sendTaskSolutionRequest);
                toaster.success(`Задание ${currentTask + 1} успешно сдано`);
            } catch (error) {
                toaster.danger("Ошибка при отправке решения");
            }
        }
    };

    const finishExam = async () => {
        const userConfirmation = window.confirm("Вы уверены, что хотите завершить экзамен?");
        if (userConfirmation) {
            try {
                localStorage.removeItem('examStartTime');
                await apiInstance.apiExamSessionExamSessionIdFinishGet(examSessionId);
                navigate('/exam-end');
            } catch (error) {
                toaster.danger("Ошибка при завершении экзамена");
            }
        }
    };

    const formatTime = (time) => {
        const hours = Math.floor(time / 3600);
        const minutes = Math.floor((time % 3600) / 60);
        const seconds = time % 60;
        return `${hours}:${minutes < 10 ? '0' + minutes : minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
    };

    return (
        <div className="examinee-page">
            <p className="time">Оставшееся время: {formatTime(time)}</p>
            <button className="finish-exam-button" onClick={finishExam}>Завершить экзамен</button>
            <h1>{tasks[currentTask] && tasks[currentTask].name}</h1>
            <p>{taskDescriptions[currentTask]}</p>
            <div ref={editorRef}></div>
            <button onClick={handleSubmit}>Сдать задание</button>
            <div className="task-switcher">
                {tasks.map((task, index) => (
                    <button key={index} onClick={() => handleTaskChange(index)}>
                        {task.name}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default Examinee;