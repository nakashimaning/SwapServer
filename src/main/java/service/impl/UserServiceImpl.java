package service.impl;

import service.UserService;
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
    public void register(User user) throws Exception {
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
        } catch (Exception e) {
            throw new Exception("Registration failed: " + e.getMessage(), e);
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

}