import { configureStore } from '@reduxjs/toolkit';
import tasksReducer from './tasksSlice';
import examSessionsReducer from './examSessionsSlice';
import usersReducer from './usersSlice';

const store = configureStore({
    reducer: {
        tasks: tasksReducer,
        examSessions: examSessionsReducer,
        users: usersReducer, // add the users reducer
    },
});

export default store;