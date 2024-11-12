package dao.impl;

import dao.WishItemNewPostDao;
import vo.WishItem;
import javax.sql.DataSource;
import common.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class WishItemNewPostDaoImpl implements WishItemNewPostDao {
    private DataSource dataSource;

    public WishItemNewPostDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public void insertWishItem(WishItem wishItem) {
        String sql = "INSERT INTO Wishitem (wish_user_id, category_id, created_date, wishitem_name, wishitem_status, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, wishItem.getWishUserId());
            statement.setInt(2, wishItem.getWishCategoryId());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setString(4, wishItem.getWishItemTitle());
            statement.setInt(5, wishItem.getWishStatus());
            statement.setString(6, wishItem.getWishDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating wish item failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int wishItemId = generatedKeys.getInt(1);
                    wishItem.setWishItemId(wishItemId);
                    addWishItemImages(wishItemId, wishItem.getWishImageList());
                } else {
                    throw new RuntimeException("Creating wish item failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWishItemImages(int wishItemId, List<String> imageList) {
        String sql = "INSERT INTO Wishitemimage (wishitem_id, wishitemimage_url) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String imageUrl : imageList) {
                statement.setInt(1, wishItemId);
                statement.setString(2, imageUrl);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
