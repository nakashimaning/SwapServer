package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import common.ServiceLocator;
import dao.NotificationDao;
import vo.Notification;

public class NotificationDaoImpl implements NotificationDao{
	private DataSource dataSource;

    public NotificationDaoImpl() {
    	dataSource = ServiceLocator.getInstance().getDataSource();
    }
	@Override
	public List<Notification> getWishNotifications(int userId,int type) throws Exception {
		String sql = "select * from Notification where type= ? and user_id = ?";
		List<Notification> notificationsList = new ArrayList<Notification>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, type);
			pstmt.setInt(2, userId);
			
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					Notification notification = new Notification();
					notification.setNotification_id(rs.getInt("notification_id"));
					notification.setType(rs.getInt("type"));
					notification.setUser_id(rs.getInt("user_id"));
					notification.setMessage(rs.getString("message"));
					notification.setCreated_date(rs.getTimestamp("created_date"));
					notification.setIs_read(rs.getBoolean("is_read"));
					notificationsList.add(notification);
				}
				return notificationsList;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("查詢資料時發生錯誤: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("資料庫連線錯誤"+ e.getMessage());
		}
		
	}
	@Override
	public int updateNotificationIsRead(int notificationId, boolean isRead) {
		String sql = "UPDATE Notification SET is_read = ? WHERE notification_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setBoolean(1, isRead);
			pstmt.setInt(2, notificationId);
			try{
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("查詢資料時發生錯誤: " + e.getMessage());
			}
			
			
		}catch (Exception e) {
			
		}
		
		return 0;
	}

	

}
