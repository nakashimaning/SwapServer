package dao;

import java.util.List;

import vo.Application;
import vo.ApplicationProduct;
import vo.Product;
import vo.Productimage;

public interface ProductDao {
	List<Product> getProductsByUserId(int userId);
	List<String> getProductImagesByProductId(int productId);
	List<ApplicationProduct> getApplicationsByProductId(int productId);
}
