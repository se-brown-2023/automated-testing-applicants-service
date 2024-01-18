import React, {useEffect, useState} from "react";
import "./MainModal.css";
import {TaskApi} from "../../api-backend-manage";

const MainModal = ({ onClose, examSession, isRatingMode, setIsRatingMode }) => {
    const [rating, setRating] = useState(0);
    const [tasks, setTasks] = useState([]);
    const taskApi = new TaskApi();

    useEffect(() => {
        const promises = examSession.submissions.submissions.map(submission => {
            return taskApi.getTask(submission.task_id).then(r => {
                return r.data
            })
        })
        Promise.all(promises).then(tasks => {
            setTasks(tasks)
        })
    }, [])

    const handleRatingChange = (event) => {
        setRating(event.target.value);
    };

    const handleRatingSubmit = () => {
        setIsRatingMode(false);
        console.log("Submitted Rating:", rating);
    };

    const handleEditRating = () => {
        setIsRatingMode(true);
    };

    return (
        <div className="modal" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="modal-header">
                    <h2>Задание</h2>
                    <span className="close" onClick={onClose}>
                        &times;
                    </span>
                </div>
                {tasks.map(task =>
                    (<div key={task} className="modal-body">
                        <p>{task.name} | {task.description}</p>
                        <textarea
                            value={examSession.submissions.submissions.filter(submission => submission.task_id === task.id)
                                .map(s => s.user_source_code)}
                            rows={10}
                            cols={50}
                        />
                    </div>)
                )}

                <div className="modal-footer">
                    {isRatingMode ? (
                        <>
                        <div className="rating">
                                <label htmlFor="rating">Оценка:</label>
                                <input
                                    type="number"
                                    id="rating"
                                    name="rating"
                                    min="0"
                                    max="100"
                                    value={rating}
                                    onChange={handleRatingChange}
                                />
                            </div>
                            <button onClick={handleRatingSubmit}>Оценить</button>
                        </>
                    ) : (
                        <>
                            <div className="rating">
                                <span>Оценка: {rating}</span>
                                <button onClick={handleEditRating}>Изменить оценку</button>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default MainModal;