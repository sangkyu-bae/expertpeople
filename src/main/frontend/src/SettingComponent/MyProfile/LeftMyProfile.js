import React from 'react';

function LeftMyProfile(props) {
    return (
        <div className="left">
            <div className="itme-wrap">
                <ul className="my-item">
                    <li className="items click first">
                        <span>
                            프로필
                        </span>
                    </li>
                    <li className="items">
                        <span>
                            패스워드
                        </span>
                    </li>
                    <li className="items">
                        <span>
                            알림
                        </span>
                    </li>
                    <li className="items">
                        <span>
                            관심 일감
                        </span>
                    </li>
                    <li className="items">
                        <span>
                            활동 지역
                        </span>
                    </li>
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