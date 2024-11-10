package dao.impl;

import dao.WishListDao;
import vo.WishItem;
import javax.sql.DataSource;
import common.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WishListDaoImpl implements WishListDao {
    private DataSource dataSource;

    public WishListDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public List<WishItem> getAllWishItems(int userId) {
        List<WishItem> wishItems = new ArrayList<>();
        String sql = "SELECT * FROM Wishitem WHERE wishitem_status = 0 AND wish_user_id <> ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                WishItem wishItem = new WishItem();
                wishItem.setWishItemId(resultSet.getInt("wishitem_id"));
                wishItem.setWishUserId(resultSet.getInt("wish_user_id"));
                wishItem.setWishCategoryId(resultSet.getInt("category_id"));
                wishItem.setWishCreatedDate(resultSet.getTimestamp("created_date"));
                wishItem.setWishDeleteDate(resultSet.getTimestamp("delete_date"));
                wishItem.setWishItemTitle(resultSet.getString("wishitem_name"));
                wishItem.setWishStatus(resultSet.getInt("wishitem_status"));
                wishItem.setWishDescription(resultSet.getString("description"));

                // 獲取圖片列表
                List<String> imageList = getWishItemImages(wishItem.getWishItemId());
                wishItem.setWishImageList(imageList);

                // TODO: 取得 Register 列表
                // List<Register> registerList = getRegisterList(wishItem.getWishItemId());
                // wishItem.setRegisterList(registerList);

                wishItems.add(wishItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wishItems;
    }

    // 查詢指定 wishItem 的圖片列表
    private List<String> getWishItemImages(Integer wishItemId) {
        List<String> imageList = new ArrayList<>();
        String sql = "SELECT wishitemimage_url FROM Wishitemimage WHERE wishitem_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, wishItemId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                imageList.add(resultSet.getString("wishitemimage_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageList;
    }
}

