
//src/main/java/service/impl/TransactionServiceImpl.java
package service.impl;

import dao.MarketProductDao;
import dao.TransactionDao;
import dao.impl.MarketProductDaoImpl;
import dao.impl.TransactionDaoImpl;
import service.MarketProductService;
import service.TransactionService;
import vo.MarketProduct;
import vo.Transaction;
import java.util.List;

public class MarketProductServiceImpl implements MarketProductService {
 private MarketProductDao dao;

 public MarketProductServiceImpl() {
     dao = new MarketProductDaoImpl();
 }

@Override
public List<MarketProduct> getAllProduct(int userId) {
	return dao.getAllProducts(userId);
}
}
