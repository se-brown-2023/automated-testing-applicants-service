import { createSlice } from '@reduxjs/toolkit';

const tasksSlice = createSlice({
    name: 'tasks',
    initialState: [],
    reducers: {
        createTask: (state, action) => {
            state.push(action.payload);
        },
        updateTask: (state, action) => {
            const index = state.findIndex(task => task.id === action.payload.id);
            if (index !== -1) {
                state[index] = action.payload;
            }
        },
    },
});

export const { createTask, updateTask } = tasksSlice.actions;

export default tasksSlice.reducer;