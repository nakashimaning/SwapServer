package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import common.ServiceLocator;
import service.ProductListSectionService;
import vo.ProductListSection;
import vo.Productimage;

public class ProductListSectionDaoImpl implements ProductListSectionService {
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
    public List<ProductListSection> getProductsAppliedByUserId(int userId) throws SQLException {
        String sql = "SELECT p.*, pi.productimage_id, pi.productimage_url " +
                     "FROM Application a " +
                     "JOIN Product p ON a.tobetraded_product_id = p.product_id " +
                     "LEFT JOIN ProductImage pi ON p.product_id = pi.product_id " +
                     "WHERE a.user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userId);

        ResultSet rs = pstmt.executeQuery();
        List<ProductListSection> products = new ArrayList<>();
        ProductListSection currentProduct = null;
        int lastProductId = -1;

        while (rs.next()) {
            int productId = rs.getInt("product_id");
            if (productId != lastProductId) {
                currentProduct = new ProductListSection();
                currentProduct.setProductId(productId);
                currentProduct.setUserId(rs.getInt("user_id"));
                currentProduct.setCategoryId(rs.getInt("category_id"));
                currentProduct.setCreatedDate(rs.getTimestamp("created_date"));
                currentProduct.setDeleteDate(rs.getTimestamp("delete_date"));
                currentProduct.setTitle(rs.getString("title"));
                currentProduct.setStatus(rs.getInt("status"));
                currentProduct.setDescription(rs.getString("description"));
                currentProduct.setImages(new ArrayList<>());
                products.add(currentProduct);
                lastProductId = productId;
            }

            int productImageId = rs.getInt("productimage_id");
            if (!rs.wasNull()) {
                Productimage image = new Productimage();
                image.setProductimage_id(productImageId);
                image.setProduct_id(productId);
                image.setProductimage_url(rs.getString("productimage_url"));
                currentProduct.getImages().add(image);
            }
        }
        rs.close();
        pstmt.close();
        return products;
    }
}