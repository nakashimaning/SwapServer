package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.ProductDao;
import vo.Application;
import vo.ApplicationProduct;
import vo.Product;

public class ProductDaoImpl implements ProductDao {
	private DataSource ds;

	public ProductDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Swap");
	}

	// 取得我的市集物品
	@Override
	public List<Product> getProductsByUserId(int userId) {
		String sql = "SELECT * FROM Product WHERE user_id = ?";
		List<Product> productList = new ArrayList<Product>();
		try (Connection conn = ds.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, userId);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Product product = new Product();
					product.setProductId(rs.getInt("product_id"));
					product.setUserId(rs.getInt("user_id"));
					product.setCategoryId(rs.getInt("category_id"));
					product.setCreatedDate(rs.getTimestamp("created_date"));
					product.setDeleteDate(rs.getTimestamp("delete_date"));
					product.setTitle(rs.getString("title"));
					product.setStatus(rs.getInt("status"));
					product.setDescription(rs.getString("description"));
					productList.add(product);
				}
				
				return productList;
			} catch (Exception e) {
				e.printStackTrace();
	            throw new RuntimeException("查詢資料時發生錯誤: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
	        throw new RuntimeException("資料庫連線錯誤: " + e.getMessage());
		}
	}
	
	// 取得所有的照片連結
	@Override
	public List<String> getProductImagesByProductId(int productId) {
		String sql = "SELECT productimage_url FROM Productimage WHERE product_id = ? ";
		List<String> imageList = new ArrayList<String>();
		
		try (Connection conn = ds.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, productId);
		        
		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                String imageUrl = rs.getString("productImage_url");
		                imageList.add(imageUrl);
		            }
		            return imageList;
		        } catch (SQLException e) {
		            e.printStackTrace();
		            throw new Exception("查詢結果集時發生錯誤", e);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("資料庫連接或查詢準備時發生錯誤", e);
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new RuntimeException("發生未預期的錯誤", e);
		    }
	}

	// 取得所有申請交換的物品資訊
	@Override
	public List<ApplicationProduct> getApplicationsByProductId(int productId) {
		String sql = 
			    "SELECT ap.product_id as application_product_id, " +
			    "       ap.user_id, ap.category_id, ap.created_date, " +
			    "       ap.title, ap.status, ap.description, " +
			    "       u.profile_pic as user_avatar, " +
			    "       pi.productimage_url " +
			    "FROM Application a " +
			    "JOIN Product ap ON a.applying_product_id = ap.product_id " +
			    "JOIN User u ON ap.user_id = u.user_id " + 
			    "LEFT JOIN Productimage pi ON ap.product_id = pi.product_id " +
			    "WHERE a.tobetraded_product_id = ?";

		    try (
		        Connection conn = ds.getConnection();
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		    ) {
		        pstmt.setInt(1, productId);
		        
		        ResultSet rs = pstmt.executeQuery();
		        
		        Map<Integer, ApplicationProduct> productMap = new HashMap<>();
		        
		        while (rs.next()) {
		            Integer currentProductId = rs.getInt("application_product_id");
		            
		            if (!productMap.containsKey(currentProductId)) {
		                ApplicationProduct product = new ApplicationProduct();
		                product.setApplicationProductId(currentProductId);
		                product.setUserId(rs.getInt("user_id"));
		                product.setUserAvatar(rs.getString("user_avatar"));
		                product.setCategoryId(rs.getInt("category_id"));
		                product.setCreatedDate(rs.getTimestamp("created_date"));
//		                product.setDeleteDate(rs.getTimestamp("delete_date"));
		                product.setTitle(rs.getString("title"));
		                product.setStatus(rs.getInt("status"));
		                product.setDescription(rs.getString("description"));
		                product.setImageList(new ArrayList<>());
		                
		                productMap.put(currentProductId, product);
		            }
		            
		            String imageUrl = rs.getString("productimage_url");
		            if (imageUrl != null) {
		                productMap.get(currentProductId).getImageList().add(imageUrl);
		            }
		        }
		        return new ArrayList<>(productMap.values());
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null;
		    }
	}
}
