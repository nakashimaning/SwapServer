package service;

import vo.WishItemDetail;

public interface WishItemdetailService {
    WishItemDetail getWishItemDetails(Integer wishItemId) throws Exception;
    boolean isWishItemFavoritedByUser(int userId, int wishItemId) throws Exception;
    void addFavorite(int userId, int wishItemId) throws Exception;
    boolean isUserRegisteredForWishItem(int userId, int wishItemId) throws Exception;
    void addRegistration(int userId, int wishItemId) throws Exception;
}
