package dao.impl;

import dao.MarketProductDao;
import vo.MarketProduct;

import javax.sql.DataSource;
import common.ServiceLocator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarketProductDaoImpl implements MarketProductDao {
    private DataSource dataSource;

    public MarketProductDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    // 根據 userId 撈取多個商品的列表
    @Override
    public List<MarketProduct> getAllProducts(int userId) {
        List<MarketProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE status = 0 AND user_id <> ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            // 查詢user_id會員已申請的列表並轉換為 applicationList
            List<Integer> applicationProductList = getApplicationProductIdList(userId);
            System.out.println("Application product list size: " + applicationProductList.size());

            // 查詢user_id的收藏列表
            List<Integer> favoriteProductList = getisFavorite(userId);
            System.out.println("Favorite product list size: " + favoriteProductList.size());

            while (resultSet.next()) {
                MarketProduct product = new MarketProduct();
                product.setProductId(resultSet.getInt("product_id"));
                product.setUserId(resultSet.getInt("user_id"));
                product.setCategoryId(resultSet.getInt("category_id"));
                product.setCreatedDate(resultSet.getTimestamp("created_date"));
                product.setDeleteDate(resultSet.getTimestamp("delete_date"));
                product.setTitle(resultSet.getString("title"));
                product.setStatus(resultSet.getInt("status"));
                product.setDescription(resultSet.getString("description"));

                System.out.println("Processing product ID: " + product.getProductId());

                // 獲取圖片列表並設置圖片
                List<String> imageList = getProductImages(product.getProductId());
                product.setImageList(imageList);

                System.out.println("Image list size for product ID " + product.getProductId() + ": " + imageList.size());

                // 設置申請和收藏狀態，檢查是否包含在相應列表中
                product.setApplicationed(applicationProductList.contains(product.getProductId()));
                product.setFavorite(favoriteProductList.contains(product.getProductId()));

                System.out.println("Product " + product.getProductId() + " - isApplicationed: " + product.isApplicationed() + ", isFavorite: " + product.isFavorite());

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // 查詢指定產品的圖片列表
    private List<String> getProductImages(Integer productId) {
        List<String> imageList = new ArrayList<>();
        String sql = "SELECT productimage_url FROM Productimage WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                imageList.add(resultSet.getString("productimage_url"));
            }
            System.out.println("Product ID: " + productId + ", image list size: " + imageList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageList;
    }

    // 查詢user_id會員已申請的列表並轉換為 applicationList
    private List<Integer> getApplicationProductIdList(int userId) {
        List<Integer> applicationList = new ArrayList<>();
        String sql = "SELECT tobetraded_product_id FROM Application WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                applicationList.add(resultSet.getInt("tobetraded_product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return applicationList;
    }

    // 查詢user_id的收藏列表
    private List<Integer> getisFavorite(int userId) {
        List<Integer> favoriteProductList = new ArrayList<>();
        String sql = "SELECT product_id FROM Favorite_Product WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                favoriteProductList.add(resultSet.getInt("product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return favoriteProductList;
    }

    // 根據 productId 查詢單一商品的方法
    @Override
    public MarketProduct getProductById(int productId, Integer currentUserId) {
        MarketProduct product = null;
        String sql = "SELECT Product.*, " +
                     "       User.username AS providerName, " +
                     "       User.profile_pic AS providerPhotoUrl " +
                     "  FROM Product " +
                     "  JOIN User ON User.user_id = Product.user_id " +
                     " WHERE Product.product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = new MarketProduct();
                product.setProductId(resultSet.getInt("product_id"));
                product.setUserId(resultSet.getInt("user_id"));
                product.setCategoryId(resultSet.getInt("category_id"));
                product.setCreatedDate(resultSet.getTimestamp("created_date"));
                product.setDeleteDate(resultSet.getTimestamp("delete_date"));
                product.setTitle(resultSet.getString("title"));
                product.setStatus(resultSet.getInt("status"));
                product.setProviderName(resultSet.getString("providerName"));
                product.setProviderPhotoUrl(resultSet.getString("providerPhotoUrl"));
                product.setDescription(resultSet.getString("description"));
                product.setImageList(getProductImages(productId));

                // 若 currentUserId 不為 null，則檢查該用戶的收藏和申請狀態
                if (currentUserId != null) {
                    List<Integer> favoriteProductList = getisFavorite(currentUserId);
                    product.setFavorite(favoriteProductList.contains(productId));

                    List<Integer> applicationProductList = getApplicationProductIdList(currentUserId);
                    product.setApplicationed(applicationProductList.contains(productId));
                }
                System.out.println("Retrieved product ID: " + productId + " with images: " + product.getImageList().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }


    // 新增收藏方法 
    @Override
    public boolean addFavorite(int userId, int productId) {
        String sql = "INSERT INTO Favorite_Product (user_id, product_id, created_date) VALUES (?, ?, NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            
            int rowsAffected = statement.executeUpdate(); // 獲取影響行數
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Favorite added successfully for userId: " + userId + ", productId: " + productId);
                return true;
            } else {
                System.out.println("Favorite not added. No rows affected.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException occurred while adding favorite.");
            e.printStackTrace(); // 打印具體的 SQL 錯誤
            return false;
        }
    }

    // 移除收藏
    @Override
    public boolean removeFavorite(int userId, int productId) {
        String sql = "DELETE FROM Favorite_Product WHERE user_id = ? AND product_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查詢有無提交過申請
    @Override
    public boolean checkApplicationStatus(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM Application WHERE user_id = ? AND tobetraded_product_id = ? AND application_status = 0";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // 若有資料表示已提出申請
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 提出申請
    @Override
    public boolean addExchangeApplication(int userId, int toBeTradedProductId, int applyingProductId) {
        String sql = "INSERT INTO Application (user_id, tobetraded_product_id, applying_product_id, application_date, application_status) VALUES (?, ?, ?, NOW(), 0)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, toBeTradedProductId);
            statement.setInt(3, applyingProductId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 根據 userId 撈取指定會員的所有發布商品
    @Override
    public List<MarketProduct> getUserProducts(int userId) {
        List<MarketProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE user_id = ? AND status = 0";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MarketProduct product = new MarketProduct();
                product.setProductId(resultSet.getInt("product_id"));
                product.setUserId(resultSet.getInt("user_id"));
                product.setCategoryId(resultSet.getInt("category_id"));
                product.setCreatedDate(resultSet.getTimestamp("created_date"));
                product.setDeleteDate(resultSet.getTimestamp("delete_date"));
                product.setTitle(resultSet.getString("title"));
                product.setStatus(resultSet.getInt("status"));
                product.setDescription(resultSet.getString("description"));
                product.setImageList(getProductImages(product.getProductId()));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
