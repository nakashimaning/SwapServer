package service;

public interface FavoriteWishService {
    boolean toggleFavorite(Integer userId, Integer wishItemId) throws Exception;
    boolean isWishItemFavorited(Integer userId, Integer wishItemId) throws Exception;
}