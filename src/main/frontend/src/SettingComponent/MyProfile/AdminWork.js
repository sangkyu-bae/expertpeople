import React, {useEffect, useState} from 'react';
import axiosCo from "../../util/common/axiosCommon";
import ProfileLeft from "../SettingCommon/ProfileLeft";
import AdminRight from "./RightComponent/AdminRight";
import Dropdown from "../../CommonComponent/Dropdwon";
function AdminWork(props) {

    return (
        <div className="ct_height">
            <div className="container">
                <div className="container-wrap">
                    <div className="admin_flex">
                        <ProfileLeft click={'admin'}></ProfileLeft>
                        <AdminRight></AdminRight>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default AdminWork;