import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './Accept.css';
import {ExamSessionApi} from "../../api-backend-conduct";
import {toaster} from "evergreen-ui";

export const Accept = ({ examSessionId }) => {
    const navigate = useNavigate();
    const apiInstance = new ExamSessionApi();
    const [examTime, setExamTime] = useState(null);
    useEffect(() => {
        apiInstance.apiExamSessionExamSessionIdTimeGet(examSessionId)
            .then((response) => {
                setExamTime(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    useEffect(() => {

    }, []);
    const handleStartExam = () => {
        const apiInstance = new ExamSessionApi();
        apiInstance.apiExamSessionExamSessionIdStartGet(examSessionId)
            .then(() => navigate('/examinee'))
            .catch(() => toaster.danger('Не удалось начать экзамен'));
    }

    return (
        <div className="exam-start-page">
            <h1>Начало экзамена</h1>
            <p>Время, выделенное на экзамен: {examTime} минут</p>
            <button onClick={handleStartExam}>Начать экзамен</button>
        </div>
    );
}
