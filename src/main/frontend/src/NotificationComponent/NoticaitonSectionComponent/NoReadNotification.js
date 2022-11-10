import React from 'react';
import './NoticationRightWrap.css'
import NoticeItemBox from "./NoticeItemBox";
function NoReadNotification({notifications}) {
    return (
        <div className='notice-right-wrap'>
            <div className='notice-content-box'>
                <h2>일감 참가 신청 관련 소식이 있습니다.</h2>
                <ul className='noti-item-box'>
                    {
                        notifications.map(notification=>
                            <NoticeItemBox key={notification.id} notification={notification}></NoticeItemBox>)
                    }
                </ul>
            </div>
            <div className='notice-content-box'>
                <h2>참가중인 일감 관련 소식이 있습니다.</h2>
                <ul className='noti-item-box'>
                    <li className='noti-item'>
                        <span>tetet</span><span className='item-cnt'>1분전</span>
                    </li>
                    <li className='noti-item'>
                        <span>tetet</span><span className='item-cnt'>1분전</span>
                    </li>
                    <li className='noti-item'>
                        <span>tetet</span><span className='item-cnt'>1분전</span>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default NoReadNotification;