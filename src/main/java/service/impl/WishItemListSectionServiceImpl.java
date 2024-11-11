package service.impl;

import java.sql.SQLException;
import java.util.List;

import dao.WishItemListSectionDao;
import service.WishItemListSectionService;
import vo.WishItemListSection;

public class WishItemListSectionServiceImpl implements WishItemListSectionService {
	private WishItemListSectionDao wishItemDAO;

	public WishItemListSectionServiceImpl() {
		this.wishItemDAO = new WishItemListSectionDao();
	}

	@Override
	public List<WishItemListSection> getWishItemsByRegistrantUserId(int userId) throws SQLException {
		return wishItemDAO.getWishItemsByRegistrantUserId(userId);
	}
}