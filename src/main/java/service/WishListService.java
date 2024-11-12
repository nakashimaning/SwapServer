package service;

import vo.WishItem;
import java.util.List;

public interface WishListService {
    List<WishItem> getAllWishItems(int userId);
}
