import React, {useEffect, useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import MainCardView from "../../../mainCompoment/IndexCommonComponet/MainCardView";
import ActionAreaCard from "../../../CommonComponent/ActionAreaCard";

function AdminRight(props) {
    const[adminWorks,setAdminWorks]=useState([]);
    useEffect(() => {
        getAdminWork();
    }, [])
    const getAdminWork = () => {
        axiosCo.getAdminWork()
            .then(e => {
                console.log(e.data)
                setAdminWorks(e.data);
            })
            .catch(e => {
                console.log(e)
            })
    }
    return (
        <div className="right">
            {
                adminWorks.length>0&&
                adminWorks.map( adminWork=>
                    <ActionAreaCard search={adminWork} key={adminWork.id}></ActionAreaCard>
                )
            }

        </div>
    );
}

export default AdminRight;