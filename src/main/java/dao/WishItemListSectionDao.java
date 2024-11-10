package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import common.ServiceLocator;
import vo.WishItemListSection;
import vo.Wishitemimage;

public class WishItemListSectionDao {
    private static Connection conn = null;

	private static DataSource ds = null;
	static {
		try {
//         Context ctx = new InitialContext();
//         ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
			ds = ServiceLocator.getInstance().getDataSource();
			conn = ds.getConnection(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public List<WishItemListSection> getWishItemsByRegistrantUserId(int userId) throws SQLException {
        String sql = "SELECT wi.*, wii.wishitemimage_id, wii.wishitemimage_url " +
                     "FROM Register r " +
                     "JOIN `Wishitem` wi ON r.wishitem_id = wi.wishitem_id " +
                     "LEFT JOIN `Wishitemimage` wii ON wi.wishitem_id = wii.wishitem_id " +
                     "WHERE r.registrant_user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);

        ResultSet rs = pstmt.executeQuery();
        List<WishItemListSection> wishItems = new ArrayList<>();
        WishItemListSection currentWishItem = null;
        int lastWishItemId = -1;

        while (rs.next()) {
            int wishItemId = rs.getInt("wishitem_id");
            if (wishItemId != lastWishItemId) {
                currentWishItem = new WishItemListSection();
                currentWishItem.setWishItemId(wishItemId);
                currentWishItem.setWishUserId(rs.getInt("wish_user_id"));
                currentWishItem.setWishItemName(rs.getString("wishitem_name"));
                currentWishItem.setCreatedDate(rs.getTimestamp("created_date"));
                currentWishItem.setDeleteDate(rs.getTimestamp("delete_date"));
                currentWishItem.setCategoryId(rs.getInt("category_id"));
                currentWishItem.setDescription(rs.getString("description"));
                currentWishItem.setStatus(rs.getInt("wishlist_status"));
                currentWishItem.setImages(new ArrayList<>());
                wishItems.add(currentWishItem);
                lastWishItemId = wishItemId;
            }

            int wishItemImageId = rs.getInt("wishitemimage_id");
            if (!rs.wasNull()) {
                Wishitemimage image = new Wishitemimage();
                image.setWishitemimage_id(wishItemImageId);
                image.setWishitem_id(wishItemId);
                image.setWishitemimage_url(rs.getString("wishitemimage_url"));
                currentWishItem.getImages().add(image);
            }
        }
        rs.close();
        pstmt.close();
        return wishItems;
    }
}