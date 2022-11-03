import React, {useEffect} from 'react';
import moment from "moment/moment";
import axiosCo from "../../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {
    setAcceptAttendEnrollmentInfo,
    setAcceptEnrollmentInfo,
    setRejectEnrollmentInfo,
    setRemoveEnrollmentInfo
} from "../../../util/Redux/enrollmentRedcuer";

function EnrollmentBox({enrollment, id}) {
    const path = useParams();
    const dispatch = useDispatch();
    const enrollments = useSelector(state => state.enrollmentReducer.enrollments);
    // console.log(enrollments)
    const rejectEnrollment = () => {
        axiosCo.rejectEnrollment(path.path, path.id, enrollment.id)
            .then(e => {
                // console.log(e.data)
                dispatch(setRemoveEnrollmentInfo(enrollment.id))
            })
            .catch(e => console.log(e));
    }
    const acceptEnrollment = () => {
        axiosCo.acceptEnrollment(path.path, path.id, enrollment.id)
            .then(e => {
                // console.log(e.data);
                dispatch(setAcceptEnrollmentInfo(enrollment.id))
            })
            .catch(e => console.log(e));
    }
    const attendAcceptEnrollment = () => {
        axiosCo.attendAcceptEnrollment(path.path, path.id, enrollment.id)
            .then(e => {
                dispatch(setAcceptAttendEnrollmentInfo(enrollment.id))
                //console.log(e.data)
            })
            .catch(e => console.log(e));
    }
    const cancelAttendEnrollment = () => {
        axiosCo.cancelAttendEnrollment(path.path, path.id, enrollment.id)
            .then(e => {
                dispatch(setRejectEnrollmentInfo(enrollment.id))
                //console.log(e.data)
            })
            .catch(e => console.log(e));
    }

    return (
        <tr key={enrollment.id}>
            <td>{id}</td>
            <td><span className='color-blue'>{enrollment.name}</span></td>
            <td>{enrollment.enrolledAt}</td>
            {
                enrollment.accepted ?
                    enrollment.attended ?
                        <>
                            <td>확정</td>
                            <td></td>
                        </>
                        :
                        <>
                            <td>확정</td>
                            <td><span className='color-blue' onClick={rejectEnrollment}>취소</span></td>
                        </> :
                    <>
                        <td>대기중</td>
                        <td><span className='color-blue' onClick={acceptEnrollment}>신청수락</span></td>
                    </>
            }

            {
                enrollment.attended ?
                    <td><span className='color-blue' onClick={cancelAttendEnrollment}>출근 취소</span></td> :
                    enrollment.accepted ?
                        <td><span className='color-blue' onClick={attendAcceptEnrollment}>출근 완료</span></td> :
                        <></>
            }

        </tr>
    );
}

export default EnrollmentBox;