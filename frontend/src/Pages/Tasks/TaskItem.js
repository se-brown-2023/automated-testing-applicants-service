import React from 'react';
import './TaskItem.css';

const TaskItem = ({ task, onTaskSelect, onTaskClick, selectedTasks }) => {
    return (
        <div className="task-item">
            <input type="checkbox" checked={selectedTasks.includes(task)} onChange={() => onTaskSelect(task)} />
            <div className="task-details" onClick={() => onTaskClick(task)}>
                <div className="task-title">
                    {task.title}
                </div>
            </div>
        </div>
    );
};

export default TaskItem;