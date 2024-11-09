
//src/main/java/service/impl/TransactionServiceImpl.java
package service.impl;

import dao.TransactionDao;
import dao.impl.TransactionDaoImpl;
import service.TransactionService;
import vo.Transaction;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
 private TransactionDao dao;

 public TransactionServiceImpl() {
     dao = new TransactionDaoImpl();
 }

 @Override
 public List<Transaction> getGivenRatings(Integer userId) {
     return dao.getGivenRatings(userId);
 }

 @Override
 public List<Transaction> getReceivedRatings(Integer userId) {
     return dao.getReceivedRatings(userId);
 }

 @Override
 public boolean updateRating(Integer transactionId, String review, Integer stars, boolean isProvider) {
     return dao.updateRating(transactionId, review, stars, isProvider);
 }

 @Override
 public Transaction getTransactionById(Integer transactionId) {
     return dao.getTransactionById(transactionId);
 }

@Override
public boolean isUserInvolved(Integer userId, Integer transactionId) {
	return dao.isUserInvolved(userId, transactionId);
}

	@Override
	public String addTransaction(Transaction transaction) {
		if (transaction == null) {
            return "交易資料不能為空";
        }
        
        if (transaction.getProvider_product_id() == null || 
            transaction.getSeeker_product_id() == null) {
            return "商品資料不完整";
        }
        
        int result = dao.addTransaction(transaction);
        return result > 0 ? null : "新增交易失敗";
	}
}
