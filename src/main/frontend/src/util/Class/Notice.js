import moment from "moment/moment";

class Notice {
    constructor() {
    }
    splitByNotification(notifications,newCount,oldCount){
        let initNotificationData = {
            WORK_CREATED: [],
            WORK_UPDATED: [],
            RECRUIT_ENROLLMENT: [],
            newCount:newCount,
            oldCount:oldCount
        }

        notifications.forEach(notification=>{
            initNotificationData[notification.notificationType].push(notification);
        })

        return initNotificationData;
    }
    getDiffTime(createDate){
         createDate=moment(createDate);
        const nowDate=moment();

        let time;
        const day = nowDate.diff(createDate, 'days')
        const hours = nowDate.diff(createDate, 'hours')
        const minute = nowDate.diff(createDate, 'minute')
        if (day > 0) {
            let checkHours = hours;
            while (checkHours > 24) {
                checkHours = checkHours - 24;
            }
            time = `${day}일 ${checkHours}시간`
        }

        if (hours > 0) {
            let checkMinute = minute;
            while (checkMinute > 60) {
                checkMinute = checkMinute - 60;
            }
            time += ` ${checkMinute}분전`
        }
        return time
    }
}
export default Notice;