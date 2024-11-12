package dao;

import vo.MarketProduct;
import vo.Product;
import java.util.List;

public interface MarketProductDao {
	
	// 根據 userId 撈取多個商品的列表
    List<MarketProduct> getAllProducts(Integer userId);
    
    // 根據 productId 查詢單一商品的方法，包含 userId 參數
    MarketProduct getProductById(int productId, Integer userId);
    
    // 新增收藏方法
    boolean addFavorite(int userId, int productId);
    
    // 移除收藏
    boolean removeFavorite(int userId, int productId);

    // 查詢有無提交過申請
    boolean checkApplicationStatus(int userId, int productId);
    
    // 提出申請
    boolean addExchangeApplication(int userId, int toBeTradedProductId, int applyingProductId);

    // 根據 userId 撈取指定會員的所有發布商品
    List<MarketProduct> getUserProducts(int userId);
}
