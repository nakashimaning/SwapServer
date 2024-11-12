package vo;


public class Register {
    private Integer register_id;
    private Integer wishitem_id;
    private Integer registrant_user_id;
    private String userName;        // Added to match username from User table
    private String profilePic;      // Added to match profile_pic from User table
    private Integer userId;         // Added to match user_id from User table

    public Register() {
    }

    public Register(Integer register_id, Integer wishitem_id, Integer registrant_user_id) {
        this.register_id = register_id;
        this.wishitem_id = wishitem_id;
        this.registrant_user_id = registrant_user_id;
    }

    // Original Getters and Setters
    public Integer getRegister_id() {
        return register_id;
    }

    public void setRegister_id(Integer register_id) {
        this.register_id = register_id;
    }

    public Integer getWishitem_id() {
        return wishitem_id;
    }

    public void setWishitem_id(Integer wishitem_id) {
        this.wishitem_id = wishitem_id;
    }

    public Integer getRegistrant_user_id() {
        return registrant_user_id;
    }

    public void setRegistrant_user_id(Integer registrant_user_id) {
        this.registrant_user_id = registrant_user_id;
    }

    // New Getters and Setters for User information
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

//
//public class Register {
//    private Integer register_id;
//    private Integer wishitem_id;
//    private Integer registrant_user_id;
//
//    public Register() {
//    }
//
//    public Register(Integer register_id, Integer wishitem_id, Integer registrant_user_id) {
//        this.register_id = register_id;
//        this.wishitem_id = wishitem_id;
//        this.registrant_user_id = registrant_user_id;
//    }
//
//    // Getters and Setters
//    public Integer getRegister_id() {
//        return register_id;
//    }
//
//    public void setRegister_id(Integer register_id) {
//        this.register_id = register_id;
//    }
//
//    public Integer getWishitem_id() {
//        return wishitem_id;
//    }
//
//    public void setWishitem_id(Integer wishitem_id) {
//        this.wishitem_id = wishitem_id;
//    }
//
//    public Integer getRegistrant_user_id() {
//        return registrant_user_id;
//    }
//
//    public void setRegistrant_user_id(Integer registrant_user_id) {
//        this.registrant_user_id = registrant_user_id;
//    }
//}