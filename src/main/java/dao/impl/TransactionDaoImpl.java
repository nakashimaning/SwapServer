
//src/main/java/dao/impl/TransactionDAOImpl.java
package dao.impl;
import vo.Transaction;
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

	private static final String GET_GIVEN_RATINGS = 
		    "SELECT t.*, " +
		    	    "       p1.user_id as provider_user_id, " +
		    	    "       u1.username as provider_username, " +
		    	    "       u1.profile_pic as provider_profile_pic, " +
		    	    "       p2.user_id as seeker_user_id, " +
		    	    "       u2.username as seeker_username, " +
		    	    "       u2.profile_pic as seeker_profile_pic " +
		    	    "FROM Transaction t " +
		    	    "JOIN Product p1 ON t.provider_product_id = p1.product_id " +
		    	    "JOIN Product p2 ON t.seeker_product_id = p2.product_id " +
		    	    "JOIN User u1 ON p1.user_id = u1.user_id " +
		    	    "JOIN User u2 ON p2.user_id = u2.user_id " +
		    	    "WHERE ( p2.user_id = ?) " +
		    	    "AND t.status = 0 " +
		    	    "ORDER BY t.transaction_date DESC";
	
//    "SELECT t.*, " +
//    "       p1.user_id as provider_user_id, " +
//    "       u1.username as provider_username, " +
//    "       u1.profile_pic as provider_profile_pic, " +
//    "       p2.user_id as seeker_user_id, " +
//    "       u2.username as seeker_username, " +
//    "       u2.profile_pic as seeker_profile_pic " +
//    "FROM Transaction t " +
//    "JOIN Product p1 ON t.provider_product_id = p1.product_id " +
//    "JOIN Product p2 ON t.seeker_product_id = p2.product_id " +
//    "JOIN User u1 ON p1.user_id = u1.user_id " +
//    "JOIN User u2 ON p2.user_id = u2.user_id " +
//    "WHERE (p1.user_id = ? OR p2.user_id = ?) " +
//    "AND t.status = 0 " +
//    "ORDER BY t.transaction_date DESC";
	
//	private static final String GET_GIVEN_RATINGS = "SELECT t.* FROM Transaction t "
//			+ "JOIN Product p1 ON t.provider_product_id = p1.product_id "
//			+ "JOIN Product p2 ON t.seeker_product_id = p2.product_id " + "WHERE (p1.user_id = ? OR p2.user_id = ?) "
//			+ "AND t.status = 0 ORDER BY t.transaction_date DESC";

	private static final String GET_RECEIVED_RATINGS =
		    "SELECT t.*, " +
		    	    "       p1.user_id as provider_user_id, " +
		    	    "       u1.username as provider_username, " +
		    	    "       u1.profile_pic as provider_profile_pic, " +
		    	    "       p2.user_id as seeker_user_id, " +
		    	    "       u2.username as seeker_username, " +
		    	    "       u2.profile_pic as seeker_profile_pic " +
		    	    "FROM Transaction t " +
		    	    "JOIN Product p1 ON t.provider_product_id = p1.product_id " +
		    	    "JOIN Product p2 ON t.seeker_product_id = p2.product_id " +
		    	    "JOIN User u1 ON p1.user_id = u1.user_id " +
		    	    "JOIN User u2 ON p2.user_id = u2.user_id " +
		    	    "WHERE (p1.user_id = ? ) " +
		    	    "AND t.status = 0 " +
		    	    "ORDER BY t.transaction_date DESC";
	
