package dao;

import vo.User;

public interface UserDao {
//	int insert (User user);

    // Add user into the database
    int addUser(User user) throws Exception;

    // Search user by user ID
    User searchUserById(Integer userId) throws Exception;

    // Search user by email
    User searchUserByEmail(String email) throws Exception;

    
    // Delete user by user ID
    int deleteUserById(Integer userId) throws Exception;

    // Delete user by email
    int deleteUserByEmail(String email) throws Exception;

    // Update user by user ID
    int updateUserById(User user) throws Exception;

    // Update user by email
    int updateUserByEmail(User user) throws Exception;

	User searchUserByUsername(String username) throws Exception;
	
	void updateUser(User user) throws Exception;
}
