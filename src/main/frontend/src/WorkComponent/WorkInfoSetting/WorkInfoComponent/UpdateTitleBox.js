import React, {useState} from 'react';
import formValidation from "../../../util/common/Validation";
import axiosCo from "../../../util/common/axiosCommon";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

function UpdateTitleBox(props) {
    const [newTitle,setNewTitle]=useState("");
    const onChange=e=>setNewTitle(e.target.value);
    const [isError,setIsError]=useState(false);
    const navigate=useNavigate();
    const onSubmit=e=>{
        e.preventDefault();
        const isValiation=formValidation.updateNewTitle(newTitle);
        if(!isValiation){
            setIsError(true);
            return false;
        }
        updateWorkTitle();
    }
    const updateWorkTitle=()=>{
        axiosCo.updateWorkTitle(props.path.path,newTitle)
            .then(e=>{
                navigate(`/work/${props.path.path}`);
            })
            .catch(e=>{
                setIsError(true);
            })
    }

    const work=useSelector(state => state.workReducer.work);


    return (
        <>
            {
                isError&&
                <div className="topic-content locations open-comment al">
                    타이틀이 짧습니다. 확인후 다시 시도하세요.
                </div>
            }
            <div className="topic-head">
                <h2>일감 이름</h2>
            </div>
            <div className="topic-content">
                일감 이름을 변경합니다.
            </div>
            <form onSubmit={onSubmit}>
                <input value={newTitle} onChange={onChange} name="newPath" type="text"placeholder={work.title}/>
                <button className='job-open-btn yell'>이름 수정</button>
            </form>

        </>
    );
}

export default UpdateTitleBox;