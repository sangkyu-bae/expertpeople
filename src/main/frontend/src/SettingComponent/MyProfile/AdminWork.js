import React, {useEffect, useState} from 'react';
import axiosCo from "../../util/common/axiosCommon";
import ProfileLeft from "../SettingCommon/ProfileLeft";
import AdminRight from "./RightComponent/AdminRight";
function AdminWork(props) {

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <ProfileLeft click={'admin'}></ProfileLeft>
                    <AdminRight></AdminRight>
                </div>
            </div>
        </div>
    );
}

export default AdminWork;