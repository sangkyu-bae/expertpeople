import React, {useCallback, useEffect, useState} from 'react';
import Tags from "@yaireo/tagify/dist/react.tagify" // React-wrapper file
import "@yaireo/tagify/dist/tagify.css"
import tagifySetting from "./TagifySetting";
import axiosCo from "../../util/common/axiosCommon";

const baseTagifySettings = {
    blacklist: ["xxx", "yyy", "zzz"],
    maxTags: 3,
    //backspace: "edit",
    placeholder: "type something",
    dropdown: {
        enabled: 0 // a;ways show suggestions dropdown
    }
}
function CenterAttention(props) {

    const [isChange,setIsChange]=useState(false);
    const [isSelect,setIsSelect]=useState(false);

    const [settings,setSettings]=useState({
        ...baseTagifySettings,
        whitelist:[],
        enforceWhitelist:"test",
    })
     const {whitelist}=settings;

    const insertTag=()=>{

    }


    useEffect(()=>{
        axiosCo.getMyJobs()
            .then(e=>{
                setSettings({
                    ...settings,
                    whitelist: e.data.allJobs,

                })
            })
            .catch(e=>console.log(e.data))
    },[])


    const onChange = useCallback((e) => {
        console.log("CHANGED:"
            , e.detail.tagify.value[0].value // Array where each tag includes tagify's (needed) extra properties
            , e.detail.tagify.getCleanValue() // Same as above, without the extra properties
            , e.detail.value // a string representing the tags
        )
        if(!isChange) console.log("ss")

    }, [])

    return (
        <div className="topic-wrap">
            <div className="topic-head">
                <h2>관심있는 일감</h2>
            </div>
            <div className="topic-content">
                일하고 싶은 일을 주제로 입력해주세요. 해당 주제의 일감이 생기면 알림을 받을 수 잇습니다.
                태그를 입력하고 콤마(,) 또는 엔터를 입력하세요
            </div>
            <div>
                {
                    whitelist.length>0&&
                    <Tags
                        onChange={onChange}
                        settings={settings}
                        onDropdownSelect={() => setIsChange(true)}
                    ></Tags>
                }

            </div>
        </div>
    );
}

export default CenterAttention;