//src/main/java/dao/TransactionDAO.java
package dao;

import vo.Transaction;
import java.util.List;

public interface TransactionDao {
	List<Transaction> getGivenRatings(Integer userId);
	List<Transaction> getReceivedRatings(Integer userId);
	boolean updateRating(Integer transactionId, String review, Integer stars, boolean isProvider);
	Transaction getTransactionById(Integer transactionId);
	public boolean isUserInvolved(Integer userId, Integer transactionId);
	int addTransaction(Transaction transaction);
	List<Transaction> gettransactiobyuserid(Integer userId); 
 }

