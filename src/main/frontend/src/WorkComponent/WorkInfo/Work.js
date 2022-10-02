import React, {useEffect} from 'react';
import './Work.css';
import WorkNav from "./WorkNav";
import axios from "axios";
import axiosCo from "../../util/common/axiosCommon";
function Work(props) {
    useEffect(()=>{

    },[])

    const getWork=()=>{
        axiosCo.getWork("test")
            .then(e=>{

            })
            .catch(e=>{

            });
    }

    return (
        <div className="container ">
            <div className="container-wrap nw-co">
                <div className="content-wrap">
                    <h2 className="work-head">test</h2>
                    <small className="work-head-small">test 등록</small>
                </div>
                <WorkNav check='info'></WorkNav>
            </div>
        </div>
    );
}

export default Work;
