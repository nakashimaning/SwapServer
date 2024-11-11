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
	public List<MarketProduct> getAllProducts(int userId) {
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

	// 查詢有無提交過申請
	@Override
	public boolean hasAppliedForExchange(int userId, int productId) {
		return dao.checkApplicationStatus(userId, productId);
	}

	// 提出申請
	@Override
	public boolean applyForExchange(int userId, int toBeTradedProductId, int applyingProductId) {
		return dao.addExchangeApplication(userId, toBeTradedProductId, applyingProductId);
	}

	// 根據 userId 撈取指定會員的所有發布商品
	@Override
	public List<MarketProduct> getUserProducts(int userId) {
		return dao.getUserProducts(userId);
	}
}
