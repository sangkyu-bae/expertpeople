import React, {useEffect, useState} from 'react';
import axiosCo from "../util/common/axiosCommon";
import NotificationLeftNav from "./NoticaitonSectionComponent/NoticationLeftNav";
import NoReadNotification from "./NoticaitonSectionComponent/NoReadNotification";

function Notification(props) {
    const [noticeInfo,setNoticeInfo]=useState({});
    useEffect(()=>{
        getNotify()
    },[])
    const getNotify=()=>{
        axiosCo.getNotification()
            .then(e=>{
                console.log(e.data);
                setNoticeInfo(e.data);
            })
            .catch(e=>{
                console.log(e);
            })
    }

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    {
                        Object.keys(noticeInfo).length>0&&
                        <>
                            <NotificationLeftNav check='new'
                                                 newCount={noticeInfo.newCount}
                                                 oldCount={noticeInfo.oldCount}/>
                            <NoReadNotification notifications={noticeInfo.notifications}/>
                        </>
                    }

                </div>
            </div>
        </div>
    );
}

export default Notification;
