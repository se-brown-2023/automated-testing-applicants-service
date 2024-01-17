import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {useNavigate} from 'react-router-dom';
import {Body, Cell, Header, HeaderCell, HeaderRow, Row, Table} from '@table-library/react-table-library/table';
import {useTheme} from '@table-library/react-table-library/theme';
import {getTheme} from '@table-library/react-table-library/baseline';
import './MainPage.css';
import Avatar from './user.png';
import MainModal from './MainModal';

export const MainPage = () => {
    const examSessions = useSelector(state => state.examSessions);
    const navigate = useNavigate();
    const [expandedRowId, setExpandedRowId] = useState(null);
    const [selectedExamSession, setSelectedExamSession] = useState(null)
    const [isRatingMode, setIsRatingMode] = useState(true);

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
                    <Table data={{nodes: examSessions}} theme={theme} className="table">
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
                                                <Cell>Имя</Cell>
                                                <Cell>example@mail.ru</Cell>
                                                <Cell>9/10</Cell>
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
                                                                    <strong>Имя:</strong> Имя
                                                                </li>
                                                                <li>
                                                                    <strong>Email:</strong> example@mail.ru
                                                                </li>
                                                                <li>
                                                                    <strong>Описание:</strong> Описание
                                                                </li>
                                                                <li>
                                                                    <strong>Язык:</strong> Java
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