import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import {Login} from './Pages/Login/Login';
import {MainPage} from './Pages/Main/MainPage';
import TaskList from './Pages/Tasks/TaskList';
import ExamSession from "./Pages/ExamSession/ExamSession";
import {Accept} from "./Pages/Accept/Accept";
import Examinee from "./Pages/Examinee/Examinee";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/main" element={<MainPage/>}/>
                <Route path="/exam-session" element={<ExamSession/>}/>
                <Route path="/tasks" element={<TaskList/>}/>
                <Route path="/exam-start" element={<Accept/>}/>
                <Route path="/examinee" element={<Examinee/>}/>
                <Route path="/" element={<Login/>}/>
            </Routes>
        </Router>
    );
}

export default App;