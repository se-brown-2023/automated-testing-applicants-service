import React, {useEffect, useState} from 'react';
import { useParams } from 'react-router-dom';
import { ExamSessionApi } from "../../api-backend-conduct/api";
import { Accept } from '../Accept/Accept';
import { FinishExam } from '../FinishExam/FinishExam';
import Examinee from '../Examinee/Examinee';

const Exam = () => {
    const { examSessionId } = useParams();
    const apiInstance = new ExamSessionApi();
    const [status, setStatus] = useState(null);

    useEffect(() => {
        apiInstance.apiExamSessionExamSessionIdGet(examSessionId)
            .then((response) => {
                setStatus(response.data.status);
            })
            .catch((error) => {
                console.error('Failed to fetch exam session:', error);
            });
    }, [examSessionId, apiInstance]);

    if (status === 'CREATED') {
        return <Accept examSessionId={examSessionId} />;
    } else if (status === 'STARTED') {
        return <Examinee examSessionId={examSessionId} />;
    } else if (status === 'FINISHED') {
        return <FinishExam />;
    } else {
        return <div>Loading...</div>;
    }
};

export default Exam;