package service;
//src/main/java/service/TransactionService.java

import vo.Transaction;
import java.util.List;

public interface TransactionService {
 List<Transaction> getGivenRatings(Integer userId);
 List<Transaction> getReceivedRatings(Integer userId);
 boolean updateRating(Integer transactionId, String review, Integer stars, boolean isProvider);
 Transaction getTransactionById(Integer transactionId);
}
