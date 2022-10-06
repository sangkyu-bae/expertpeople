import React from 'react';
import './WorkSetting.css';
import WorkSettingHead from "./WorkSettingHead";
import NewWorkAlert from "../NewWork/NewWorkAlert";

function WorkSetting(props) {
    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co flex">
                <WorkSettingHead workNavCheck='setting' settingNavCheck='info'></WorkSettingHead>
            </div>
        </div>
    );
}

export default WorkSetting;