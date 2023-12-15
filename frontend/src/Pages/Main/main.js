import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { Table, Header, HeaderRow, Body, Row, HeaderCell, Cell } from '@table-library/react-table-library/table';
import { useTheme } from '@table-library/react-table-library/theme';
import { getTheme } from '@table-library/react-table-library/baseline';
import './main.css';
import Avatar from './photo_2023-08-21_10-55-05.jpg';
import Modal from './modal';

export const Main = () => {
    const dispatch = useDispatch();
    const tasks = useSelector(state => state.tasks);
    const users = useSelector(state => state.users);
    const examSessions = useSelector(state => state.examSessions);
    const navigate = useNavigate();
    const [expandedRowId, setExpandedRowId] = useState(null);
    const [selectedVacancy, setSelectedVacancy] = useState(null);
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [selectedExamSession, setSelectedExamSession] = useState(null)

    const handleToggleExpansion = (rowId) => {
        setExpandedRowId(rowId === expandedRowId ? null : rowId);
    };

    const handleOpenModal = (vacancy) => {
        setSelectedVacancy(vacancy);
    };

    const handleCloseModal = () => {
        setSelectedVacancy(null);
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
                        <img className="avatar" src={Avatar} alt="User Avatar" />
                        <div>Username</div>
                    </div>
                    <div className="text-wrapper-container">
                        <div className="text-wrapper">Система тестирования соискателей</div>
                    </div>
                    <button className="assign-button" onClick={handleCreateSession}>
                        Создать экзамен
                    </button>
                </div>
                <div className="mainSection">
                    <Table data={{ nodes: examSessions }} theme={theme} className="table">
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
                                                <Cell>{item.user?.name}</Cell>
                                                <Cell>{item.user?.email}</Cell>
                                                <Cell>{item.testResults}</Cell>
                                            </Row>
                                            {item.id === expandedRowId && (
                                                <Row item={item}>
                                                    <tr style={{ display: "flex", gridColumn: "1 / -1", margin: "0 5px 10px 5px"}}>
                                                        <td style={{ flex: "1", border: "0"}}>
                                                            <ul>
                                                                <li>
                                                                    <strong>Name:</strong> {item.name}
                                                                </li>
                                                                <li>
                                                                    <strong>Email:</strong> {item.email}
                                                                </li>
                                                                <li>
                                                                    <strong>Описание:</strong> {item.description}
                                                                </li>
                                                                <li>
                                                                    <strong>Язык:</strong> {item.language}
                                                                </li>
                                                                <li>
                                                                    <strong>Дата создания:</strong> {item.creationDate}
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
                <Modal vacancy={selectedVacancy} onClose={handleCloseModal} selectedTasks={selectedTasks} examSession={selectedExamSession} />
            )}
        </div>
    );
};