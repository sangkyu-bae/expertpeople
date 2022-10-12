import React, {useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";

function RemoveWorkBox(props) {
    const [isError,setIsError]=useState(false);
    const navigate=useNavigate();
    const onClick=()=>{
        removeWork();
    }
    const removeWork=()=>{
        axiosCo.removeWork(props.path.path)
            .then(e=>{
                navigate("/");
            })
            .catch(e=>{
                setIsError(true);
            })
    }

    const work=useSelector(state => state.workReducer.work);
    return (
        <>
            <div className="topic-head">
                <h2>일감 삭제</h2>
            </div>
            {
                (work.published||isError)&&
                <>
                    <div className="topic-content locations open-comment al re">
                        공개중인 일감은 삭제할 수 없습니다.
                    </div>
                    <button className=' disabled red-btn'>일감 삭제</button>
                </>
            }
            {
                !work.published&&!isError&&
                <>
                    <div className="topic-content re">
                        일감을 삭제하면 일감 관련 모든 기록을 삭제하여 복구할 수 없습니다.
                    </div>
                    <button onClick={onClick} className='yell red-btn event'>일감 삭제</button>
                </>

            }



        </>
    );
}

export default RemoveWorkBox;