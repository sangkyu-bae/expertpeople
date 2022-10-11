import React, {useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import formValidation, {regexp} from "../../../util/common/Validation";
import {useNavigate} from "react-router-dom";

function UpdateUrlBox(props) {
    const [newPath,setNewPath]=useState("");
    const onChange=e=> setNewPath(e.target.value);
    const onSubmit=e=>{
        e.preventDefault();
        const isVlidation=regexp(newPath);
        if(!isVlidation){
            setIsError(true);
            return false;
        }
        updateWorkUrl();
    }
    const navigate=useNavigate();
    const [isError,setIsError]=useState(false);
    const updateWorkUrl=()=>{
        axiosCo.updateWorkUrl(props.path.path,newPath)
            .then(e=>{
                navigate(`/work/${newPath}/setting`);
            })
            .catch(e=>{
                console.log(e);
                setIsError(true);
            })
    }

    return (
        <>
            {
                isError&&
                <div className="topic-content locations open-comment al">
                    잘못된 경로입니다. 확인후 다시 시도하세요.
                </div>
            }
            <div className="topic-head">
                <h2>구인경로</h2>
            </div>
            <div className="topic-content">
                구인 경로를 수정하면 이전에 사용하던 경로를 구인정보에 접근할 수 없으니 주의하세요.
            </div>
            <form onSubmit={onSubmit}>
                <input value={newPath} onChange={onChange} name="newPath" type="text"placeholder={props.path.path}/>
                <button className='job-open-btn yell'>경로 수정</button>
            </form>

        </>

    );
}

export default UpdateUrlBox;