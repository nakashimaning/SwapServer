package vo;

import java.sql.Timestamp;

public class Follow {
    private Integer follower_user_id;
    private Integer followed_user_id;
    
    public Integer getFollower_user_id() {
		return follower_user_id;
	}

	public void setFollower_user_id(Integer follower_user_id) {
		this.follower_user_id = follower_user_id;
	}

	public Integer getFollowed_user_id() {
		return followed_user_id;
	}

	public void setFollowed_user_id(Integer followed_user_id) {
		this.followed_user_id = followed_user_id;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	private Timestamp created_date;

    public Follow() {
    }

    public Follow(Integer follower_user_id, Integer followed_user_id, Timestamp created_date) {
        this.follower_user_id = follower_user_id;
        this.followed_user_id = followed_user_id;
        this.created_date = created_date;
    }

    // Getters and Setters
}
