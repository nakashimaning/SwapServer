package service.impl;

import java.sql.SQLException;
import java.util.List;

import dao.NotificationDao;
import dao.impl.NotificationDaoImpl;
import service.NotificationService;
import vo.Notification;

public class NotificationServiceImpl implements NotificationService{
	private NotificationDao notificationDao;
	
	public NotificationServiceImpl() throws Exception{
		this.notificationDao = new NotificationDaoImpl();
		}

	 @Override
	    public List<Notification> searchByUser(int userId, int type){
	        try {
				List<Notification> notifications = notificationDao.getWishNotifications(userId, type);
				return notifications;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
	    }

	@Override
	public int updateNotificationIsRead(int notificationId, boolean isRead) {
        try {
			return notificationDao.updateNotificationIsRead(notificationId, isRead);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
}
