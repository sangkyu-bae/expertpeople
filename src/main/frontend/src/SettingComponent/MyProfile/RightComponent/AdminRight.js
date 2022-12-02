import React, {useEffect, useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import ActionAreaCard from "../../../CommonComponent/ActionAreaCard";
import paging from "../../../util/common/Paging";
import Paging from "../../../util/common/Paging";
import {Pagination} from "@mui/material";
import Dropdown from "../../../CommonComponent/Dropdwon";

function AdminRight(props) {
    const [adminWorks, setAdminWorks] = useState([]);
    const [pagination, setPagination] = useState({
        totalCount: 0,
        totalPage: 0,
        pagingPage: 9,
        isNext: true,
        isPrev: false,
        currentPage: 1,
        pageWorks: []
    })
    useEffect(() => {
        getAdminWork();
    }, [])
    const {totalCount, totalPage, pagingPage, pageWorks} = pagination;
    useEffect(() => {
        console.log(adminWorks.length)
        if (adminWorks.length>0) {
            const total = paging.getTotalPage(adminWorks.length, pagingPage);
            const totalCnt = adminWorks.length;
            setPagination({
                ...pagination,
                totalCount: totalCnt,
                totalPage: total,
                pageWorks: Paging.setWorksPage(adminWorks, 1, totalCnt, pagingPage)
            })
        }
    }, [adminWorks])

    const getAdminWork = () => {
        axiosCo.getAdminWork()
            .then(e => {
                setAdminWorks(e.data);
            })
            .catch(e => {
                console.log(e)
            })
    }
    function handleChange(event: React.ChangeEvent<unknown>, value: number) {
        const work = paging.setWorksPage(adminWorks, value, totalCount, pagingPage);
        setPagination({
            ...pagination,
            currentPage: value,
            pageWorks: work
        })
    }
    return (
        <>
            <div className="admin_main">
                {
                    adminWorks.length > 0 &&
                    pageWorks.map(adminWork =>
                        <ActionAreaCard search={adminWork} key={adminWork.id}></ActionAreaCard>
                    )
                }
                {
                    totalPage > 1 &&
                    <Pagination count={totalPage}
                                shape="rounded"
                                className="pagig-nav"
                                onChange={handleChange}
                    />
                }
            </div>
        </>
    );
}

export default AdminRight;