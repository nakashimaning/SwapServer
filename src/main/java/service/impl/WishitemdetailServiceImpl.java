package service.impl;

import service.WishItemdetailService;
import dao.WishitemdetailDao;
import dao.impl.WishitemdetailDaoImpl;
import vo.WishItem;
import vo.WishItemDetail;


public class WishitemdetailServiceImpl implements WishItemdetailService {

    private WishitemdetailDao wishitemdetailDao;

    public WishitemdetailServiceImpl() throws Exception {
        // Initialize the DAO
        wishitemdetailDao = new WishitemdetailDaoImpl();
    }

    @Override
    public WishItemDetail getWishItemDetails(Integer wishItemId) throws Exception {
        // Correctly call the DAO method
        return wishitemdetailDao.getWishItemById(wishItemId);  // Calling the correct method
    }
}
