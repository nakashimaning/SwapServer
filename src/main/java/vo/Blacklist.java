package vo;

import java.sql.Timestamp;

public class Blacklist {
    private Integer user_id;
    private Integer blacklisted_user_id;
    private Timestamp created_date;

    public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getBlacklisted_user_id() {
		return blacklisted_user_id;
	}

	public void setBlacklisted_user_id(Integer blacklisted_user_id) {
		this.blacklisted_user_id = blacklisted_user_id;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Blacklist() {
    }

    public Blacklist(Integer user_id, Integer blacklisted_user_id, Timestamp created_date) {
        this.user_id = user_id;
        this.blacklisted_user_id = blacklisted_user_id;
        this.created_date = created_date;
    }

}


