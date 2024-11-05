package dao;

import vo.MarketProduct;
import vo.Product;
import java.util.List;

public interface MarketProductDao {
    List<MarketProduct> getAllProducts(int userId);
    // 其他方法如查找、插入、更新等可以在此定義
}
