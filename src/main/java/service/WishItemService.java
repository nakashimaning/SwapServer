package service;

import java.util.List;
import vo.WishItem;

public interface WishItemService {
	List<WishItem> getMyWishItems(int userId);
	String updateWishItem(WishItem wishItem);
	String deleteWishItem(WishItem wishItem);
}