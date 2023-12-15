import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import {Login} from './Pages/Login/login';
import {Main} from './Pages/Main/main';
import TaskList from './Pages/Tasks/TaskList';
import ExamSession from "./Pages/ExamSession/ExamSession";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/main" element={<Main/>}/>
                <Route path="/exam-session" element={<ExamSession/>}/>
                <Route path="/tasks" element={<TaskList/>}/>
                <Route path="/" element={<Login/>}/>
            </Routes>
        </Router>
    );
}

export default App;