package dao.impl;

import dao.WishItemRegisterDAO;
import vo.WishItemRegister;
import javax.sql.DataSource;
import common.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WishItemRegisterDAOImpl implements WishItemRegisterDAO {
    private DataSource dataSource;

    // 建構子 - 初始化資料來源
    public WishItemRegisterDAOImpl() throws Exception {
        try {
            dataSource = ServiceLocator.getInstance().getDataSource();
        } catch (Exception e) {
            throw new Exception("初始化資料來源時發生錯誤", e);
        }
    }

    @Override
    public boolean isAlreadyRegistered(Integer registrantUserId, Integer wishitemId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dataSource.getConnection();
            String sql = "SELECT 1 FROM Register WHERE registrant_user_id = ? AND wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, registrantUserId);
            stmt.setInt(2, wishitemId);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("檢查登記狀態時發生錯誤: " + e.getMessage() + 
                              "\nSQL State: " + e.getSQLState() + 
                              "\nError Code: " + e.getErrorCode(), e);
        } catch (Exception e) {
            throw new Exception("檢查登記狀態時發生錯誤: " + e.getMessage(), e);
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public void addRegister(WishItemRegister register) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO Register (wishitem_id, registrant_user_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, register.getWishitemId());
            stmt.setInt(2, register.getRegistrantUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("新增登記時發生錯誤: " + e.getMessage() + 
                              "\nSQL State: " + e.getSQLState() + 
                              "\nError Code: " + e.getErrorCode(), e);
        } catch (Exception e) {
            throw new Exception("新增登記時發生錯誤: " + e.getMessage(), e);
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public void removeRegister(Integer registrantUserId, Integer wishitemId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM Register WHERE registrant_user_id = ? AND wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, registrantUserId);
            stmt.setInt(2, wishitemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("移除登記時發生錯誤: " + e.getMessage() + 
                              "\nSQL State: " + e.getSQLState() + 
                              "\nError Code: " + e.getErrorCode(), e);
        } catch (Exception e) {
            throw new Exception("移除登記時發生錯誤: " + e.getMessage(), e);
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    // 統一關閉資源的方法
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new Exception("關閉資源時發生錯誤: " + e.getMessage(), e);
        }
    }
}