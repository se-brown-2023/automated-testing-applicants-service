
import React from 'react';
import './TaskItem.css';

const TaskItem = ({ task, onTaskClick }) => {
    return (
        <div className="task-item" onClick={() => onTaskClick(task)}>
            <div className="task-title">{task.name}</div>
            <div className="task-description">{task.description}</div>
        </div>
    );
};

export default TaskItem;