package dao.impl;

import dao.MarketProductDao;
import vo.Product;
import vo.Productimage;
import vo.Application;
import vo.ApplicationProduct;
import vo.MarketProduct;

import javax.sql.DataSource;

import common.ServiceLocator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarketProductDaoImpl implements MarketProductDao {
    private DataSource dataSource;

    public MarketProductDaoImpl() {
        dataSource = ServiceLocator.getInstance().getDataSource();
    }

    @Override
    public List<MarketProduct> getAllProducts(int userId) {
        List<MarketProduct> products = new ArrayList<>();
        //首頁物品不顯示下架及自己刊登的物品
        String sql = "SELECT * FROM Product WHERE status = 0 AND user_id <> ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            // 查詢user_id會員已申請的列表並轉換為 applicationList
            List<Integer> applicationProductList = getApplicationProductIdList(userId);
            // 查詢user_id的收藏列表
            List<Integer> favoriteProductList = getisFavorite(userId);
            
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

                // 獲取圖片列表
                List<String> imageList = getProductImages(product.getProductId());
                product.setImageList(imageList);
                
                // 查詢user_id會員已申請的列表並轉換為 applicationList
                product.setApplicationed(applicationProductList.contains(product.getProductId()));
                // 查詢user_id的收藏列表
                product.setFavorite(favoriteProductList.contains(product.getProductId()));
                
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageList;
    }

    // 查詢user_id會員已申請的列表並轉換為 applicationList
    private List<Integer> getApplicationProductIdList(int userId) {
    	List<Integer> applicationList = new ArrayList<Integer>();
        String sql = "SELECT * FROM Application WHERE user_id = ?";

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
    	List<Integer> favoriteProductList = new ArrayList<Integer>();
        String sql = "SELECT * FROM Favorite_Product WHERE user_id = ?";

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
}
