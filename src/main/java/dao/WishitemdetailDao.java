package dao;

import vo.WishItemDetail;

public interface WishitemdetailDao {
    WishItemDetail getWishItemById(int wishitem_id) throws Exception;
    boolean isWishItemFavoritedByUser(int userId, int wishItemId) throws Exception;
    void addFavorite(int userId, int wishItemId) throws Exception;
    boolean isUserRegisteredForWishItem(int userId, int wishItemId) throws Exception;
    void addRegistration(int userId, int wishItemId) throws Exception;
}
