
//src/main/java/dao/impl/TransactionDAOImpl.java
package dao.impl;

import vo.Transaction;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import common.ServiceLocator;
import dao.TransactionDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
	private static DataSource ds = null;
	static {
		try {
//         Context ctx = new InitialContext();
//         ds = (DataSource) ctx.lookup("java:comp/env/jdbc/example");
			ds = ServiceLocator.getInstance().getDataSource();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String GET_GIVEN_RATINGS = "SELECT t.* FROM Transaction t "
			+ "JOIN Product p1 ON t.provider_product_id = p1.product_id "
			+ "JOIN Product p2 ON t.seeker_product_id = p2.product_id " + "WHERE (p1.user_id = ? OR p2.user_id = ?) "
			+ "AND t.status = 0 ORDER BY t.transaction_date DESC";

	private static final String GET_RECEIVED_RATINGS = "SELECT t.* FROM Transaction t "
			+ "JOIN Product p1 ON t.provider_product_id = p1.product_id "
			+ "JOIN Product p2 ON t.seeker_product_id = p2.product_id " + "WHERE (p1.user_id = ? OR p2.user_id = ?) "
			+ "AND t.status = 0 ORDER BY t.transaction_date DESC";

	private static final String UPDATE_PROVIDER_RATING = "UPDATE Transaction SET provider_review = ?, provider_star = ? WHERE transaction_id = ?";

	private static final String UPDATE_SEEKER_RATING = "UPDATE Transaction SET seeker_review = ?, seeker_star = ? WHERE transaction_id = ?";

//	private static final String GET_BY_ID = "SELECT * FROM Transaction WHERE transaction_id = ?";
	private static final String GET_BY_ID = 
		    "SELECT t.*, " +
		    "       p1.user_id as provider_user_id, " +
		    "       p2.user_id as seeker_user_id " +
		    "FROM Transaction t " +
		    "JOIN Product p1 " +
		    "    ON t.provider_product_id = p1.product_id " +
		    "JOIN Product p2 " +
		    "    ON t.seeker_product_id = p2.product_id " +
		    "WHERE t.transaction_id = ?";


	
	private final String IS_USER_INVOLVED = 
		    "SELECT p1.user_id as provider_user_id, " +
		    "       p2.user_id as seeker_user_id " +
		    "FROM Transaction t " +
		    "JOIN Product p1 " +
		    "    ON t.provider_product_id = p1.product_id " +
		    "JOIN Product p2 " +
		    "    ON t.seeker_product_id = p2.product_id " +
		    "WHERE t.transaction_id = ?";

	


	@Override
	public List<Transaction> getGivenRatings(Integer userId) {
		List<Transaction> list = new ArrayList();
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_GIVEN_RATINGS)) {

			pstmt.setInt(1, userId);
			pstmt.setInt(2, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Transaction transaction = new Transaction();
					transaction.setTransaction_id(rs.getInt("transaction_id"));
					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
					transaction.setStatus(rs.getInt("status"));
					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
					transaction.setProvider_review(rs.getString("provider_review"));
					transaction.setSeeker_review(rs.getString("seeker_review"));
					transaction.setProvider_star(rs.getInt("provider_star"));
					transaction.setSeeker_star(rs.getInt("seeker_star"));
					list.add(transaction);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error getting given ratings", e);
		}
		return list;
	}

	@Override
	public List<Transaction> getReceivedRatings(Integer userId) {
		List<Transaction> list = new ArrayList();

		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_RECEIVED_RATINGS)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Transaction transaction = new Transaction();
					transaction.setTransaction_id(rs.getInt("transaction_id"));
					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
					transaction.setStatus(rs.getInt("status"));
					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
					transaction.setProvider_review(rs.getString("provider_review"));
					transaction.setSeeker_review(rs.getString("seeker_review"));
					transaction.setProvider_star(rs.getInt("provider_star"));
					transaction.setSeeker_star(rs.getInt("seeker_star"));
					list.add(transaction);
				}
			} catch (SQLException e) {
				throw new RuntimeException("Error getting received ratings", e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean updateRating(Integer transactionId, String review, Integer stars, boolean isProvider) {
		String sql = isProvider ? UPDATE_PROVIDER_RATING : UPDATE_SEEKER_RATING;
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, review);
			pstmt.setInt(2, stars);
			pstmt.setInt(3, transactionId);

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new RuntimeException("Error updating rating", e);
		}
	}

	@Override
	public Transaction getTransactionById(Integer transactionId) {
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_BY_ID)) {

			pstmt.setInt(1, transactionId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Transaction transaction = new Transaction();
					transaction.setTransaction_id(rs.getInt("transaction_id"));
					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
					transaction.setStatus(rs.getInt("status"));
					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
					transaction.setProvider_review(rs.getString("provider_review"));
					transaction.setSeeker_review(rs.getString("seeker_review"));
					transaction.setProvider_star(rs.getInt("provider_star"));
					transaction.setSeeker_star(rs.getInt("seeker_star"));
					transaction.setProviderUserId(rs.getInt("provider_user_id"));
					transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
					return transaction;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error getting transaction by ID", e);
		}
		return null;
	}


	@Override
	public boolean isUserInvolved(Integer userId, Integer transactionId) {

	    
	    try (Connection conn = ds.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(IS_USER_INVOLVED)) {
	        
	        pstmt.setInt(1, transactionId);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int providerUserId = rs.getInt("provider_user_id");
	                int seekerUserId = rs.getInt("seeker_user_id");
	                
	                return (userId.equals(providerUserId) || userId.equals(seekerUserId));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}


}