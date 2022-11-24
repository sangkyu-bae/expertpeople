import React, {useEffect, useState} from 'react';
import './Index.css'
import Card from "./IndexCommonComponet/Card";
import {Pagination, Typography} from "@mui/material";



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
            const total=getTotalPage(searchData.works.length,pagingPage);
            const totalCnt=searchData.works.length;
            setWorks(searchData.works);
            setPagination({
                ...pagination,
                totalCount: searchData.works.length,
                totalPage: total,
                pageWorks: setWorksPage(searchData.works,currentPage,totalCnt,pagingPage)
            })
        }
    },[searchData])

    const getTotalPage=(totalCount,pagingCount)=>{
        const tt=totalCount/pagingCount;
        return Math.ceil(tt);
    }
    const setWorksPage=(worksData,page,totalcnt,paging)=>{
        const work=[];
        let i=(page-1)*pagingPage;
        let brenchmark=page*pagingPage;

        if(totalcnt<paging){
            brenchmark=paging
        }
        if(totalcnt<brenchmark){
            brenchmark=totalcnt;
        }

        for(i;i<brenchmark;i++){
            work.push(worksData[i]);
        }

        return work;
    }

    function handleChange(event: React.ChangeEvent<unknown>, value: number) {
        const work=setWorksPage(works,value,totalCount);
        setPagination({
            ...pagination,
            currentPage: value,
            pageWorks: work
        })
    }

    return (
        <div>
            <div className="container">
                {
                    isSearch ?
                        pageWorks.length>0&&
                        <div className="py-5 search-text-center ">
                            {searchData.keyword}에 해당하는 일감을{totalCount}개 찾았습니다.
                        </div>:
                        <div className="py-5 text-center">
                            <h2> 전문인력들을 만나다 ExpertPeople</h2>
                        </div>
                }
                <div className="main_flex">
                    {
                        isSearch&&
                        pageWorks.length>0 &&
                        pageWorks.map(search=>
                            <Card key={search.id} search={search}></Card>
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