package dao.impl;

import vo.Register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import common.ServiceLocator;
import dao.RegisterDao;

public class RegisterDAOImpl implements RegisterDao {
	private DataSource dataSource;

	public RegisterDAOImpl() throws Exception {

		try {
//			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/swapapp");
			dataSource = ServiceLocator.getInstance().getDataSource();

		} catch (Exception e) {
			throw new Exception("Error initializing DataSource", e);

		}
	}

    @Override
    public boolean isUserRegistered(Integer wishItemId, Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isRegistered = false;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT 1 FROM Register WHERE wishitem_id = ? AND registrant_user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishItemId);
            stmt.setInt(2, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                isRegistered = true;
            }
        } catch (Exception e) {
            throw new Exception("Error checking registration status", e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return isRegistered;
    }

    @Override
    public void addRegister(Register register) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO Register (wishitem_id, registrant_user_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, register.getWishitem_id());
            stmt.setInt(2, register.getRegistrant_user_id());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error registering for wish item", e);
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    @Override
    public void removeRegister(Integer wishItemId, Integer userId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM Register WHERE wishitem_id = ? AND registrant_user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishItemId);
            stmt.setInt(2, userId);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error removing registration", e);
        } finally {
            if (stmt != null) stmt.close();
        }
    }
}