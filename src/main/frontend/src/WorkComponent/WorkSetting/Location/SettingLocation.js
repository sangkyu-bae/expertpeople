import React, {useEffect, useState} from 'react';
import WorkHead from "../../WorkCommonComponet/WorkHead";
import WorkNav from "../../WorkCommonComponet/WorkNav";
import SettingNav from "../../WorkCommonComponet/SettingNav";
import Tags from "@yaireo/tagify/dist/react.tagify";
import {useParams} from "react-router-dom";
import axiosCo from "../../../util/common/axiosCommon";
import tagifySetting from "../../../SettingComponent/AttentionTopic/TagifySetting";

function SettingLocation(props) {
    const path=useParams();
    const insertTag=e=>{
        axiosCo.addWorkToZone(e.detail.data.value,path.path)
            .then(e=>{
                console.log(e);
            }).catch(e=>{
            console.log(e);
        })
    }
    const removeTag=e=>{
        axiosCo.removeWorkToZone(e.detail.data.value,path.path)
            .then(e=>{
                console.log(e)
            }).catch(e=>console.log(e))
    }
    const tagifyCallbacks = {
        add: insertTag,
        remove: removeTag,
    };
    const [initZones,setIniZones]=useState([]);
    const [settings,setSettings]=useState({
        ...tagifySetting.baseTagifySettings,
        whitelist:[],
        enforceWhitelist:"test",
        callbacks: tagifyCallbacks
    })
    const {whitelist}=settings;
    useEffect(()=>{
        getZones();
    },[])
    const getZones=()=>{
        axiosCo.getWorkToZone(path.path)
            .then(e=>{
                console.log(e.data);
                setIniZones(e.data.zone);
                setSettings({
                    ...settings,
                    whitelist: e.data.allZone,
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
                    <SettingNav check='location' path={path}></SettingNav>
                    <div className='work-setting-content'>
                        <div className="topic-wrap">
                            <div className="topic-head">
                                <h2>일터지역</h2>
                            </div>
                            <div className="topic-content locations">
                               생성된 일감의 지역을 선택해 주세요. 태그를 입력하고 콤마(,)또는 엔터를 입력하세요.
                            </div>
                            <div>
                                {
                                    whitelist.length>0&&
                                    <Tags
                                        settings={settings}
                                        value={initZones}
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

export default SettingLocation;
