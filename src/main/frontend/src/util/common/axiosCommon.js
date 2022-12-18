import {accessAPI, accTE, API, tts, updateAPI} from "./APICommon";
import requests from "./RequestUrl";

const axiosCo = {
    login: async (email, password) => {
        const res = await API.post(requests.fetchLogin, {
            email: email,
            password: password
        })
        return res;
    },
    myInfo: async (email) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(requests.fetchGetAccountInfo, {
            params: {
                email: email
            }
        })
        return res;
    }, updateProfile: async (Profile) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(requests.fetchUpdateProfile, {
            bio: Profile.bio,
            location: Profile.location,
            profileImage: Profile.profileImage,
            job: Profile.job
        })
        return res;
    }, updatePassword: async (passwordForm) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(requests.fetchUpdatePassword, {
            password: passwordForm.password,
            passwordCheck: passwordForm.newPassword
        });
        return res
    }, getMyJobs: async () => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(requests.fetchGetMyJob)
        return res;
    }, addMyJobsTags: async (jobs) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(requests.fetchAddMyJobsTags, {
            jobsName: jobs
        })
        return res;
    }, removeMyJobsTags: async (jobs) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.delete(requests.fetchRemoveMyJobsTags, {
                data: {
                    jobsName: jobs
                }
            }
        )
        return res;
    }, getMyLocation: async () => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(requests.fetchGetMyLocation)
        return res;
    }, addMyLocationTag: async (location) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(requests.fetchAddMyLocation, {
            zoneName: location
        })
        return res;
    }, removeMyLocationTag: async (location) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.delete(requests.fetchRemoveMyLocation, {
            data: {
                zoneName: location
            }
        })
        return res;
    }, addWork: async (work) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(requests.fetchAddWork, {
            title: work.workTitle,
            shortDescription: work.shortInfo,
            fullDescription: work.workInfo,
            path: work.url
        })
        return res
    }, getWork: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchGetWork}/${path}`)
       // const res = await accessAPI.get(`${requests.fetchGetWork}/${path}`)
        return res;
    }, updateDescription: async (descriptions, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/description`, {
            fullDescription: descriptions.fullDescription,
            shortDescription: descriptions.shortDescription
        })
        return res;
    }, getWorkToJob: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchWorkSetting}/${path}/jobs`)
        return res;
    }, addWorkToJob: async (jobsName, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(`${requests.fetchWorkSetting}/${path}/jobs/add`, {
            jobsName: jobsName
        })
        return res;
    }, removeWorkToJob: async (jobsName, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.delete(`${requests.fetchWorkSetting}/${path}/jobs/remove`, {
            data: {
                jobsName: jobsName
            }
        })
        return res;
    },
    getWorkToZone: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchWorkSetting}/${path}/zone`)
        return res;
    },
    removeWorkToZone: async (zoneName, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.delete(`${requests.fetchWorkSetting}/${path}/zone/remove`, {
            data: {
                zoneName: zoneName
            }
        })
        return res;
    },
    addWorkToZone: async (zoneName, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(`${requests.fetchWorkSetting}/${path}/zone/add`, {
            zoneName: zoneName
        })
        return res;
    },
    updatePublish: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/publish`)
        return res;
    },
    openRecruit: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/recruit/start`)
        return res;
    },
    stopRecruit: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/recruit/stop`)
        return res;
    },
    updateWorkUrl: async (path, newPath) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/url/${newPath}`)
        return res;
    },
    updateWorkTitle: async (path, newTitle) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/title/${newTitle}`)
        return res;
    },
    removeWork: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.delete(`${requests.fetchWorkSetting}/${path}/remove`)
        return res;
    },
    joinMember: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchWorkSetting}/${path}/add/member`)
        return res;
    },
    getJobs: async () => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchRecruitment}/jobs`)
        return res;

    },
    addRecruitment: async (inputs, path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.post(`${requests.fetchRecruitment}/${path}/add/recruitment`, {
            title: inputs.title,
            description: inputs.description,
            endEnrollmentDateTime: new Date(inputs.endEnrollmentDateTime),
            startDateTime: new Date(inputs.startDateTime),
            endDateTime: new Date(inputs.endDateTime),
            jobsName: inputs.jobName,
            limitOfEnrollments: inputs.limitOfEnrollments,
            eventType: inputs.eventType
        })
        return res;

    },
    getRecruitment: async (path, id) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchRecruitment}/${path}/recruitment/${id}`)
        //const res = await accessAPI.get(`${requests.fetchRecruitment}/${path}/recruitment/${id}`)
        return res;
    },
    addEnrollment: async (path, id) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchRecruitment}/${path}/recruitment/${id}`)
        return res;

    },
    getWorkInRecruitments: async (path) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.get(`${requests.fetchRecruitment}/${path}`)
        return res;

    },
    updateRecruit: async (path, id,inputs) => {
        const JwtAPI=updateAPI();
        const res = await JwtAPI.put(`${requests.fetchRecruitment}/update/${path}/${id}`,{
            title:inputs.title,
            description:inputs.description,
            endEnrollmentDateTime:new Date(inputs.endEnrollmentDateTime),
            startDateTime:new Date(inputs.startDateTime),
            endDateTime:new Date(inputs.endDateTime),
            limitOfEnrollments:inputs.limitOfEnrollments,
            eventType:inputs.eventType
        })
        return res;
    },
    acceptEnrollment:async (path, recruitId, enrollmentId)=> {
        const JwtAPI=updateAPI();
        const res= await JwtAPI.put(`${requests.fetchRecruitment}/${path}/accept/recruitment/${recruitId}/${enrollmentId}`)
        return res;
    },
    rejectEnrollment:async (path, recruitId, enrollmentId)=> {
        const JwtAPI=updateAPI();
        const res= await JwtAPI.put(`${requests.fetchRecruitment}/${path}/reject/recruitment/${recruitId}/${enrollmentId}`)
        return res;
    },
    attendAcceptEnrollment:async (path, recruitId, enrollmentId)=> {
        const JwtAPI=updateAPI();
        const res= await JwtAPI.put(`${requests.fetchRecruitment}/${path}/attend/enrollment/${recruitId}/${enrollmentId}`)
        return res;
    },
    cancelAttendEnrollment:async (path, recruitId, enrollmentId)=> {
        const JwtAPI=updateAPI();
        const res= await JwtAPI.put(`${requests.fetchRecruitment}/${path}/cancel/attend/${recruitId}/${enrollmentId}`)
        return res;
    },
    getNotification:async ()=>{
        const JwtAPI=updateAPI();
        const res=await JwtAPI.get(`${requests.fetchGetNotification}`);
        return res;
    },
    getOldNotification:async ()=>{
        const JwtAPI=updateAPI();
        const res=await JwtAPI.get(`${requests.fetchGetNotification}/old`);
        return res;
    },
    getSearchWork:async (search)=> {
        const res=await API.get(`${requests.fetchSearch}`, {
                params: {
                    keyword: search
                }
            })
        return res;
    },
    getMainWorkData:async ()=>{
        const res=await API.get(`${requests.fetchMainWork}`);
        return res;
    },
    getAdminWork:async ()=>{
        const JwtAPI=updateAPI();
        const res=await JwtAPI.get(`${requests.fetchAdminWork}`)
        return res;
    },
    getMyNotification:async ()=>{
        const JwtAPI=updateAPI();
        const res=await JwtAPI.get(`${requests.fetchGetMyNotification}`)
        return res;
    },
    updateMyNotification:async (myNoti)=> {
        const JwtAPI=updateAPI();
        const res=await JwtAPI.put(`${requests.fetchUpdateMyNotification}`,{
            workCreateByEmail:myNoti.workCreateByEmail,
            workCreateByWeb:myNoti.workCreateByWeb,
            workEnrollmentResultByEmail:myNoti.workEnrollmentResultByEmail,
            workEnrollmentResultByWeb:myNoti.workEnrollmentResultByWeb,
            workUpdateByEmail:myNoti.workUpdateByEmail,
            workUpdateByWeb:myNoti.workUpdateByWeb
        })
        return res;
    },
    getMyData:async ()=>{
        const JwtAPI=updateAPI();
        const res=await JwtAPI.get(`${requests.fetchGetMyData}`)
        //const res=await accessAPI.get(`${requests.fetchGetMyData}`)
        return res;
    },
    readNotification:async (notificationId)=> {
        const JwtApI=updateAPI();
        const res=await JwtApI.put(`${requests.fetchReadNotification}/${notificationId}`)
        return res;
    },
    readAllNotification:async ()=> {
        const JwtAPI=updateAPI();
        const res=await JwtAPI.put(`${requests.fetchReadAllNotification}`)
        return res;
    },
    deleteOldNotification:async ()=> {
        const JwtAPI=updateAPI();
        const res=await JwtAPI.delete(`${requests.fetchDeleteOldNotification}`)
        return res;
    }
}

export default axiosCo;