package service;

import vo.WishItem;

public interface WishItemdetailService {
    WishItem getWishItemDetails(Integer wishItemId) throws Exception;
}