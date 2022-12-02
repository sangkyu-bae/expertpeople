import React, {useEffect, useRef} from 'react';
import {Link} from "react-router-dom";

function LeftMyProfile(props) {

    return (
        <div className="left">
            <div className="itme-wrap">
                <ul className="my-item">
                    <Link to='/setting/profile'>
                        <li className={`items first ${props.check=='profile' && "click"}`} >
                            <span >
                                프로필
                            </span>
                        </li>
                    </Link>
                    <Link to='/setting/password'>
                        <li className={`items ${props.check=='passwrod' && "click"}`} >
                            <span>
                                패스워드
                            </span>
                        </li>
                    </Link>
                    <Link to='/setting/notification'>
                        <li className={`items ${props.check=='notification' && "click"}`}>
                        <span>
                            알림
                        </span>
                        </li>
                    </Link>

                    <Link to="/setting/attention-topic">
                        <li className={`items ${props.check=='attention' && "click"}`}>
                            <span>
                                관심 일감
                            </span>
                        </li>
                    </Link>
                    <Link to="/setting/location">
                        <li className={`items ${props.check=='location' && "click"}`}>
                            <span>
                                활동 지역
                            </span>
                        </li>
                    </Link>

                    <li className="items last">
                        <span>
                            계정
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default LeftMyProfile;