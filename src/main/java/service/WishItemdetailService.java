package service;

import vo.WishItem;
import vo.WishItemDetail;

public interface WishItemdetailService {
    WishItemDetail getWishItemDetails(Integer wishItemId) throws Exception;
}