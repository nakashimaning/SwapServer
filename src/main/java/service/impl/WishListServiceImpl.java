package service.impl;

import dao.WishListDao;
import dao.impl.WishListDaoImpl;
import service.WishListService;
import vo.WishItem;
import java.util.List;

public class WishListServiceImpl implements WishListService {
    private WishListDao dao;

    public WishListServiceImpl() {
        dao = new WishListDaoImpl();
    }

    @Override
    public List<WishItem> getAllWishItems(int userId) {
        return dao.getAllWishItems(userId);
    }
}
