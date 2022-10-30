import React, {useEffect} from 'react';
import moment from "moment/moment";

function EnrollmentBox({enrollment}) {


    useEffect(()=>{
        enrollment.enrolledAt=moment().format(enrollment.enrolledAt)
    },[])
    return (
        <tr key={enrollment.id}>
            <td>1</td>
            <td>{enrollment.name}</td>
            <td>{enrollment.enrolledAt}</td>
            {
                enrollment.accepted?
                    <>
                        <td>확정</td>
                        <td>취소</td>
                    </>:
                    <>
                        <td>대기중</td>
                        <td>신청수락</td>
                    </>
            }

            {
                !enrollment.attended&&
                <td>출근 완료</td>
            }

        </tr>
    );
}

export default EnrollmentBox;