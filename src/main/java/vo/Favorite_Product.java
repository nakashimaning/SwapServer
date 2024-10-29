package vo;

import java.sql.Timestamp;

public class Favorite_Product {
	private Integer user_id;
	private Timestamp created_date;
	
	public Favorite_Product() {
	}
	
	public Favorite_Product(Timestamp created_date, Integer user_id) {
		this.setCreated_date(created_date);
		this.setUser_id(user_id);
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	

}