//	private static final String GET_RECEIVED_RATINGS = "SELECT t.* FROM Transaction t "
//			+ "JOIN Product p1 ON t.provider_product_id = p1.product_id "
//			+ "JOIN Product p2 ON t.seeker_product_id = p2.product_id " + "WHERE (p1.user_id = ? OR p2.user_id = ?) "
//			+ "AND t.status = 0 ORDER BY t.transaction_date DESC";

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
	
	private static final String GET_TRANSACTION_BY_USER =
		    "SELECT t.*, " +
		    	    "       p1.user_id as provider_user_id, " +
		    	    "       u1.username as provider_username, " +
		    	    "       u1.profile_pic as provider_profile_pic, " +
		    	    "       p2.user_id as seeker_user_id, " +
		    	    "       u2.username as seeker_username, " +
		    	    "       u2.profile_pic as seeker_profile_pic " +
		    	    "FROM Transaction t " +
		    	    "JOIN Product p1 ON t.provider_product_id = p1.product_id " +
		    	    "JOIN Product p2 ON t.seeker_product_id = p2.product_id " +
		    	    "JOIN User u1 ON p1.user_id = u1.user_id " +
		    	    "JOIN User u2 ON p2.user_id = u2.user_id " +
		    	    "WHERE (p1.user_id = ? OR p2.user_id = ? ) " +
		    	    "AND t.status = 0 " +
		    	    "ORDER BY t.transaction_date DESC";

	
	private final String IS_USER_INVOLVED = 
		    "SELECT p1.user_id as provider_user_id, " +
		    "       p2.user_id as seeker_user_id " +
		    "FROM Transaction t " +
		    "JOIN Product p1 " +
		    "    ON t.provider_product_id = p1.product_id " +
		    "JOIN Product p2 " +
		    "    ON t.seeker_product_id = p2.product_id " +
		    "WHERE t.transaction_id = ?";
	
	private final String EXCHANGE_TRANSACTION_BY_USER_ID = "SELECT t.*, " +
            "u_provider.username AS providerUsername, " +
            "u_provider.profile_pic AS providerProfilePic, " +
            "p_provider.user_id AS provider_user_id, " +  // Fixed this
            "p_seeker.user_id AS seeker_user_id, " +     // Fixed this
            "u_seeker.username AS seekerUsername, " +
            "u_seeker.profile_pic AS seekerProfilePic, " +
            "pi_provider.productimage_url AS providerProductImageUrl, " +
            "pi_seeker.productimage_url AS seekerProductImageUrl " +
            "FROM Transaction t " +
            "JOIN Product p_provider ON t.provider_product_id = p_provider.product_id " +
            "JOIN Product p_seeker ON t.seeker_product_id = p_seeker.product_id " +
            "JOIN User u_provider ON p_provider.user_id = u_provider.user_id " +
            "JOIN User u_seeker ON p_seeker.user_id = u_seeker.user_id " +
            "LEFT JOIN Productimage pi_provider ON p_provider.product_id = pi_provider.product_id " +
            "LEFT JOIN Productimage pi_seeker ON p_seeker.product_id = pi_seeker.product_id " +
            "WHERE u_provider.user_id = ? OR u_seeker.user_id = ?";

	@Override
	public List<Transaction> getGivenRatings(Integer userId) {
		List<Transaction> list = new ArrayList<Transaction>();
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(GET_GIVEN_RATINGS)) {

			pstmt.setInt(1, userId);
//			pstmt.setInt(2, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Transaction transaction = new Transaction();
					// Set basic transaction info
	                transaction.setTransaction_id(rs.getInt("transaction_id"));
	                transaction.setProvider_product_id(rs.getInt("provider_product_id"));
	                transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
	                transaction.setStatus(rs.getInt("status"));
	                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
	                transaction.setProvider_review(rs.getString("provider_review"));
	                transaction.setSeeker_review(rs.getString("seeker_review"));
	                transaction.setProvider_star(rs.getInt("provider_star"));
	                transaction.setSeeker_star(rs.getInt("seeker_star"));
	                
	                // Set provider info
	                transaction.setProviderUserId(rs.getInt("provider_user_id"));
	                transaction.setProviderUsername(rs.getString("provider_username"));
	                transaction.setProviderprofilepic(rs.getString("provider_profile_pic"));
	                
	                // Set seeker info
	                transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
	                transaction.setSeekerUsername(rs.getString("seeker_username"));
	                transaction.setSeekerprofilepic(rs.getString("seeker_profile_pic"));
//					transaction.setTransaction_id(rs.getInt("transaction_id"));
//					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
//					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
//					transaction.setStatus(rs.getInt("status"));
//					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
//					transaction.setProvider_review(rs.getString("provider_review"));
//					transaction.setSeeker_review(rs.getString("seeker_review"));
//					transaction.setProvider_star(rs.getInt("provider_star"));
//					transaction.setSeeker_star(rs.getInt("seeker_star"));
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
		List<Transaction> list = new ArrayList<Transaction>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_RECEIVED_RATINGS)) {
			pstmt.setInt(1, userId);
//			pstmt.setInt(2, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Transaction transaction = new Transaction();
					// Set basic transaction info
	                transaction.setTransaction_id(rs.getInt("transaction_id"));
	                transaction.setProvider_product_id(rs.getInt("provider_product_id"));
	                transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
	                transaction.setStatus(rs.getInt("status"));
	                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
	                transaction.setProvider_review(rs.getString("provider_review"));
	                transaction.setSeeker_review(rs.getString("seeker_review"));
	                transaction.setProvider_star(rs.getInt("provider_star"));
	                transaction.setSeeker_star(rs.getInt("seeker_star"));
	                
	                // Set provider info
	                transaction.setProviderUserId(rs.getInt("provider_user_id"));
	                transaction.setProviderUsername(rs.getString("provider_username"));
	                transaction.setProviderprofilepic(rs.getString("provider_profile_pic"));
	                
	                // Set seeker info
	                transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
	                transaction.setSeekerUsername(rs.getString("seeker_username"));
	                transaction.setSeekerprofilepic(rs.getString("seeker_profile_pic"));
//					transaction.setTransaction_id(rs.getInt("transaction_id"));
//					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
//					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
//					transaction.setStatus(rs.getInt("status"));
//					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
//					transaction.setProvider_review(rs.getString("provider_review"));
//					transaction.setSeeker_review(rs.getString("seeker_review"));
//					transaction.setProvider_star(rs.getInt("provider_star"));
//					transaction.setSeeker_star(rs.getInt("seeker_star"));
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
					// Set basic transaction info
	                transaction.setTransaction_id(rs.getInt("transaction_id"));
	                transaction.setProvider_product_id(rs.getInt("provider_product_id"));
	                transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
	                transaction.setStatus(rs.getInt("status"));
	                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
	                transaction.setProvider_review(rs.getString("provider_review"));
	                transaction.setSeeker_review(rs.getString("seeker_review"));
	                transaction.setProvider_star(rs.getInt("provider_star"));
	                transaction.setSeeker_star(rs.getInt("seeker_star"));
	                
	                // Set provider info
	                transaction.setProviderUserId(rs.getInt("provider_user_id"));
	                transaction.setProviderUsername(rs.getString("provider_username"));
	                transaction.setProviderprofilepic(rs.getString("provider_profile_pic"));
	                
	                // Set seeker info
	                transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
	                transaction.setSeekerUsername(rs.getString("seeker_username"));
	                transaction.setSeekerprofilepic(rs.getString("seeker_profile_pic"));
//					transaction.setTransaction_id(rs.getInt("transaction_id"));
//					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
//					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
//					transaction.setStatus(rs.getInt("status"));
//					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
//					transaction.setProvider_review(rs.getString("provider_review"));
//					transaction.setSeeker_review(rs.getString("seeker_review"));
//					transaction.setProvider_star(rs.getInt("provider_star"));
//					transaction.setSeeker_star(rs.getInt("seeker_star"));
//					transaction.setProviderUserId(rs.getInt("provider_user_id"));
//					transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
					return transaction;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error getting transaction by ID", e);
		}
		return null;
	}

	
	@Override
	public List<Transaction> gettransactiobyuserid(Integer userId) {
		List<Transaction> list = new ArrayList<Transaction>();
		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(EXCHANGE_TRANSACTION_BY_USER_ID)) {

			pstmt.setInt(1, userId);
			pstmt.setInt(2, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Transaction transaction = new Transaction();
					// Set basic transaction info
	                transaction.setTransaction_id(rs.getInt("transaction_id"));
	                transaction.setProvider_product_id(rs.getInt("provider_product_id"));
	                transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
	                transaction.setStatus(rs.getInt("status"));
	                transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
	                transaction.setProvider_review(rs.getString("provider_review"));
	                transaction.setSeeker_review(rs.getString("seeker_review"));
	                transaction.setProvider_star(rs.getInt("provider_star"));
	                transaction.setSeeker_star(rs.getInt("seeker_star"));

	                // Set provider info
	                transaction.setProviderUserId(rs.getInt("provider_user_id"));
	                transaction.setProviderUsername(rs.getString("providerUsername"));  // Fix this line
	                transaction.setProviderprofilepic(rs.getString("providerProfilePic"));

	                // Set seeker info
	                transaction.setSeekerUserId(rs.getInt("seeker_user_id"));
	                transaction.setSeekerUsername(rs.getString("seekerUsername"));
	                transaction.setSeekerprofilepic(rs.getString("seekerProfilePic"));

	                // Set product image URLs
	                transaction.setProviderProductImageUrls(rs.getString("providerProductImageUrl"));
	                transaction.setSeekerProductImageUrls(rs.getString("seekerProductImageUrl"));


	                // Add the transaction object to the list
	                
//					transaction.setTransaction_id(rs.getInt("transaction_id"));
//					transaction.setProvider_product_id(rs.getInt("provider_product_id"));
//					transaction.setSeeker_product_id(rs.getInt("seeker_product_id"));
//					transaction.setStatus(rs.getInt("status"));
//					transaction.setTransaction_date(rs.getTimestamp("transaction_date"));
//					transaction.setProvider_review(rs.getString("provider_review"));
//					transaction.setSeeker_review(rs.getString("seeker_review"));
//					transaction.setProvider_star(rs.getInt("provider_star"));
//					transaction.setSeeker_star(rs.getInt("seeker_star"));
					list.add(transaction);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error getting given ratings", e);
		}
		return list;
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

	@Override
	public int addTransaction(Transaction transaction) {
		String sql = "INSERT INTO Transaction (provider_product_id, seeker_product_id, status) VALUES (?, ?, ?)";
	    
	    try (
	        Connection conn = ds.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        pstmt.setInt(1, transaction.getProvider_product_id());
	        pstmt.setInt(2, transaction.getSeeker_product_id());
	        pstmt.setInt(3, 0);  // 交易狀態0: 交易中 
	        
	        return pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return -1;
	}


}