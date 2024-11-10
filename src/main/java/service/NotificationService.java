package service;

import java.util.List;

import vo.Notification;

public interface NotificationService {

	List<Notification> searchByUser(int userId, int type) throws Exception;
	
	int updateNotificationIsRead(int notificationId, boolean isRead);
}
