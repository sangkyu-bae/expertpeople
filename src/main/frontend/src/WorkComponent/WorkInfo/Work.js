import React, {useEffect, useState} from 'react';
import './Work.css';
import WorkNav from "../WorkCommonComponet/WorkNav";
import axiosCo from "../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "../WorkCommonComponet/WorkHead";
import { useSelector} from "react-redux";
function Work(props) {
    const [error,setError]=useState({
        isError:false,
        errorMessage:""
    });
    const {isError,errorMessage}=error;
    const errors=(e)=>{
        setError({
            ...error,
            isError:true,
            errorMessage:e.response.data.message
        })
    }
    const work=useSelector(state=>state.workReducer.work);
    const isManager=useSelector(state=>state.workReducer.isManager);
    //console.log(work);
    return (
        <div className="container ">
            <div className="container-wrap nw-co">

                {
                    isError&&<NewWorkAlert text={errorMessage}></NewWorkAlert>
                }

                {
                    !work.published&&!isManager&&!isError&&
                    <>
                        <WorkHead errors={errors}></WorkHead>
                        <NewWorkAlert text="공개된 일감이 아닙니다."></NewWorkAlert>
                    </>

                }
                {
                    !isError&&(work.published||isManager)&&
                    <>
                        <WorkHead errors={errors}></WorkHead>
                        <WorkNav check='info'></WorkNav>
                        <div className="work-contents" dangerouslySetInnerHTML={ {__html: work.fullDescription}}>
                        </div>
                    </>
                }
            </div>
        </div>
    );
}

export default Work;
