import React, {useEffect, useState} from 'react';
import './Work.css';
import WorkNav from "./WorkCommonComponet/WorkNav";
import axiosCo from "../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "./WorkCommonComponet/WorkHead";
import {useDispatch, useSelector} from "react-redux";
import {setWorkInfoReDux} from "../../util/Redux/workReducer";
function Work(props) {
    // const dispatch=useDispatch();
    // const {path}=useParams();
    const [error,setError]=useState({
        isError:false,
        errorMessage:""
    });
    const {isError,errorMessage}=error;
    // useEffect(()=>{
    //     getWork()
    // },[])
    //
    //
    // const getWork=()=>{
    //     axiosCo.getWork(path)
    //         .then(e=>{
    //             console.log(e.data)
    //             dispatch(setWorkInfoReDux(e.data));
    //         })
    //         .catch(e=>{
    //             console.log(e)
    //             setError({
    //                 ...error,
    //                 isError:true,
    //                 errorMessage:e.response.data.message
    //             })
    //         });
    // }

    const errors=(e)=>{
        setError({
            ...error,
            isError:true,
            errorMessage:e.response.data.message
        })
    }
    const work=useSelector(state=>state.workReducer.work);

    return (
        <div className="container ">
            <div className="container-wrap nw-co">
                {
                    isError&&<NewWorkAlert text={errorMessage}></NewWorkAlert>
                }
                {
                    !isError&&
                    <>
                        <WorkHead errors={errors}></WorkHead>
                        <WorkNav check='info'></WorkNav>
                        <div dangerouslySetInnerHTML={ {__html: work.fullDescription}}>
                        </div>
                    </>
                }

                {/*{*/}
                {/*    isError&&<NewWorkAlert text={errorMessage}></NewWorkAlert>*/}
                {/*}*/}
                {/*{*/}
                {/*    !isError&& workInfo&&*/}
                {/*    <>*/}
                {/*        <WorkHead workInfo={workInfo}></WorkHead>*/}
                {/*        <WorkNav workManager={workInfo} check='info'></WorkNav>*/}
                {/*    </>*/}
                {/*}*/}
            </div>
        </div>
    );
}

export default Work;
