import React from 'react';
import './WorkSetting.css';
import WorkSettingHead from "./WorkSettingHead";

function WorkSetting(props) {
    return (
        <div className="container ">
            <div className="container-wrap nw-co flex">
                <WorkSettingHead workNavCheck='setting' settingNavCheck='info'></WorkSettingHead>
            </div>
        </div>
    );
}

export default WorkSetting;