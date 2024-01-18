import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Body, Cell, Header, HeaderCell, HeaderRow, Row, Table} from '@table-library/react-table-library/table';
import {useTheme} from '@table-library/react-table-library/theme';
import {getTheme} from '@table-library/react-table-library/baseline';
import './MainPage.css';
import Avatar from './user.png';
import MainModal from './MainModal';
import {ExamApi, ExamSessionApi, SubmissionApi} from "../../api-backend-manage";

export const MainPage = () => {
    const [examSessions, setExamSessions] = useState({nodes: []})
    const navigate = useNavigate();
    const [expandedRowId, setExpandedRowId] = useState(null);
    const [selectedExamSession, setSelectedExamSession] = useState(null)
    const [isRatingMode, setIsRatingMode] = useState(true);

    const examSessionApi = new ExamSessionApi();
    const submissionApi = new SubmissionApi();
    const examApi = new ExamApi();

    useEffect(() => {
        examSessionApi.getAllExamSessions().then(r => {
            const newExamSessionsPromises = r.data.map(async examSession => {
                const submissionsPromise = submissionApi.getSubmissionsByExamSessionId(examSession.id).then(r => {
                    let passedTotal = 0;
                    let total = 0;
                    r.data.forEach(submission => {
                        passedTotal += submission.testResults.filter(it => it.passed).length;
                        total += submission.testResults.length;
                    });
                    return {
                        passedTotal: passedTotal,
                        total: total,
                        submissions: r.data
                    };
                })
                const examPromise = examApi.getExam(examSession.exam_id).then(r => {
                    return r.data
                });
                const promises = await Promise.all([submissionsPromise, examPromise]);
                const submissions = promises[0];
                const exam = promises[1];
                return {
                    id: examSession.id,
                    status: examSession.status,
                    exam: exam,
                    examinee: examSession.examinee,
                    startTime: examSession.start_time_stamp,
                    endTime: examSession.finish_time_stamp,
                    submissions: submissions
                };
            });
            Promise.all(newExamSessionsPromises).then(r => {
                setExamSessions({nodes: r})
            })
        })
    }, []);

    const handleToggleExpansion = (rowId) => {
        setExpandedRowId(rowId === expandedRowId ? null : rowId);
    };

    const handleOpenModal = (examSession) => {
        setSelectedExamSession(examSession);
    };

    const handleCloseModal = () => {
        setSelectedExamSession(null);
    };

    const theme = useTheme(getTheme());

    const handleCreateSession = () => {
        navigate('/exam-session');
    };

    return (
        <div>
            <div className="main-screen">
                <div className="main-screen-header">
                    <div className="user-info">
                        <img className="avatar" src={Avatar} alt="User Avatar"/>
                        <div>Username</div>
                    </div>
                    <div className="text-wrapper-container">
                        <div className="text-wrapper">Система тестирования соискателей</div>
                    </div>
                    <div className="button-container">
                        <button className="assign-button" onClick={handleCreateSession}>
                            Создать экзамен
                        </button>
                    </div>
                </div>
                <div className="mainSection">
                    <Table data={examSessions} theme={theme} className="table">
                        {(tableList) => (
                            <>
                                <Header>
                                    <HeaderRow>
                                        <HeaderCell>Имя экзаменуемого</HeaderCell>
                                        <HeaderCell>Почта</HeaderCell>
                                        <HeaderCell>Результаты тестов</HeaderCell>
                                    </HeaderRow>
                                </Header>
                                <Body>
                                    {tableList.map((item) => (
                                        <React.Fragment key={item.id}>
                                            <Row
                                                item={item}
                                                onClick={() => handleToggleExpansion(item.id)}
                                            >
                                                <Cell>{item.examinee.first_name}</Cell>
                                                <Cell>{item.examinee.email}</Cell>
                                                <Cell>{item.submissions.passedTotal} / {item.submissions.total}</Cell>
                                            </Row>
                                            {item.id === expandedRowId && (
                                                <Row item={item}>
                                                    <tr style={{
                                                        display: "flex",
                                                        gridColumn: "1 / -1",
                                                        margin: "0 5px 10px 5px"
                                                    }}>
                                                        <td style={{flex: "1", border: "0"}}>
                                                            <ul>
                                                                <li>
                                                                    <strong>session id:</strong> {item.id}
                                                                </li>
                                                                <li>
                                                                    <strong>Имя:</strong> {item.examinee.first_name} {item.examinee.last_name}
                                                                </li>
                                                                <li>
                                                                    <strong>Email:</strong> {item.examinee.email}
                                                                </li>
                                                                <li>
                                                                    <strong>Описание:</strong> {item.exam.description}
                                                                </li>
                                                                <li>
                                                                    <strong>Язык:</strong> {item.exam.programming_language}
                                                                </li>
                                                                <li>
                                                                    <strong>Статус:</strong> {item.status}
                                                                </li>
                                                                <li>
                                                                    <strong>Время начала:</strong> {item.startTime}
                                                                </li>
                                                                <li>
                                                                    <strong>Время окончания:</strong> {item.endTime}
                                                                </li>
                                                            </ul>
                                                            <button onClick={() => handleOpenModal(item)}>
                                                                Посмотреть подробнее
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </Row>
                                            )}
                                        </React.Fragment>
                                    ))}
                                </Body>
                            </>
                        )}
                    </Table>
                </div>
            </div>
            {selectedExamSession && (
                <MainModal
                    onClose={handleCloseModal}
                    examSession={selectedExamSession}
                    isRatingMode={isRatingMode}
                    setIsRatingMode={setIsRatingMode}
                />
            )}
        </div>
    );
};