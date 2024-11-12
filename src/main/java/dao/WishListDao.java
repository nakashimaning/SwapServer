package dao;

import vo.WishItem;
import java.util.List;

public interface WishListDao {
    List<WishItem> getAllWishItems(int userId);
    // 其他方法如查找、插入、更新等可以在此定義
}
