import React from 'react';
import './NoticationRightWrap.css'
import NoticeItemBox from "./NoticeItemBox";
function NoReadNotification({notifications}) {
    return (
        <div className='notice-right-wrap'>
            <div className='notice-content-box'>
                {
                    notifications.WORK_CREATED.length>0?
                        <>
                            <h2>새 일감 관련 소식이 있습니다.</h2>
                            <ul className='noti-item-box'>
                                {
                                    notifications.WORK_CREATED.map(notification=>
                                        <NoticeItemBox key={notification.id} notification={notification}></NoticeItemBox>)
                                }
                            </ul>
                        </>
                     :
                        <>
                            <h2>새 일감 관련 소식이 없습니다.</h2>
                        </>
                }

            </div>
            <div className='notice-content-box'>
                {
                    notifications.RECRUIT_ENROLLMENT.length>0?
                        <>
                            <h2>일감 참가 신청 관련 소식이 있습니다.</h2>
                            <ul className='noti-item-box'>
                                {
                                    notifications.RECRUIT_ENROLLMENT.map(notification=>
                                        <NoticeItemBox key={notification.id} notification={notification}></NoticeItemBox>)
                                }
                            </ul>
                        </>:
                        <>
                            <h2>일감 참가 신청 관련 소식이 없습니다.</h2>
                        </>
                }

            </div>
            <div className='notice-content-box'>
                {
                    notifications.WORK_UPDATED.length>0?
                        <>
                            <h2>참가중인 일감 관련 소식이 있습니다.</h2>
                            <ul className='noti-item-box'>
                                {
                                    notifications.WORK_UPDATED.map(notification=>
                                        <NoticeItemBox key={notification.id} notification={notification}></NoticeItemBox>)
                                }
                            </ul>
                        </>:
                        <>
                            <h2>참가중인 일감 관련 소식이 없습니다.</h2>
                        </>
                }

            </div>
        </div>
    );
}

export default NoReadNotification;