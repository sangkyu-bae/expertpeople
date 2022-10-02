export const SET_WORK_INFO="SET_WORK_INFO";

const initialState = {
    work: {
        close: false,
        closeDateTime: '',
        fullDescription: "",
        id: '',
        image: '',
        jobs: [],
        managers: [],
        members: [],
        path: "",
        published: false,
        publishedDateTime: '',
        recruiting: false,
        recruitingUpdateTime: null,
        shortDescription: '',
        title: '',
        useBanner: false,
        zones: []
    },
    isMember:false,
    isManager:false,
    isJoinable:false
}
export default function userReducer(state=initialState,action){
    switch (action.type){
        case SET_WORK_INFO:{
            return{
                work:action.work,
                isMembers: action.isMembers,
                isManagers: action.isManagers,
                isJoinable: action.isJoinable
            }
        }
        
        default:
            return state
    }

}