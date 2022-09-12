import React, {useEffect, useState} from 'react';
import "@yaireo/tagify/dist/tagify.css"
import axiosCo from "../../util/common/axiosCommon";
import tagifySetting from "../AttentionTopic/TagifySetting";
import Tags from "@yaireo/tagify/dist/react.tagify" // React-wrapper file


const insertLocation=e=>{
    axiosCo.addMyLocationTag(e.detail.data.value)
        .then(e=>{
            console.log(e);
        }).catch(e=>{
        console.log(e);
    })
}

const removeLocation=e=>{
    axiosCo.removeMyLocationTag(e.detail.data.value)
        .then(e=>{
            console.log(e)
        }).catch(e=>console.log(e))
}

function MyLocationCenter(props) {
    const [initLocation,setInitLocation]=useState([])

    const tagifyCallbacks = {
        add: insertLocation,
        remove: removeLocation,
    };
    const [settings,setSettings]=useState(
        {
            ...tagifySetting.baseTagifySettings,
            whitelist:[],
            enforceWhitelist:"test",
            callbacks: tagifyCallbacks
        }
    )

    const {whitelist}=settings;

    useEffect(()=>{
        axiosCo.getMyLocation()
            .then(e=>{
               setInitLocation(e.data.zone);
               setSettings({
                       ...settings,
                       whitelist:e.data.allZone
                   })
            }).catch(e=>console.log(e))
    },[])

    return (
        <div className="topic-wrap">
            <div className="topic-head">
                <h2>주요활동 지역</h2>
            </div>
            <div className="topic-content locations">
                주로 일할 수 있는 지역을 등록하세요. 해당 지역에 일감이 생기면 알림을 받을 수 있습니다.<br/>
                시스템에 등록된 지역만 선택할 수 있습니다.
            </div>
            <div>
                {
                    whitelist.length>0&&
                    <Tags
                        settings={settings}
                        value={initLocation}
                    ></Tags>
                }
            </div>
        </div>
    );
}

export default MyLocationCenter;