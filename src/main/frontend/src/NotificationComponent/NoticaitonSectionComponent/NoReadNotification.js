import React from 'react';
import './NoticationRightWrap.css'
import NoticeItemBox from "./NoticeItemBox";

function NoReadNotification({notifications, fullInfo}) {
    return (
        <div className='notice-right-wrap'>

            {
                fullInfo.mode == 'full' || fullInfo.mode == 'WORK_CREATED' ?
                    notifications.WORK_CREATED.length > 0 ?
                        <>
                            <div className='notice-content-box'>
                                <h2>새 일감 관련 소식이 있습니다.</h2>
                                <ul className='noti-item-box'>
                                    {
                                        notifications.WORK_CREATED.map(notification =>
                                            <NoticeItemBox key={notification.id}
                                                           notification={notification}></NoticeItemBox>)
                                    }
                                </ul>
                            </div>
                        </>
                        :
                        <>
                            <div className='notice-content-box'>
                                <h2>새 일감 관련 소식이 없습니다.</h2>
                            </div>
                        </> :
                    <></>
            }


            {
                fullInfo.mode == 'full' || fullInfo.mode == 'RECRUIT_ENROLLMENT' ?
                    notifications.RECRUIT_ENROLLMENT.length > 0 ?
                        <>
                            <div className='notice-content-box'>
                                <h2>일감 참가 신청 관련 소식이 있습니다.</h2>
                                <ul className='noti-item-box'>
                                    {
                                        notifications.RECRUIT_ENROLLMENT.map(notification =>
                                            <NoticeItemBox key={notification.id}
                                                           notification={notification}></NoticeItemBox>)
                                    }
                                </ul>
                            </div>
                        </> :
                        <>
                            <div className='notice-content-box'>
                                <h2>일감 참가 신청 관련 소식이 없습니다.</h2>
                            </div>
                        </> :
                    <></>
            }


            {
                fullInfo.mode == 'full' || fullInfo.mode == 'WORK_UPDATED' ?
                    notifications.WORK_UPDATED.length > 0 ?
                        <>
                            <div className='notice-content-box'>
                                <h2>참가중인 일감 관련 소식이 있습니다.</h2>
                                <ul className='noti-item-box'>
                                    {
                                        notifications.WORK_UPDATED.map(notification =>
                                            <NoticeItemBox key={notification.id}
                                                           notification={notification}></NoticeItemBox>)
                                    }
                                </ul>
                            </div>
                        </> :
                        <>
                            <div className='notice-content-box'>
                                <h2>참가중인 일감 관련 소식이 없습니다.</h2>
                            </div>
                        </> :
                    <></>
            }


        </div>
    );
}

export default NoReadNotification;