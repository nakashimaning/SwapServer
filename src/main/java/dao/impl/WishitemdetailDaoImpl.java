package dao.impl;

import dao.WishitemdetailDao;
import vo.WishItemDetail;
import vo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import common.ServiceLocator;

public class WishitemdetailDaoImpl implements WishitemdetailDao {

    private DataSource dataSource;

    public WishitemdetailDaoImpl() throws Exception {
        try {
            dataSource = ServiceLocator.getInstance().getDataSource();
        } catch (Exception e) {
            throw new Exception("Error initializing DataSource", e);
        }
    }

    @Override
    public WishItemDetail getWishItemById(int wishitem_id) throws Exception {
        WishItemDetail wishItemDetail = null;
        
        String sql = "SELECT w.wishitem_id, w.wish_user_id, w.category_id, w.created_date, " +
                     "w.delete_date, w.wishitem_name, w.wishitem_status, w.description, " +
                     "u.user_id, u.username, u.profile_pic " +
                     "FROM Wishitem w " +
                     "JOIN User u ON w.wish_user_id = u.user_id " +
                     "WHERE w.wishitem_id = ?";
                     
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, wishitem_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    wishItemDetail = new WishItemDetail();
                    wishItemDetail.setWishItemId(rs.getInt("wishitem_id"));
                    wishItemDetail.setWishUserId(rs.getInt("wish_user_id"));
                    wishItemDetail.setWishCategoryId(rs.getInt("category_id"));
                    wishItemDetail.setWishCreatedDate(rs.getTimestamp("created_date"));
                    wishItemDetail.setWishDeleteDate(rs.getTimestamp("delete_date"));
                    wishItemDetail.setWishItemTitle(rs.getString("wishitem_name"));
                    wishItemDetail.setWishStatus(rs.getInt("wishitem_status"));
                    wishItemDetail.setWishDescription(rs.getString("description"));

                    User publisher = new User();
                    publisher.setUser_id(rs.getInt("user_id"));
                    publisher.setUsername(rs.getString("username"));
                    publisher.setProfile_pic(rs.getString("profile_pic"));
                    wishItemDetail.setPublisher(publisher);
                } else {
                    throw new Exception("WishItem not found");
                }
            }
        }
        
        sql = "SELECT r.registrant_user_id, u.username " +
              "FROM Register r " +
              "JOIN User u ON r.registrant_user_id = u.user_id " +
              "WHERE r.wishitem_id = ?";
              
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, wishitem_id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> registrantList = new ArrayList<>();
                while (rs.next()) {
                    User registrant = new User();
                    registrant.setUser_id(rs.getInt("registrant_user_id"));
                    registrant.setUsername(rs.getString("username"));
                    registrantList.add(registrant);
                }
                wishItemDetail.setRegisterList(registrantList);
            }
        }
        
        return wishItemDetail;
    }

    @Override
    public boolean isWishItemFavoritedByUser(int userId, int wishItemId) throws Exception {
        String sql = "SELECT 1 FROM FavoriteWish WHERE user_id = ? AND wishitem_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new Exception("Error checking favorite status", e);
        }
    }

    @Override
    public void addFavorite(int userId, int wishItemId) throws Exception {
        String sql = "INSERT INTO FavoriteWish (user_id, wishitem_id, created_date) VALUES (?, ?, NOW())";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new Exception("Error adding favorite", e);
        }
    }

    @Override
    public boolean isUserRegisteredForWishItem(int userId, int wishItemId) throws Exception {
        String sql = "SELECT 1 FROM Register WHERE user_id = ? AND wishitem_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new Exception("Error checking registration status", e);
        }
    }

    @Override
    public void addRegistration(int userId, int wishItemId) throws Exception {
        String sql = "INSERT INTO Register (user_id, wishitem_id, created_date) VALUES (?, ?, NOW())";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new Exception("Error adding registration", e);
        }
    }
}
