package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import dao.WishItemDao;
import dao.impl.WishItemDaoImpl;
import service.WishItemService;
import vo.Register;
import vo.WishItem;
import vo.Wishitemimage;

public class WishItemServiceImpl implements WishItemService{
	private WishItemDao wishItemDao;
	
	public WishItemServiceImpl() throws NamingException {
		wishItemDao = new WishItemDaoImpl();
	}

	@Override
	public List<WishItem> getMyWishItems(int userId) {
		try {
            List<WishItem> wishItems = wishItemDao.getWishItemsByUserId(userId);
            List<WishItem> detailWishItemList = new ArrayList<>();
            
            for (WishItem wishItem : wishItems) {
                List<String> imageList = wishItemDao.getWishItemsImagesById(wishItem.getWishItemId());
                
                List<Register> registerUsers = wishItemDao.getRegistersUsersByProductId(wishItem.getWishItemId());
                
                WishItem detailWishItem = new WishItem(
                    wishItem.getWishItemId(),
                    wishItem.getWishUserId(),
                    wishItem.getWishCategoryId(),
                    wishItem.getWishCreatedDate(),
                    wishItem.getWishItemTitle(),
                    wishItem.getWishStatus(),
                    wishItem.getWishDescription(),
                    imageList,
                    registerUsers
                );
                
                detailWishItemList.add(detailWishItem);
            }
            
            return detailWishItemList;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("取得我的許願物品列表時發生錯誤: " + e.getMessage());
        }
	}
}
