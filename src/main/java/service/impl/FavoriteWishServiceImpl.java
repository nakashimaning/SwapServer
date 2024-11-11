package service.impl;

import dao.FavoriteWishDAO;
import dao.impl.FavoriteWishDAOImpl;
import service.FavoriteWishService;
import vo.FavoriteWish;

import java.sql.Timestamp;

public class FavoriteWishServiceImpl implements FavoriteWishService {

    private FavoriteWishDAO favoriteWishDAO;

    public FavoriteWishServiceImpl() throws Exception {
        favoriteWishDAO = new FavoriteWishDAOImpl();
    }

    @Override
    public boolean toggleFavorite(Integer userId, Integer wishItemId) throws Exception {
        boolean isFavorited = favoriteWishDAO.isWishItemFavorited(userId, wishItemId);

        if (isFavorited) {
            favoriteWishDAO.removeFavoriteWish(userId, wishItemId);
            return false; // Now unfavorited
        } else {
            FavoriteWish favoriteWish = new FavoriteWish();
            favoriteWish.setUser_id(userId);
            favoriteWish.setWishitem_id(wishItemId);
            favoriteWish.setCreated_date(new Timestamp(System.currentTimeMillis()));
            favoriteWishDAO.addFavoriteWish(favoriteWish);
            return true; // Now favorited
        }
    }

    @Override
    public boolean isWishItemFavorited(Integer userId, Integer wishItemId) throws Exception {
        return favoriteWishDAO.isWishItemFavorited(userId, wishItemId);
    }
}