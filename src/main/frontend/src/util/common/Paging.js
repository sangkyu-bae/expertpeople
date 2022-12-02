import axiosCo from "./axiosCommon";

const paging = {
    getTotalPage: (totalCount, pagingCount) => {
        const tt = totalCount / pagingCount;
        return Math.ceil(tt);
    },
    setWorksPage: (worksData, page, totalcnt, paging) => {
        const work = [];
        let i = (page - 1) * paging;
        let brenchmark = page * paging;
        if (totalcnt < paging) {
            brenchmark = paging
        }
        if (totalcnt < brenchmark) {
            brenchmark = totalcnt;
        }

        for (i; i < brenchmark; i++) {
            work.push(worksData[i]);
        }

        return work;
    },
    setSearchWithMemberLength: (workData) => {
        workData.sort(function (a, b) {
            if (a.members.length > b.members.length) return -1;
            if (a.members.length === b.members.length) return 0;
            if (a.members.length < b.members.length) return 1;
        })
        return workData;
    },
    setSearchWithPublishDate:(workData)=>{
        workData.sort((a, b) => new Date(b.publishedDateTime)-new Date(a.publishedDateTime) )
        return workData;
    },
}

export default paging;