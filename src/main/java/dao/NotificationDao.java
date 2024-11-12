package dao;

import java.util.List;

import vo.Notification;

public interface NotificationDao {

	
	List<Notification> getWishNotifications(int userId,int type) throws Exception;
	
	int updateNotificationIsRead(int notificationId,boolean isRead);
    
}
