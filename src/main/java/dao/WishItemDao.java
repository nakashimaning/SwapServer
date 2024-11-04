package dao;

import java.util.List;
import vo.Register;
import vo.WishItem;

public interface WishItemDao {
	List<WishItem> getWishItemsByUserId(int userId);
	List<String> getWishItemsImagesById(int wishItemId);
	List<Register> getRegistersUsersByProductId(int productId);
}
