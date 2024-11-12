package service.impl;

import dao.WishItemNewPostDao;
import service.WishItemNewPostService;
import vo.WishItem;

public class WishItemNewPostServiceImpl implements WishItemNewPostService {
    private WishItemNewPostDao wishItemNewPostDao;

    public WishItemNewPostServiceImpl(WishItemNewPostDao wishItemNewPostDao) {
        this.wishItemNewPostDao = wishItemNewPostDao;
    }

    @Override
    public void addWishItem(WishItem wishItem) {
        if (wishItem == null) {
            throw new IllegalArgumentException("Wish item cannot be null");
        }
        
        try {
            wishItemNewPostDao.insertWishItem(wishItem);
        } catch (Exception e) {
            System.err.println("Error adding wish item: " + e.getMessage());
            e.printStackTrace();
        }
    }
}