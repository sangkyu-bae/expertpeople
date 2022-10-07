import React, {useCallback, useEffect, useState} from 'react';
import Tags from "@yaireo/tagify/dist/react.tagify" // React-wrapper file
import "@yaireo/tagify/dist/tagify.css"
import tagifySetting from "./TagifySetting";
import axiosCo from "../../util/common/axiosCommon";

//
// const callback = e =>
//     console.log(`%c ${e.type}: `, "background:#222; color:#bada55", e.detail.data.value);
const insertTag=e=>{
    axiosCo.addMyJobsTags(e.detail.data.value)
        .then(e=>{
            console.log(e);
        }).catch(e=>{
        console.log(e);
    })
}

const removeTag=e=>{
    axiosCo.removeMyJobsTags(e.detail.data.value)
        .then(e=>{
            console.log(e)
        }).catch(e=>console.log(e))
}

function CenterAttention(props) {
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
        axiosCo.getMyJobs()
            .then(e=>{
                console.log(e.data);
                setIniJobs(e.data.job);
                setSettings({
                    ...settings,
                    whitelist: e.data.allJobs,
                })
            })
            .catch(e=>console.log(e.data))
    },[])


    const onChange = useCallback((e) => {
        console.log("CHANGED:"
            ,e
            // , e.detail.tagify.value// Array where each tag includes tagify's (needed) extra properties
            , e.detail.tagify.getCleanValue() // Same as above, without the extra properties
            //, e.detail.value // a string representing the tags
        )
    }, [])



    return (
        <div className="topic-wrap">
            <div className="topic-head">
                <h2>관심있는 일감</h2>
            </div>
            <div className="topic-content">
                일하고 싶은 일을 주제로 입력해주세요. 해당 주제의 일감이 생기면 알림을 받을 수 있습니다.<br/>
                시스템에 등록된 지역만 선택할 수 있습니다.
            </div>
            <div>
                {
                    whitelist.length>0&&
                    <Tags
                        onChange={onChange}
                        settings={settings}
                        value={initJobs}
                    ></Tags>
                }
            </div>
        </div>
    );
}

export default CenterAttention;