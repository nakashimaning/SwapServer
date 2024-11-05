
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
	// TODO Auto-generated method stub
	return dao.isUserInvolved(userId, transactionId);
}
 


}
