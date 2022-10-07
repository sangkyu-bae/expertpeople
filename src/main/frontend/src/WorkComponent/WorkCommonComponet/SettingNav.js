import React from 'react';
import {Link} from "react-router-dom";

function SettingNav(props) {
    const path=props.path.path;
    return (
        <div className="left">
            <div className="itme-wrap">
                <ul className="my-item">
                    <Link to={`/work/${path}/setting/info`}>
                        <li className={`items first ${props.check=='info' && "click"}`} >
                            <span >
                                내용수정
                            </span>
                        </li>
                    </Link>
                    <Link to={`/work/${path}/setting/job`}>
                        <li className={`items ${props.check=='job' && "click"}`} >
                            <span>
                               필요직업
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
                            일감
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SettingNav;