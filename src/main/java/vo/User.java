package vo;

public class User {
	private Integer user_id;
	private String username;
	private String email;
	private String password;
	private String profile_pic;
	private Integer asseek_totalstarcount;
	private Integer asprovider_totalstarcount;
	private Integer asseek_totalreviewcount;
	private Integer asprovider_totalreviewcount;
	private String FCM_token;

	public User(Integer user_id, String username, String email, String password, String profile_pic,
			Integer asseek_totalstarcount, Integer asprovider_totalstarcount, Integer asseek_totalreviewcount,
			Integer asprovider_totalreviewcount, String FCM_token) {
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profile_pic = profile_pic;
		this.asseek_totalstarcount = asseek_totalstarcount;
		this.asprovider_totalstarcount = asprovider_totalstarcount;
		this.asseek_totalreviewcount = asseek_totalreviewcount;
		this.asprovider_totalreviewcount = asprovider_totalreviewcount;

	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	public Integer getAsseek_totalstarcount() {
		return asseek_totalstarcount;
	}

	public void setAsseek_totalstarcount(Integer asseek_totalstarcount) {
		this.asseek_totalstarcount = asseek_totalstarcount;
	}

	public Integer getAsprovider_totalstarcount() {
		return asprovider_totalstarcount;
	}

	public void setAsprovider_totalstarcount(Integer asprovider_totalstarcount) {
		this.asprovider_totalstarcount = asprovider_totalstarcount;
	}

	public Integer getAsseek_totalreviewcount() {
		return asseek_totalreviewcount;
	}

	public void setAsseek_totalreviewcount(Integer asseek_totalreviewcount) {
		this.asseek_totalreviewcount = asseek_totalreviewcount;
	}

	public Integer getAsprovider_totalreviewcount() {
		return asprovider_totalreviewcount;
	}

	public void setAsprovider_totalreviewcount(Integer asprovider_totalreviewcount) {
		this.asprovider_totalreviewcount = asprovider_totalreviewcount;
	}

	public String getFCM_token() {
		return FCM_token;
	}

	public void setFCM_token(String fCM_token) {
		FCM_token = fCM_token;
	}
}
