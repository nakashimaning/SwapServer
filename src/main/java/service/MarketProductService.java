package service;
//src/main/java/service/TransactionService.java

import vo.MarketProduct;
import vo.Transaction;
import java.util.List;

public interface MarketProductService {
 List<MarketProduct> getAllProduct(int userId);
}
