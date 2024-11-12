package dao.impl;

import dao.ProductNewPostDao;
import vo.Product;
import javax.sql.DataSource;
import common.ServiceLocator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class ProductNewPostDaoImpl implements ProductNewPostDao {
    private DataSource dataSource;

    public ProductNewPostDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public void insertProduct(Product product) {
        String sql = "INSERT INTO Product (user_id, category_id, created_date, title, status, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // 設定 user_id 和 category_id，並處理 null 值
            if (product.getUserId() != null) {
                statement.setInt(1, product.getUserId());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }

            if (product.getCategoryId() != null) {
                statement.setInt(2, product.getCategoryId());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }

            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setString(4, product.getTitle());

//             設定 status，若為 null 則使用預設值 0
            if (product.getStatus() != null) {
                statement.setInt(5, product.getStatus());
            } else {
                statement.setInt(5, 0);  // 預設值為 0
            }

            statement.setString(6, product.getDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    product.setProductId(productId);
                    addProductImages(productId, product.getImageList());
                } else {
                    throw new RuntimeException("Creating product failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProductImages(int productId, List<String> imageList) {
        String sql = "INSERT INTO Productimage (product_id, productimage_url) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String imageUrl : imageList) {
                statement.setInt(1, productId);
                statement.setString(2, imageUrl);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
