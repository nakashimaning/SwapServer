package vo;

import java.util.List;

public class MyPost {
	private List<Product> productList;
	private List<WishItem> wishItemList;
	
	public MyPost() {
		
	}
	
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public List<WishItem> getWishItemList() {
		return wishItemList;
	}
	public void setWishItemList(List<WishItem> wishItemList) {
		this.wishItemList = wishItemList;
	}
}
