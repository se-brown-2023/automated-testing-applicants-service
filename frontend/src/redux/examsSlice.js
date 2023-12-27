import { createSlice } from '@reduxjs/toolkit';

const examsSlice = createSlice({
    name: 'exams',
    initialState: [],
    reducers: {
        createExam: (state, action) => {
            state.push(action.payload);
        },
    },
});

export const { createExam } = examsSlice.actions;

export default examsSlice.reducer;