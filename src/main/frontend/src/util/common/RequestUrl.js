const requests={
    fetchLogin:"login",
    fetchJoinUp:"api/join-up",
    fetchGetAccountInfo:"api/account",
    fetchUpdateProfile:"api/setting/profile",
    fetchUpdatePassword:"api/setting/password",
    fetchGetMyJob:'api/setting/job',
    fetchAddMyJobsTags:'api/setting/add/jobs',
    fetchRemoveMyJobsTags:'api/setting/delete/jobs',
    fetchGetMyLocation:'api/setting/zone',
    fetchAddMyLocation:'api/setting/zone/add',
    fetchRemoveMyLocation:'api/setting/zone/delete',
    fetchAddWork:'api/work/add',
    fetchGetWork:'api/work',
    fetchWorkSetting:"api/work/setting",
    fetchRecruitment:"api/recruitment",
    fetchGetNotification:"api/notification",
    sseNotify:"/api/notify",
    fetchSearch:"api/search/work",
    fetchMainWork:"api/main/work",
}

export const requestURL="http://localhost:8080/"
export default requests;