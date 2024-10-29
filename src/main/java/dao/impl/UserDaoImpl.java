package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.UserDao;
import vo.User;

public class UserDaoImpl implements UserDao{
	private DataSource ds;
	
	public UserDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Swap");
	}

	@Override
	public int insert(User user) {
		String sql = "insert into user(user_id, password, email, profile_pic, FCM_token) value(?, ?, ?, ?, ?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, user.getUser_id());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getProfile_pic());
			pstmt.setString(5, user.getFCM_token());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
