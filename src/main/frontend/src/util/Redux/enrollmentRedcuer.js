export const SET_ENROLLMENT_INFO = "SET_ENROLLMENT_INFO";
export const SET_ADD_ENROLLMENT_INFO = "SET_ADD_ENROLLMENT_INFO";

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
                ...state,
                enrollments:action.enrollment,
                isEnrollment: action.isEnrollment
            }
        }
        default:
            return state
    }
}