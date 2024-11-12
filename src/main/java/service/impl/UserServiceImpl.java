package service.impl;

import service.UserService;

import java.sql.SQLException;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import vo.User;

public class UserServiceImpl implements UserService {

    private UserDao userDAO;

    public UserServiceImpl() throws Exception {
        // Initialize the UserDAO implementation
        this.userDAO = new UserDaoImpl();
    }


    // Register a new user
    @Override
    // 0 OK 1 BOOM
    public Integer register(User user) throws Exception {
        try {
            // Check if user with same email or username already exists
            User existingUserByEmail = userDAO.searchUserByEmail(user.getEmail());
            if (existingUserByEmail != null) {
                throw new Exception("User with this email already exists.");
            }
            User existingUserByUsername = userDAO.searchUserByUsername(user.getUsername());
            if (existingUserByUsername != null) {
                throw new Exception("Username is already taken.");
            }
            userDAO.addUser(user);
            return 0;
        } catch (Exception e) {

//            throw new Exception("Registration failed: " + e.getMessage(), e);
        	return 1;
        }
    }

    // Login user with username/email and password
    @Override
    public User login(String identifier, String password) throws Exception {
        try {
            User user = userDAO.searchUserByEmail(identifier);
            if (user == null) {
                // If not found by email, try by username
                user = userDAO.searchUserByUsername(identifier);
                if (user == null) {
                    throw new Exception("User not found.");
                }
            }
            if (!user.getPassword().equals(password)) {
                throw new Exception("Invalid password.");
            }
            return user;
        } catch (Exception e) {
            throw new Exception("Login failed: " + e.getMessage(), e);
        }
    }

    // Get user info by user ID
    @Override
    public User getUserInfo(Integer userId) throws Exception {
        try {
            User user = userDAO.searchUserById(userId);
            if (user == null) {
                throw new Exception("User not found.");
            }
            return user;
        } catch (Exception e) {
            throw new Exception("Failed to get user info: " + e.getMessage(), e);
        }
    }
    
    @Override
    public User getUserprofile(String email) throws Exception{
    	try {
            User user = userDAO.searchUserByEmail(email);
            return user;
        } catch (Exception e) {
            throw new Exception("Error getting user profile: " + e.getMessage(), e);
        }
    }
    
    @Override
    public User updateUserProfile(String email, String username, String profile_pic) throws Exception {
        try {
            // 查詢使用者資訊
            User user = userDAO.searchUserByEmail(email);

            if (user == null) {
                throw new Exception("User not found for email: " + email);
            }
            user.setUsername(username);
            user.setProfile_pic(profile_pic);
            userDAO.updateUserByEmail(user);  // 更新使用者資訊
            return user;  // 返回更新後的 User 物件
            
        } catch (Exception e) {
            throw new Exception("Error updating user profile: " + e.getMessage(), e);
        }
    }

}