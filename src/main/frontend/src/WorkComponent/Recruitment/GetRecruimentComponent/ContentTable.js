import React, {useEffect, useState} from 'react';
import EnrollmentBox from "./EnrollmentBox";
import moment from "moment";

function ContentTable({enrollments}) {
    const [enrollmentList,setEnrollmentList]=useState([]);
    const dateForm='YYYY-MM-DD';
    useEffect(()=>{
        enrollments.forEach((e)=>{
            e.enrolledAt=moment(e.enrolledAt).format(dateForm)
        })
        setEnrollmentList(enrollments)

    },[])
    return (
        <table>
            <thead>
            <tr>
                <th className='th-first'>#</th>
                <th className='th-second'>참석자</th>
                <th className='th-third'>참가 신청 일시</th>
                <th className='th-second'>참가 상태</th>
                <th className='th-second'>참가 신청 관리</th>
                <th className='th-second'>참석 여부</th>
            </tr>
            </thead>
            <tbody>
            {
                enrollmentList.length>0&&
                enrollmentList.map((enrollment)=>
                   <EnrollmentBox key={enrollment.id} enrollment={enrollment}></EnrollmentBox>

                )
            }

            </tbody>
        </table>

    );
}

export default ContentTable;