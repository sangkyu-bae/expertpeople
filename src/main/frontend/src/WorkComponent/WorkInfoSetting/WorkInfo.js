import React from 'react';
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import SettingNav from "../WorkCommonComponet/SettingNav";
import {useParams} from "react-router-dom";
import Tags from "@yaireo/tagify/dist/react.tagify";

function WorkInfo(props) {
    const path=useParams()

    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co">
                <div>
                    <WorkHead></WorkHead>
                    <WorkNav check='setting'></WorkNav>
                </div>

                <div className='flex'>
                    <SettingNav  path={path}></SettingNav>
                    <div className='work-setting-content'>

                    </div>
                </div>

            </div>
        </div>
    );
}

export default WorkInfo;

