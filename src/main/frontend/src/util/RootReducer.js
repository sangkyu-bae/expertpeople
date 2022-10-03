import {combineReducers} from "redux";
import userReducer from "./Redux/userReducer";
import {configureStore} from "@reduxjs/toolkit";
import {persistReducer} from "redux-persist";
import storage from 'redux-persist/lib/storage';

import thunk from 'redux-thunk';
import workReducer from "./Redux/workReducer";
const RootReducer =combineReducers({
    userReducer:userReducer,
    workReducer:workReducer
});

const persistConfig={
    key:'root',
    storage,
};

const persistedReducer=persistReducer(persistConfig,RootReducer);
const store = configureStore({
    reducer: persistedReducer,
    devTools: process.env.NODE_ENV !== 'production',
    middleware: [thunk],
});

export default store;