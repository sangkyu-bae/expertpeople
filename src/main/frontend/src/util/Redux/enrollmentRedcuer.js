export const SET_ENROLLMENT_INFO = "SET_ENROLLMENT_INFO";
export const SET_ADD_ENROLLMENT_INFO = "SET_ADD_ENROLLMENT_INFO";
export const SET_REJECT_ENROLLMENT_INFO="SET_REJECT_ENROLLMENT_INFO";
export const SET_ACCEPT_ENROLLMENT_INFO="SET_ACCEPT_ENROLLMENT_INFO";
export const SET_ACCEPT_ATTEND_ENROLLMENT_INFO="SET_ACCEPT_ATTEND_ENROLLMENT_INFO";
export const SET_REJECT_ATTEND_ENROLLMENT_INFO="SET_REJECT_ATTEND_ENROLLMENT_INFO";

export const setEnrollmentInfoRedux = (enrollments,isEnrollment) => ({
    type: SET_ENROLLMENT_INFO,
    enrollments: enrollments,
    isEnrollment:isEnrollment
})

export const setAddEnrollmentInfo = (enrollment,isEnrollment) => ({
    type: SET_ADD_ENROLLMENT_INFO,
    enrollment: {
        id: enrollment.id,
        name: enrollment.name,
        enrolledAt: enrollment.enrolledAt,
        accepted: enrollment.accepted,
        attended: enrollment.attended
    },
    isEnrollment:isEnrollment
})

export const setRemoveEnrollmentInfo=(enrollmentId)=>({
    type:SET_REJECT_ENROLLMENT_INFO,
    enrollmentId:enrollmentId
})
export const setAcceptEnrollmentInfo=(enrollmentId)=>({
    type:SET_ACCEPT_ENROLLMENT_INFO,
    enrollmentId:enrollmentId
})
export const setAcceptAttendEnrollmentInfo=(enrollmentId)=>({
    type:SET_ACCEPT_ATTEND_ENROLLMENT_INFO,
    enrollmentId:enrollmentId
})
export const setRejectEnrollmentInfo=(enrollmentId)=>({
    type:SET_REJECT_ATTEND_ENROLLMENT_INFO,
    enrollmentId:enrollmentId
})
const initialState = {
    enrollments: [{
        id: "",
        name: "",
        enrolledAt: "",
        accepted: "",
        attended: ""
    }],
    isEnrollment:""
}

export default function enrollmentReducer(state = initialState, action) {
    switch (action.type) {
        case SET_ENROLLMENT_INFO: {
            return {
                enrollments: action.enrollments,
                isEnrollment:action.isEnrollment
            }
        }
        case SET_ADD_ENROLLMENT_INFO: {
            return {
                enrollments:[
                    ...state.enrollments,
                    action.enrollment
                ],
                isEnrollment: action.isEnrollment
            }
        }
        case SET_REJECT_ENROLLMENT_INFO:{
            var copyEnrollment=[...state.enrollments];
            copyEnrollment.forEach(enrollment=>{
                if(enrollment.id==action.enrollmentId) {
                    enrollment.accepted=false;
                }
            })
            return{
                ...state,
                enrollments:copyEnrollment
            }
        }
        case SET_ACCEPT_ENROLLMENT_INFO:{
            var copyEnrollment=[...state.enrollments];
            copyEnrollment.forEach(enrollment=>{
                if(enrollment.id==action.enrollmentId) {
                    enrollment.accepted=true;
                }
            })
            return{
                ...state,
                enrollments: copyEnrollment
            }
        }
        case SET_ACCEPT_ATTEND_ENROLLMENT_INFO:{
            var copyEnrollment=[...state.enrollments];
            copyEnrollment.forEach(enrollment=>{
                if(enrollment.id==action.enrollmentId) {
                    enrollment.attended=true;
                }
            })
            return{
                ...state,
                enrollments: copyEnrollment
            }
        }
        case SET_REJECT_ATTEND_ENROLLMENT_INFO:{
            var copyEnrollment=[...state.enrollments];
            copyEnrollment.forEach(enrollment=>{
                if(enrollment.id==action.enrollmentId) {
                    enrollment.attended=false;
                }
            })
            return{
                ...state,
                enrollments: copyEnrollment
            }
        }

        default:
            return state
    }
}