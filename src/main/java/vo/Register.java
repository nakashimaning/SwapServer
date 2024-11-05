package vo;

public class Register {
	private Integer userId;
	private String userName;
	private String profilePic;
	
	public Register() {
		
	}
	
	public Register(Integer user_id, String username, String profile_pic) {
		this.userId = user_id;
		this.userName = username;
		this.profilePic = profile_pic;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}


}
