import React from 'react';
import './TaskItem.css';

const TaskItem = ({ task, onTaskClick }) => {
    return (
        <div className="task-item" onClick={() => onTaskClick(task)}>
            <div className="task-details">
                <div className="task-title">
                    {task.title}
                </div>
            </div>
        </div>
    );
};

export default TaskItem;