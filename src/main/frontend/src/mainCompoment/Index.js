import React, {useEffect, useState} from 'react';
import './Index.css'
import {Pagination, Typography} from "@mui/material";
import Dropdown from "../CommonComponent/Dropdwon";
import Paging from "../util/common/Paging";
import paging from "../util/common/Paging";
import ActionAreaCard from "../CommonComponent/ActionAreaCard";


function Index({searchData,isSearch}) {
    const [pagination,setPagination]=useState({
        totalCount:0,
        totalPage:0,
        pagingPage:9,
        isNext:true,
        isPrev:false,
        currentPage:1,
        pageWorks:[]
    })
    const [works,setWorks]=useState()
    const{totalCount,totalPage,pagingPage,isNext,isPrev,currentPage,pageWorks}=pagination;
    useEffect(()=>{
        if(searchData.works){
            const total=paging.getTotalPage(searchData.works.length,pagingPage);
            const totalCnt=searchData.works.length;
            setWorks(searchData.works);
            setPagination({
                ...pagination,
                totalCount: searchData.works.length,
                totalPage: total,
                pageWorks: Paging.setWorksPage(searchData.works,1,totalCnt,pagingPage)
            })
        }
    },[searchData])
    function handleChange(event: React.ChangeEvent<unknown>, value: number) {
        const work=paging.setWorksPage(works,value,totalCount,pagingPage);
        setPagination({
            ...pagination,
            currentPage: value,
            pageWorks: work
        })
    }
    const workChangeSearch=(searchRequirement)=>{
        const workpaging=paging[searchRequirement];
        const workData= workpaging(works)
        const work=paging.setWorksPage(workData,1,totalCount,pagingPage)
        console.log(work)
        setPagination({
            ...pagination,
            pageWorks: work
        })
    }
    return (
        <div>
            <div className="container">
                {
                    isSearch ?
                        pageWorks.length>0&&
                        <>
                            <div className="py-5 search-text-center ">
                                {searchData.keyword}에 해당하는 일감을{totalCount}개 찾았습니다.
                            </div>
                            <div className="tet">
                                <Dropdown workChangeSearch={workChangeSearch}></Dropdown>
                            </div>

                        </>
                     :
                        <div className="py-5 text-center">
                            <h2> 전문인력들을 만나다 ExpertPeople</h2>
                        </div>
                }

                <div className="main_flex">
                    {
                        isSearch&&
                        pageWorks.length>0 &&
                        pageWorks.map(search=>
                            // <Card key={search.id} search={search}></Card>
                            <ActionAreaCard key={search.id} search={search}></ActionAreaCard>
                        )
                    }
                    {
                        isSearch&&
                        totalPage>1 &&
                        <Pagination count={totalPage}
                                    shape="rounded"
                                    className="pagig-nav"
                                    onChange={handleChange}
                                   />

                    }
                </div>
            </div>
        </div>
    );
}

export default Index;