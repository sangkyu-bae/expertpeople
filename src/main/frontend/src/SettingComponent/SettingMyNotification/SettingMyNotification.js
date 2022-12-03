import React, {useEffect, useReducer, useState} from 'react';
import LeftMyProfile from "../MyProfile/LeftMyProfile";
import {Alert} from "@mui/material";
import Switch from '@mui/material/Switch';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import MainNotification from "./MainNotification";
import GetWorkNotification from "./GetWorkNotification";
import InterestWorkNotification from "./InterestWorkNotification";
import {useSelector} from "react-redux";
import axiosCo from "../../util/common/axiosCommon";
import notification from "../../NotificationComponent/Notification";

function SettingMyNotification(props) {
    const [myNotification, setMyNotification] = useState({
        init: true,
        event:false,
        notification: {

        }
    });
    useEffect(() => {
        getMyNotification();
    }, [])
    const {init,notification,event}=myNotification;
    const getMyNotification = () => {
        axiosCo.getMyNotification()
            .then(e => {
                setMyNotification({
                        ...myNotification,
                        init: false,
                        notification: e.data
                    });
            })
            .catch(e => {
                console.log(e)
            })
    }

    const updateChecked = (e) => {
        const {name,checked}=e.target;
        setMyNotification(
            {
                ...myNotification,
                event: true,
                notification: {
                    ...notification,
                    [name]:checked
                }
            }
        )
    }
    useEffect(()=>{
        if(event){
            updateMyNotification(notification)
        }
    },[notification])

    const updateMyNotification=(myNoti)=>{
        axiosCo.updateMyNotification(myNoti)
            .then(e=>{
                console.log(e.data);
            })
            .catch(e=>{
                console.log(e)
            })
    }

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <LeftMyProfile check={'notification'}></LeftMyProfile>
                    <div className="topic-wrap">
                        <div className="topic-head">
                            <h2>알림 설정</h2>
                        </div>
                        {
                            !init &&
                            <>
                                <MainNotification
                                    myNotification={notification}
                                    updateMyNotification={updateChecked}
                                ></MainNotification>
                                <GetWorkNotification
                                    myNotification={notification}
                                    updateMyNotification={updateChecked}
                                ></GetWorkNotification>
                                <InterestWorkNotification
                                    myNotification={notification}
                                    updateMyNotification={updateChecked}
                                ></InterestWorkNotification>
                            </>
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SettingMyNotification;
