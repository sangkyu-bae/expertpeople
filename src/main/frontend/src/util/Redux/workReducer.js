export const SET_WORK_INFO="SET_WORK_INFO";

export const setWorkInfoReDux=(workInfo)=>({
    type:SET_WORK_INFO,
    work:{
        close: workInfo.work.close,
        closeDateTime: workInfo.work.closeDateTime,
        fullDescription: workInfo.work.fullDescription,
        id: workInfo.work.id,
        image: workInfo.work.image,
        jobs: workInfo.work.jobs,
        managers: workInfo.work.managers,
        members: workInfo.work.members,
        path: workInfo.work.path,
        published: workInfo.work.published,
        publishedDateTime: workInfo.work.publishedDateTime,
        recruiting: workInfo.work.recruiting,
        recruitingUpdateTime: workInfo.work.recruitingUpdateTime,
        shortDescription: workInfo.work.shortDescription,
        title: workInfo.work.title,
        useBanner: workInfo.work.useBanner,
        zones: workInfo.work.zones
    },
    isMember: workInfo.isMember,
    isManager: workInfo.isManager,
    isJoinable: workInfo.isJoinable
})

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


export default function workReducer(state=initialState,action){
    switch (action.type){
        case SET_WORK_INFO:{
            return{
                work:action.work,
                isMember: action.isMember,
                isManager: action.isManager,
                isJoinable: action.isJoinable
            }
        }
        default:
            return state
    }

}