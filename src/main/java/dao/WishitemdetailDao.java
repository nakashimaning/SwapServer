package dao;

import vo.WishItem;

public interface WishitemdetailDao {
    WishItem getWishItemById(int wishitem_id) throws Exception;
}