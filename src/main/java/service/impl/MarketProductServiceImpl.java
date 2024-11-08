package service.impl;

import dao.MarketProductDao;
import dao.impl.MarketProductDaoImpl;
import service.MarketProductService;
import vo.MarketProduct;
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

    @Override
    public MarketProduct getProductById(int productId, Integer userId) {
        return dao.getProductById(productId, userId);
    }
    
    // 新增收藏
    @Override
    public boolean addFavorite(int userId, int productId) {
        return dao.addFavorite(userId, productId);
    }
    
    // 移除收藏
    @Override
    public boolean removeFavorite(int userId, int productId) {
        return dao.removeFavorite(userId, productId);
    }
}
