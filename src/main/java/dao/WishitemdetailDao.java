package dao;

import vo.WishItem;
import vo.WishItemDetail;

public interface WishitemdetailDao {
    WishItemDetail getWishItemById(int wishitem_id) throws Exception;
}