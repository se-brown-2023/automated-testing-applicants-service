import { configureStore } from '@reduxjs/toolkit';
import tasksReducer from './tasksSlice';
import examSessionsReducer from './examSessionsSlice';
import usersReducer from './usersSlice';
import examsReducer from './examsSlice';

const store = configureStore({
    reducer: {
        tasks: tasksReducer,
        examSessions: examSessionsReducer,
        users: usersReducer,
        exams: examsReducer, // add the exams reducer
    },
});

export default store;