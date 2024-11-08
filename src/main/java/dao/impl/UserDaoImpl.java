package dao.impl;
import dao.UserDao;
import vo.User;

import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.ServiceLocator;

public class UserDaoImpl implements UserDao {

	private DataSource dataSource;

	public UserDaoImpl() throws Exception {
		try {
//			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/swapapp");
			dataSource = ServiceLocator.getInstance().getDataSource();

		} catch (Exception e) {
			throw new Exception("Error initializing DataSource", e);
		}
	}

	// Add user into the database
	@Override
	public int addUser(User user) throws Exception {
		String sql = "INSERT INTO User (username, email, password, profile_pic, "
				+ "asseek_totalstarcount, asprovider_totalstarcount, "
				+ "asseek_totalreviewcount, asprovider_totalreviewcount, FCM_token) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getProfile_pic());
			pstmt.setInt(5, user.getAsseek_totalstarcount() != null ? user.getAsseek_totalstarcount() : 0);
			pstmt.setInt(6, user.getAsprovider_totalstarcount() != null ? user.getAsprovider_totalstarcount() : 0);
			pstmt.setInt(7, user.getAsseek_totalreviewcount() != null ? user.getAsseek_totalreviewcount() : 0);
			pstmt.setInt(8, user.getAsprovider_totalreviewcount() != null ? user.getAsprovider_totalreviewcount() : 0);
			pstmt.setString(9, user.getFCM_token());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error adding user", e);
		}
	}

	// Search user by user ID
	@Override
	public User searchUserById(Integer userId) throws Exception {
		String sql = "SELECT * FROM User WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error searching user by ID", e);
		}
	}

	// Search user by email
	@Override
	public User searchUserByEmail(String email) throws Exception {
		String sql = "select * from User where email = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, email);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapRowToUser(rs);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error searching user by email", e);
		}
	}
    // Search user by username
    @Override
    public User searchUserByUsername(String username) throws Exception {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error searching user by username", e);
        }
    }
	// Delete user by user ID
	@Override
	public int deleteUserById(Integer userId) throws Exception {
		String sql = "DELETE FROM User WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error deleting user by ID", e);
		}
	}

	// Delete user by email
	@Override
	public int deleteUserByEmail(String email) throws Exception {
		String sql = "DELETE FROM User WHERE email = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, email);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error deleting user by email", e);
		}
	}

	// Update user by user ID
	@Override
	public int updateUserById(User user) throws Exception {
		String sql = "UPDATE User SET username = ?, email = ?, password = ?, profile_pic = ?, "
				+ "asseek_totalstarcount = ?, asprovider_totalstarcount = ?, "
				+ "asseek_totalreviewcount = ?, asprovider_totalreviewcount = ?, FCM_token = ? " + "WHERE user_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getProfile_pic());
			pstmt.setInt(5, user.getAsseek_totalstarcount() != null ? user.getAsseek_totalstarcount() : 0);
			pstmt.setInt(6, user.getAsprovider_totalstarcount() != null ? user.getAsprovider_totalstarcount() : 0);
			pstmt.setInt(7, user.getAsseek_totalreviewcount() != null ? user.getAsseek_totalreviewcount() : 0);
			pstmt.setInt(8, user.getAsprovider_totalreviewcount() != null ? user.getAsprovider_totalreviewcount() : 0);
			pstmt.setString(9, user.getFCM_token());
			pstmt.setInt(10, user.getUser_id());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error updating user by ID", e);
		}
	}

	// Update user by email
	@Override
	public int updateUserByEmail(User user) throws Exception {
		String sql = "UPDATE User SET username = ?, password = ?, profile_pic = ?, "
				+ "asseek_totalstarcount = ?, asprovider_totalstarcount = ?, "
				+ "asseek_totalreviewcount = ?, asprovider_totalreviewcount = ?, FCM_token = ? " + "WHERE email = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getProfile_pic());
			pstmt.setInt(4, user.getAsseek_totalstarcount() != null ? user.getAsseek_totalstarcount() : 0);
			pstmt.setInt(5, user.getAsprovider_totalstarcount() != null ? user.getAsprovider_totalstarcount() : 0);
			pstmt.setInt(6, user.getAsseek_totalreviewcount() != null ? user.getAsseek_totalreviewcount() : 0);
			pstmt.setInt(7, user.getAsprovider_totalreviewcount() != null ? user.getAsprovider_totalreviewcount() : 0);
			pstmt.setString(8, user.getFCM_token());
			pstmt.setString(9, user.getEmail());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error updating user by email", e);
		}
	}

	// Helper method to map a ResultSet row to a User object
	private User mapRowToUser(ResultSet rs) throws SQLException {
		User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"),
				rs.getString("password"), rs.getString("profile_pic"), rs.getInt("asseek_totalstarcount"),
				rs.getInt("asprovider_totalstarcount"), rs.getInt("asseek_totalreviewcount"),
				rs.getInt("asprovider_totalreviewcount"), rs.getString("FCM_token"));
		return user;
	}
	
	@Override
	public void updateUser(User user) throws Exception {
		String sql = "UPDATE users SET username = ?, profile_pic = ? WHERE email = ?";
	
        try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            // 撰寫 SQL 更新邏輯
            
        	pstmt.setString(1, user.getUsername());
        	pstmt.setString(2, user.getProfile_pic());
        	pstmt.setString(3, user.getEmail());
        	pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error updating user: " + e.getMessage(), e);
        }
	}

	}

//	@Override
//	public int insert(User user) {
//		String sql = "insert into user(user_id, password, email, profile_pic, FCM_token) value(?, ?, ?, ?, ?)";
//		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
//			pstmt.setInt(1, user.getUser_id());
//			pstmt.setString(2, user.getPassword());
//			pstmt.setString(3, user.getEmail());
//			pstmt.setString(4, user.getProfile_pic());
//			pstmt.setString(5, user.getFCM_token());
//			return pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}

