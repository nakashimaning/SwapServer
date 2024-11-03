package service;

import java.util.List;

import vo.Product;

public interface ProductService {
	List<Product> getMyProducts(int userId);
}
