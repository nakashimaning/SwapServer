package service;

import vo.User;

public interface UserService {

    // Register a new user
    Integer register(User user) throws Exception;

    // Login user with username/email and password
    User login(String identifier, String password) throws Exception;

    // Get user info by user ID
    User getUserInfo(Integer userId) throws Exception;
    
    User getUserprofile(String email) throws Exception;
    
    User updateUserProfile(String email, String username, String profile_pic) throws Exception;

}