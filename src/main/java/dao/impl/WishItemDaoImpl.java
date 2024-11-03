package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.WishItemDao;
import vo.Register;
import vo.User;
import vo.WishItem;

public class WishItemDaoImpl implements WishItemDao{
	private DataSource ds;

	public WishItemDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Swap");
	}

	@Override
	public List<WishItem> getWishItemsByUserId(int userId) {
		String sql = "SELECT * FROM Wishitem WHERE wish_user_id = ?";
        List<WishItem> wishItemList = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WishItem wishItem = new WishItem();
                    wishItem.setWishItemId(rs.getInt("wishitem_id"));
                    wishItem.setWishUserId(rs.getInt("wish_user_id"));
                    wishItem.setWishCategoryId(rs.getInt("category_id"));
                    wishItem.setWishCreatedDate(rs.getTimestamp("created_date"));
                    wishItem.setWishItemTitle(rs.getString("wishitem_name"));
                    wishItem.setWishStatus(rs.getInt("wishitem_status"));
                    wishItem.setWishDescription(rs.getString("description"));
                    
                    wishItemList.add(wishItem);
                }
                return wishItemList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("取得許願物品列表時發生錯誤: " + e.getMessage());
        }
	}

	@Override
	public List<String> getWishItemsImagesById(int wishItemId) {
		String sql = "SELECT wishitemimage_url FROM Wishitemimage WHERE wishitem_id = ?";
        List<String> imageList = new ArrayList<>();
		
        try (Connection conn = ds.getConnection();
        	 PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, wishItemId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String imageUrl = rs.getString("wishitemimage_url");
                    imageList.add(imageUrl);
                }
                return imageList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("取得許願物品圖片時發生錯誤: " + e.getMessage());
        }
	}

	@Override
	public List<Register> getRegistersUsersByUserId(int userId) {
		String sql = 
	            "SELECT u.user_id, u.username, u.profile_pic, u.email " +
	            "FROM User u " +
	            "JOIN Wishitem w ON u.user_id = w.wish_user_id " +
	            "WHERE w.wish_user_id = ?";
	        
        List<Register> registerList = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Register register = new Register();
                    register.setUserId(rs.getInt("user_id"));
                    register.setUserName(rs.getString("username"));
                    register.setProfilePic(rs.getString("profile_pic"));
                    registerList.add(register);
                }
                return registerList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("取得登記願望用戶列表時發生錯誤: " + e.getMessage());
        }
	}
}
