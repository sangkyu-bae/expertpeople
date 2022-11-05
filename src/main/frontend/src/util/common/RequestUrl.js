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
    sseNotify:"/api/notify"
}

export const requestURL=""
export default requests;