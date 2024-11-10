package service;
//src/main/java/service/TransactionService.java

import vo.MarketProduct;
import vo.Transaction;
import java.util.List;

public interface MarketProductService {
    // 根據 userId 撈取多個商品的列表
    List<MarketProduct> getAllProduct(int userId);
    
    // 根據 productId 查詢單一商品的方法
    MarketProduct getProductById(int productId, Integer userId);

    // 新增 addFavorite 方法
    boolean addFavorite(int userId, int productId);
    
    // 移除收藏
    boolean removeFavorite(int userId, int productId);
    
    // 查詢有無提交過申請
    boolean hasAppliedForExchange(int userId, int productId);
    
    // 提出申請
    boolean applyForExchange(int userId, int toBeTradedProductId, int applyingProductId);
}

