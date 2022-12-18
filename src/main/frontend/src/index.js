import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createStore} from "redux";
import RootReducer from "./util/RootReducer";
import {Provider} from "react-redux";
import {persistStore} from "redux-persist";
import {PersistGate} from "redux-persist/integration/react";
import store from "./util/RootReducer";
import {CookiesProvider} from "react-cookie";

const root = ReactDOM.createRoot(document.getElementById('root'));
let persistor=persistStore(store)
/*const store=createStore(store);*/

root.render(
    <Provider store={store}>
        <PersistGate persistor={persistor}>
        <CookiesProvider>
            {/*<React.StrictMode>*/}
                <App />
            {/*</React.StrictMode>*/}
        </CookiesProvider>
        </PersistGate>
    </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
