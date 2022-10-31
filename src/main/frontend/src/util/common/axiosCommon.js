import {accessAPI, API} from "./APICommon";
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
        const res = await accessAPI.get(requests.fetchGetAccountInfo, {
            params: {
                email: email
            }
        })
        return res;
    }, updateProfile: async (Profile) => {
        const res = await accessAPI.post(requests.fetchUpdateProfile, {
            bio: Profile.bio,
            location: Profile.location,
            profileImage: Profile.profileImage,
            job: Profile.job
        })
        return res;
    }, updatePassword: async (passwordForm) => {
        const res = await accessAPI.post(requests.fetchUpdatePassword, {
            password: passwordForm.password,
            passwordCheck: passwordForm.newPassword
        });
        return res
    }, getMyJobs: async () => {
        const res = await accessAPI.get(requests.fetchGetMyJob)
        return res;
    }, addMyJobsTags: async (jobs) => {
        const res = await accessAPI.post(requests.fetchAddMyJobsTags, {
            jobsName: jobs
        })
        return res;
    }, removeMyJobsTags: async (jobs) => {
        const res = await accessAPI.delete(requests.fetchRemoveMyJobsTags, {
                data: {
                    jobsName: jobs
                }
            }
        )
        return res;
    }, getMyLocation: async () => {
        const res = await accessAPI.get(requests.fetchGetMyLocation)
        return res;
    }, addMyLocationTag: async (location) => {
        const res = await accessAPI.post(requests.fetchAddMyLocation, {
            zoneName: location
        })
        return res;
    }, removeMyLocationTag: async (location) => {
        const res = await accessAPI.delete(requests.fetchRemoveMyLocation, {
            data: {
                zoneName: location
            }
        })
        return res;
    }, addWork: async (work) => {
        const res = await accessAPI.post(requests.fetchAddWork, {
            title: work.workTitle,
            shortDescription: work.shortInfo,
            fullDescription: work.workInfo,
            path: work.url
        })
        return res
    }, getWork: async (path) => {
        const res = await accessAPI.get(`${requests.fetchGetWork}/${path}`)
        return res;
    }, updateDescription: async (descriptions, path) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/description`, {
            fullDescription: descriptions.fullDescription,
            shortDescription: descriptions.shortDescription
        })
        return res;
    }, getWorkToJob: async (path) => {
        const res = await accessAPI.get(`${requests.fetchWorkSetting}/${path}/jobs`)
        return res;
    }, addWorkToJob: async (jobsName, path) => {
        const res = await accessAPI.post(`${requests.fetchWorkSetting}/${path}/jobs/add`, {
            jobsName: jobsName
        })
        return res;
    }, removeWorkToJob: async (jobsName, path) => {
        const res = await accessAPI.delete(`${requests.fetchWorkSetting}/${path}/jobs/remove`, {
            data: {
                jobsName: jobsName
            }
        })
        return res;
    },
    getWorkToZone: async (path) => {
        const res = await accessAPI.get(`${requests.fetchWorkSetting}/${path}/zone`)
        return res;
    },
    removeWorkToZone: async (zoneName, path) => {
        const res = await accessAPI.delete(`${requests.fetchWorkSetting}/${path}/zone/remove`, {
            data: {
                zoneName: zoneName
            }
        })
        return res;
    },
    addWorkToZone: async (zoneName, path) => {
        const res = await accessAPI.post(`${requests.fetchWorkSetting}/${path}/zone/add`, {
            zoneName: zoneName
        })
        return res;
    },
    updatePublish: async (path) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/publish`)
        return res;
    },
    openRecruit: async (path) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/recruit/start`)
        return res;
    },
    stopRecruit: async (path) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/recruit/stop`)
        return res;
    },
    updateWorkUrl: async (path, newPath) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/url/${newPath}`)
        return res;
    },
    updateWorkTitle: async (path, newTitle) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/title/${newTitle}`)
        return res;
    },
    removeWork: async (path) => {
        const res = await accessAPI.delete(`${requests.fetchWorkSetting}/${path}/remove`)
        return res;
    },
    joinMember: async (path) => {
        const res = await accessAPI.put(`${requests.fetchWorkSetting}/${path}/add/member`)
        return res;
    },
    getJobs: async () => {
        const res = await accessAPI.get(`${requests.fetchRecruitment}/jobs`)
        return res;

    },
    addRecruitment: async (inputs, path) => {
        const res = await accessAPI.post(`${requests.fetchRecruitment}/${path}/add/recruitment`, {
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
        const res = await accessAPI.get(`${requests.fetchRecruitment}/${path}/recruitment/${id}`)
        return res;
    },
    addEnrollment: async (path, id) => {
        const res = await accessAPI.put(`${requests.fetchRecruitment}/${path}/recruitment/${id}`)
        return res;

    },
    getWorkInRecruitments: async (path) => {
        const res = await accessAPI.get(`${requests.fetchRecruitment}/${path}`)
        return res;

    },
    updateRecruit: async (path, id,inputs) => {
        const res = await accessAPI.put(`${requests.fetchRecruitment}/update/${path}/${id}`,{
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
        const res= await accessAPI.put(`${requests.fetchRecruitment}/${path}/attend/recruitment/${recruitId}/${enrollmentId}`)
        return res;
    },
    rejectEnrollment:async (path, recruitId, enrollmentId)=> {
        const res= await accessAPI.put(`${requests.fetchRecruitment}/${path}/reject/recruitment/${recruitId}/${enrollmentId}`)
        return res;
    },
    attendAcceptEnrollment:async (path, recruitId, enrollmentId)=> {
        const res= await accessAPI.put(`${requests.fetchRecruitment}/${path}/attend/enrollment/${recruitId}/${enrollmentId}`)
        return res;
    },
    cancelAttendEnrollment:async (path, recruitId, enrollmentId)=> {
        const res= await accessAPI.put(`${requests.fetchRecruitment}/${path}/cancel/attend/${recruitId}/${enrollmentId}`)
        return res;
    },
}

export default axiosCo;