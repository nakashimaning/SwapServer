package service;

import java.util.List;

import vo.Product;
import vo.WishItem;

public interface ProductService {
	List<Product> getMyProducts(int userId);
	String updateProduct(Product product);
	String deleteProduct(Product product);
}
