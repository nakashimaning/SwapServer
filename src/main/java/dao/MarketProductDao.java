package dao;

import vo.MarketProduct;
import vo.Product;
import java.util.List;

public interface MarketProductDao {
	
	// 根據 userId 撈取多個商品的列表
    List<MarketProduct> getAllProducts(int userId);
    
    // 根據 productId 查詢單一商品的方法，包含 userId 參數
    MarketProduct getProductById(int productId, Integer userId);
    
    // 其他方法如查找、插入、更新等可以在此定義
}
