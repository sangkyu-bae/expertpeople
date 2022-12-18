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
}
export default Notice;