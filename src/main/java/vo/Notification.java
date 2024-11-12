package vo;

import java.sql.Timestamp;

public class Notification {
	private Integer notification_id;
	private Integer user_id;
	private Integer type;
	private String message;
	private Timestamp created_date;
	private Boolean is_read;
	
	public Notification() {
	}
	
	public Notification(Integer notification_id, Integer user_id, Integer type, String message, Timestamp created_date, Boolean is_read) {
		this.notification_id = notification_id;
		this.user_id = user_id;
		this.type = type;
		this.message = message;
		this.created_date = created_date;
		this.is_read = is_read;
	}

	public Integer getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(Integer notification_id) {
		this.notification_id = notification_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Boolean getIs_read() {
		return is_read;
	}

	public void setIs_read(Boolean is_read) {
		this.is_read = is_read;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
