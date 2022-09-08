import React, {useCallback} from 'react';
import Tags from "@yaireo/tagify/dist/react.tagify" // React-wrapper file
import "@yaireo/tagify/dist/tagify.css"
import tagifySetting from "./TagifySetting";

function CenterAttention(props) {
    const tagifySettings ={
        blacklist: ["xxx", "yyy", "zzz"],
            maxTags: 6,
            backspace: "edit",
            addTagOnBlur: false,
            placeholder: "type something",
            dropdown: {
            enabled: 0 // a;ways show suggestions dropdown
        }
    }
    const onChange = useCallback((e) => {
        console.log("CHANGED:"
            , e.detail.tagify.value // Array where each tag includes tagify's (needed) extra properties
            , e.detail.tagify.getCleanValue() // Same as above, without the extra properties
            , e.detail.value // a string representing the tags
        )
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
                <Tags
                    onChange={onChange}
                ></Tags>
            </div>
        </div>
    );
}

export default CenterAttention;