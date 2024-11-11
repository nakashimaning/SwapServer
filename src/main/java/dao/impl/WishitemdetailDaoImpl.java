package dao.impl;

import dao.WishitemdetailDao;
import vo.WishItem;
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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        WishItemDetail wishItemDetail = null;

        try {
            conn = dataSource.getConnection();

            // Get wish item details and publisher info
            String sql = "SELECT w.*, u.username, u.profile_pic, u.email, u.password, " +
                         "u.asseek_totalstarcount, u.asprovider_totalstarcount, " +
                         "u.asseek_totalreviewcount, u.asprovider_totalreviewcount, u.FCM_token " +
                         "FROM Wishitem w " +
                         "JOIN User u ON w.wish_user_id = u.user_id " +
                         "WHERE w.wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishitem_id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Initialize WishItemDetail and set basic details
                wishItemDetail = new WishItemDetail();
                wishItemDetail.setWishItemId(rs.getInt("wishitem_id"));
                wishItemDetail.setWishUserId(rs.getInt("wish_user_id"));
                wishItemDetail.setWishCategoryId(rs.getInt("category_id"));
                wishItemDetail.setWishCreatedDate(rs.getTimestamp("created_date"));
                wishItemDetail.setWishDeleteDate(rs.getTimestamp("delete_date"));
                wishItemDetail.setWishItemTitle(rs.getString("wishitem_name"));
                wishItemDetail.setWishStatus(rs.getInt("wishitem_status"));
                wishItemDetail.setWishDescription(rs.getString("description"));

                // Set publisher info
                User publisher = new User();
                publisher.setUser_id(rs.getInt("wish_user_id"));
                publisher.setUsername(rs.getString("username"));
                publisher.setEmail(rs.getString("email"));
                publisher.setPassword(rs.getString("password"));
                publisher.setProfile_pic(rs.getString("profile_pic"));
                publisher.setAsseek_totalstarcount(rs.getInt("asseek_totalstarcount"));
                publisher.setAsprovider_totalstarcount(rs.getInt("asprovider_totalstarcount"));
                publisher.setAsseek_totalreviewcount(rs.getInt("asseek_totalreviewcount"));
                publisher.setAsprovider_totalreviewcount(rs.getInt("asprovider_totalreviewcount"));
                publisher.setFCM_token(rs.getString("FCM_token"));

                wishItemDetail.setPublisher(publisher);
            } else {
                throw new Exception("WishItem not found");
            }

            // Get list of registrants (users who have registered for this wish item)
            sql = "SELECT r.registrant_user_id, u.username, u.profile_pic, u.email, u.password, " +
                  "u.asseek_totalstarcount, u.asprovider_totalstarcount, " +
                  "u.asseek_totalreviewcount, u.asprovider_totalreviewcount, u.FCM_token " +
                  "FROM Register r " +
                  "JOIN User u ON r.registrant_user_id = u.user_id " +
                  "WHERE r.wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishitem_id);
            rs = stmt.executeQuery();

            List<User> registrantList = new ArrayList<>();
            while (rs.next()) {
                User registrant = new User();
                registrant.setUser_id(rs.getInt("registrant_user_id"));
                registrant.setUsername(rs.getString("username"));
                registrant.setEmail(rs.getString("email"));
                registrant.setPassword(rs.getString("password"));
                registrant.setProfile_pic(rs.getString("profile_pic"));
                registrant.setAsseek_totalstarcount(rs.getInt("asseek_totalstarcount"));
                registrant.setAsprovider_totalstarcount(rs.getInt("asprovider_totalstarcount"));
                registrant.setAsseek_totalreviewcount(rs.getInt("asseek_totalreviewcount"));
                registrant.setAsprovider_totalreviewcount(rs.getInt("asprovider_totalreviewcount"));
                registrant.setFCM_token(rs.getString("FCM_token"));

                registrantList.add(registrant);
            }

            // Set the registerList
            wishItemDetail.setRegisterList(registrantList);

            // Fetch wish images (if necessary, add code for fetching image data)
            // For simplicity, assuming it's a separate query if images are stored elsewhere.

            // Return the populated WishItemDetail object
            return wishItemDetail;

        } catch (Exception e) {
            throw new Exception("Error fetching wish item details", e);
        } finally {
            // Close resources
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close(); // Assuming no connection pooling
        }
    }



}
