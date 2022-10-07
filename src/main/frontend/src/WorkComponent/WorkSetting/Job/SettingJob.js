import React, {useCallback, useEffect, useState} from 'react';
import SettingNav from "../../WorkCommonComponet/SettingNav";
import WorkHead from "../../WorkCommonComponet/WorkHead";
import WorkNav from "../../WorkCommonComponet/WorkNav";
import {useParams} from "react-router-dom";
import Tags from "@yaireo/tagify/dist/react.tagify";
import './SettingJob.css'
import tagifySetting from "../../../SettingComponent/AttentionTopic/TagifySetting";
import axiosCo from "../../../util/common/axiosCommon";
const insertTag=e=>{
    // axiosCo.addMyJobsTags(e.detail.data.value)
    //     .then(e=>{
    //         console.log(e);
    //     }).catch(e=>{
    //     console.log(e);
    // })
}

const removeTag=e=>{
    // axiosCo.removeMyJobsTags(e.detail.data.value)
    //     .then(e=>{
    //         console.log(e)
    //     }).catch(e=>console.log(e))
}

function SettingJob(props) {
    const path = useParams();
    const tagifyCallbacks = {
        add: insertTag,
        remove: removeTag,
    };
    const [initJobs,setIniJobs]=useState([]);
    const [settings,setSettings]=useState({
        ...tagifySetting.baseTagifySettings,
        whitelist:[],
        enforceWhitelist:"test",
        callbacks: tagifyCallbacks
    })
    const {whitelist}=settings;
    useEffect(()=>{
        getJobs();
    },[])
    const getJobs=()=>{
        axiosCo.getWorkToJob(path.path)
            .then(e=>{
                setIniJobs(e.data.job);
                setSettings({
                    ...settings,
                    whitelist: e.data.allJobs,
                })
            })
            .catch(e=>console.log(e.data))
    }
    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co">
                <WorkHead></WorkHead>
                <WorkNav check='setting'></WorkNav>
                <div className='flex'>
                    <SettingNav check='job' path={path}></SettingNav>
                    <div className='work-setting-content'>
                        <div className="topic-wrap">
                            <div className="topic-head">
                                <h2>필요 직업</h2>
                            </div>
                            <div className="topic-content">
                                일에 필요한 직업을 입력해주세요. 태그를 입력하고 콤마(,)또는 엔터를 입력하세요.
                            </div>
                            <div>
                                {
                                    whitelist.length>0&&
                                    <Tags
                                        settings={settings}
                                        value={initJobs}
                                    ></Tags>
                                }
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    );
}

export default SettingJob;