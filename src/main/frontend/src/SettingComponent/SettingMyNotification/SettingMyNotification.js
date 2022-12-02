import React from 'react';
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

function SettingMyNotification(props) {
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <LeftMyProfile check={'notification'}></LeftMyProfile>
                    <div className="topic-wrap">
                        <div className="topic-head">
                            <h2>알림 설정</h2>
                        </div>
                        <MainNotification></MainNotification>
                        <GetWorkNotification></GetWorkNotification>
                        <InterestWorkNotification></InterestWorkNotification>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SettingMyNotification;
