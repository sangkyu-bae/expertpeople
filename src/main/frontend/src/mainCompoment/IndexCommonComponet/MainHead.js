import React, {useEffect} from 'react';
import Dropdown from "../../CommonComponent/Dropdwon";

function MainHead({searchKeyword, totalCount, workChangeSearch}) {
    return (
        <>
            <div className="py-5 search-text-center ">
                <span className="keyword">{searchKeyword}</span>
                에 해당하는 일감을{totalCount}개 찾았습니다.
            </div>
            <div className="tet">
                <Dropdown workChangeSearch={workChangeSearch}></Dropdown>
            </div>
        </>
    );
}

export default MainHead;
