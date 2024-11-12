package service.impl;

import service.WishItemdetailService;
import dao.WishitemdetailDao;
import dao.impl.WishitemdetailDaoImpl;
import vo.WishItemDetail;

public class WishitemdetailServiceImpl implements WishItemdetailService {

    private WishitemdetailDao wishitemdetailDao;

    public WishitemdetailServiceImpl() throws Exception {
        wishitemdetailDao = new WishitemdetailDaoImpl();
    }

    @Override
    public WishItemDetail getWishItemDetails(Integer wishItemId) throws Exception {
        return wishitemdetailDao.getWishItemById(wishItemId);
    }

    @Override
    public boolean isWishItemFavoritedByUser(int userId, int wishItemId) throws Exception {
        return wishitemdetailDao.isWishItemFavoritedByUser(userId, wishItemId);
    }

    @Override
    public void addFavorite(int userId, int wishItemId) throws Exception {
        wishitemdetailDao.addFavorite(userId, wishItemId);
    }

    @Override
    public boolean isUserRegisteredForWishItem(int userId, int wishItemId) throws Exception {
        return wishitemdetailDao.isUserRegisteredForWishItem(userId, wishItemId);
    }

    @Override
    public void addRegistration(int userId, int wishItemId) throws Exception {
        wishitemdetailDao.addRegistration(userId, wishItemId);
    }
}
