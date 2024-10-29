package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.Favorite_ProductDao;
import vo.Favorite_Product;

public class Favorite_ProductDaoImpl implements Favorite_ProductDao{
	private DataSource ds;
	
	public Favorite_ProductDaoImpl() throws NamingException{
		ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Swap");
	}

	@Override
	public int insert(Favorite_Product favorite_product) {
		String sql = "insert into favorite_Product(created_date) value (?)";
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setTimestamp(1, favorite_product.getCreated_date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
