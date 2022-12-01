import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {Link, useNavigate, useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";
import {setWorkInfoReDux} from "../../util/Redux/workReducer";
import {Tooltip} from "@mui/joy";
import Button from "@mui/material/Button";
import {Box} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';

function WorkHead(props) {
    const dispatch = useDispatch();
    const {path} = useParams();
    const [isSuccess, setIsSuccess] = useState(false);
    useEffect(() => {
        getWork()
    }, [isSuccess])
    const getWork = () => {
        axiosCo.getWork(path)
            .then(e => {
                //console.log(e.data)
                dispatch(setWorkInfoReDux(e.data));
                console.log(e.data)
            })
            .catch(e => {
                console.log(e)
                props.errors(e)
            });
    }

    const joinMember = () => {
        axiosCo.joinMember(path)
            .then(e => {
                setIsSuccess(true);
            })
            .catch(e => {
                console.log(e);
            })
    }

    const nav = useNavigate();
    const newRecruitmentRoute = () => {
        nav(`/work/${path}/new-recruitment`);
    }

    const work = useSelector(state => state.workReducer.work);
    const isManager = useSelector(state => state.workReducer.isManager);
    const isMember = useSelector(state => state.workReducer.isMember);

    return (
        <div className="content-wrap">
            <div className="flex">
                <h2 className="work-head">{work.title}</h2>
                {
                    isManager && !work.published ?
                        <>
                            <Box sx={{display: 'flex', gap: 4, width: '15%', justifyContent: 'center'}}>
                                <Tooltip title="일감 공개 준비중" variant="outlined">
                                    <Button variant="outlined">DRAFT</Button>
                                </Tooltip>
                                <Tooltip title="Delete" variant="outlined">
                                    <Button variant="outlined">OFF</Button>
                                </Tooltip>
                            </Box>
                            {/*<div className='togle'>DRAFT</div>*/}
                            {/*<div className='togle'>OFF</div>*/}
                        </> :
                        <>
                            <Box sx={{display: 'flex', gap: 4, width: '25%', justifyContent: 'center'}}>
                                <Tooltip title="구직자 모집 시작" variant="outlined">
                                    <Button variant="outlined">OFF</Button>
                                </Tooltip>
                                <Button variant="contained" onClick={newRecruitmentRoute} endIcon={<AddIcon/>}>
                                    일감 만들기
                                </Button>
                            </Box>
                        </>
                }
                {
                    work.published && !isMember && !isManager &&
                    <>
                        <button className="join-work-btn" onClick={joinMember}>현장가입</button>
                        <div className="join-count">0</div>

                    </>
                }
            </div>
            <small className="work-head-small">{work.shortDescription}</small>
        </div>
    );
}

export default WorkHead;