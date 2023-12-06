import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Login } from './Pages/Login/login';
import { Main } from './Pages/Main/main';
import TaskList from './Pages/Tasks/TaskList';

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/main" element={<Main />} />
          <Route path="/tasks" element={<TaskList />} />
          <Route path="/" element={<Login />} />
        </Routes>
      </Router>
  );
}

export default App;