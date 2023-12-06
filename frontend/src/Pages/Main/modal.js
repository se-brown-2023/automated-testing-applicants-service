import React, { useState } from "react";
import "./modal.css";

const Modal = ({ vacancy, onClose, selectedTasks }) => {
    const [rating, setRating] = useState(0);
    const [isRatingMode, setIsRatingMode] = useState(true);

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
                <div className="modal-body">
                    <p>Текст задания</p>
                    <textarea
                        value={selectedTasks.map(task => task.title).join(', ')}
                        rows={10}
                        cols={50}
                    />
                </div>
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

export default Modal;
