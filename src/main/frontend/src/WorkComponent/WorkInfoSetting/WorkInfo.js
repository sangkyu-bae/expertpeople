import React, {useState} from 'react';
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import SettingNav from "../WorkCommonComponet/SettingNav";
import {useParams} from "react-router-dom";
import './WorkInfo.css';
import OpenBox from "./WorkInfoComponent/OpenBox";
import JobOpenningBox from "./WorkInfoComponent/JobOpenningBox";
import UpdateUrlBox from "./WorkInfoComponent/UpdateUrlBox";
import UpdateTitleBox from "./WorkInfoComponent/UpdateTitleBox";
import RemoveWorkBox from "./WorkInfoComponent/RemoveWorkBox";

function WorkInfo(props) {
    const path=useParams()
    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co">
                <div>
                    <WorkHead></WorkHead>
                    <WorkNav check='setting'></WorkNav>
                </div>

                <div className='flex container-flex'>
                    <SettingNav  path={path}></SettingNav>
                    <div className='work-setting-content'>
                        <div className='work-info-box'>
                            <OpenBox path={path}></OpenBox>
                        </div>
                        <div className='work-info-box mg-top'>
                            <JobOpenningBox path={path}></JobOpenningBox>
                        </div>
                        <div className='work-info-box mg-top ale'>
                           <UpdateUrlBox path={path}></UpdateUrlBox>
                        </div>
                        <div className='work-info-box mg-top ale'>
                           <UpdateTitleBox path={path}></UpdateTitleBox>
                        </div>
                        <div  className='work-info-box mg-top'>
                            <RemoveWorkBox path={path}></RemoveWorkBox>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
}

export default WorkInfo;

