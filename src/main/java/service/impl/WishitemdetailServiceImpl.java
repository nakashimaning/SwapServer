package service.impl;

import service.WishItemdetailService;
import dao.WishitemdetailDao;
import dao.impl.WishitemdetailDaoImpl;
import vo.WishItem;

public class WishitemdetailServiceImpl implements WishItemdetailService {

    private WishitemdetailDao wishItemDAO;

    public WishitemdetailServiceImpl() throws Exception {
        wishItemDAO = new WishitemdetailDaoImpl();
    }

    @Override
    public WishItem getWishItemDetails(Integer wishItemId) throws Exception {
        return wishItemDAO.getWishItemById(wishItemId);
    }
}