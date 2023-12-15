import { createSlice } from '@reduxjs/toolkit';

const examSessionsSlice = createSlice({
    name: 'examSessions',
    initialState: [],
    reducers: {
        createExamSession: (state, action) => {
            state.push(action.payload);
        },
    },
});

export const { createExamSession } = examSessionsSlice.actions;

export default examSessionsSlice.reducer;