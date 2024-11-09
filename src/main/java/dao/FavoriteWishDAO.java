package dao;

import vo.FavoriteWish;

public interface FavoriteWishDAO {
    boolean isWishItemFavorited(Integer userId, Integer wishItemId) throws Exception;
    void addFavoriteWish(FavoriteWish favoriteWish) throws Exception;
    void removeFavoriteWish(Integer userId, Integer wishItemId) throws Exception;
}