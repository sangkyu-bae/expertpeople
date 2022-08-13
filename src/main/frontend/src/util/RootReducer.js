import {combineReducers} from "redux";
import userReducer from "./Redux/userReducer";

const RootReducer =combineReducers({
    userReducer
});

export default RootReducer;