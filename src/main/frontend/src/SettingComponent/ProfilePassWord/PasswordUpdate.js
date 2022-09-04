import React, {useState} from 'react';
import LeftMyProfile from "../MyProfile/LeftMyProfile";
import CenterMyProfile from "../MyProfile/CenterMyProfile";
import RigthMyProfile from "../MyProfile/RigthMyProfile";
import './PasswordUpdate.css';
import PasswordCenter from "./PasswordCenter";
function PasswordUpdate(props) {
    const [isCheckPassword,setIsCheckPassword]=useState(true);
    const [isCheckOk,setIsCheckOk]=useState(false);

    const changeCheckOk=()=>setIsCheckOk(true);
    const checkPassword=()=>setIsCheckPassword(false)
    return (
        <div className="container">
            <div className="container-wrap">
                {isCheckOk&&
                    <div className="true">
                        <div className="alert alert-danger" role="alert">
                            <p>비밀번호가 변경 되었습니다.  </p>
                            <p>다음번 로그인시 변경된 비밀번호를 사용하세요.</p>
                        </div>
                    </div>
                }
                {!isCheckPassword&&
                    <div  className="alert alert-danger" role="alert">
                        <p>비밀번호가 짧습니다. </p>
                        <p>또는 비밀번호가 정확하게 입력하지 않았습니다</p>
                        <p>
                            확인 후 다시 입력하세요.
                        </p>
                    </div>
                }
                <div className="flex">
                    <LeftMyProfile check={'passwrod'}></LeftMyProfile>
                    <PasswordCenter checkPassword={checkPassword} changeCheckOk={changeCheckOk}></PasswordCenter>
                </div>
            </div>
        </div>
    );
}

export default PasswordUpdate;