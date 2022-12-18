import React, {useEffect, useState} from 'react';
import axiosCo from "../util/common/axiosCommon";
import NotificationLeftNav from "./NoticaitonSectionComponent/NoticationLeftNav";
import NoReadNotification from "./NoticaitonSectionComponent/NoReadNotification";
import {Notifications} from "@mui/icons-material";
import Notice from "../util/Class/Notice";

function OldNotification(props) {
    const [noticeInfo, setNoticeInfo] = useState({
        WORK_CREATED: [],
        WORK_UPDATED: [],
        RECRUIT_ENROLLMENT: [],
        newCount:0,
        oldCount:0
    })

    useEffect(()=>{
        getNotify()
    },[])

    const getNotify=()=>{
        axiosCo.getOldNotification()
            .then(e=>{
                const notification =new Notice();
                const initNotificationData=notification.splitByNotification(e.data.notifications,e.data.newCount,e.data.oldCount);
                setNoticeInfo(initNotificationData)
            })
            .catch(e=>{
                console.log(e);
            })
    }
    const [fullInfo,setFullInfo]=useState({
        isFull:true,
        mode:"full"
    });
    const contentChange=(mode)=>{
        setFullInfo({
            isFull:false,
            mode:mode
        })
    }

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    {
                        Object.keys(noticeInfo).length>0&&
                        <>
                            <NotificationLeftNav check='old'
                                                 newCount={noticeInfo.newCount}
                                                 oldCount={noticeInfo.oldCount}
                                                 notifications={noticeInfo}
                                                 contentChange={contentChange}
                                                 fullInfo={fullInfo}/>
                            <NoReadNotification notifications={noticeInfo}
                                                fullInfo={fullInfo}/>
                        </>
                    }

                </div>
            </div>
        </div>
    );
}

export default OldNotification;
