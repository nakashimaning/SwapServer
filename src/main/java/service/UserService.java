package service;

import vo.User;

public interface UserService {

    // Register a new user
    void register(User user) throws Exception;

    // Login user with username/email and password
    User login(String identifier, String password) throws Exception;

    // Get user info by user ID
    User getUserInfo(Integer userId) throws Exception;

}