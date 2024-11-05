package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import dao.ProductDao;
import dao.UserDao;
import dao.impl.ProductDaoImpl;
import service.ProductService;
import vo.Application;
import vo.ApplicationProduct;
import vo.Product;

public class ProductServiceImpl implements ProductService{
	private ProductDao productDao;
	
	public ProductServiceImpl() throws NamingException {
		productDao = new ProductDaoImpl();
	}
	
	@Override
	public List<Product> getMyProducts(int userId) {
		try {
            List<Product> products = productDao.getProductsByUserId(userId);
            List<Product> detailProductList = new ArrayList<>();
            
            for (Product product : products) {
                List<String> imageList = productDao.getProductImagesByProductId(product.getProductId());
                
                List<ApplicationProduct> applicationList = 
                    productDao.getApplicationsByProductId(product.getProductId());
                System.out.println("applicationList=" + applicationList);
                
                Product detailProduct = new Product(
            		product.getProductId(),
            		product.getUserId(),
            		product.getCategoryId(),
            		product.getCreatedDate(),
            		product.getTitle(),
            		product.getStatus(),
            		product.getDescription(),
                    imageList,
                    applicationList          
                );
                
                detailProductList.add(detailProduct);
            }
            
            return detailProductList;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("取得我的刊登物列表時發生錯誤: " + e.getMessage());
        }
    }

	@Override
	public String updateProduct(Product product) {
		Integer productId = product.getProductId();
	    String title = product.getTitle();
	    Integer status = product.getStatus();
	    String description = product.getDescription();

	    if (productId == null) {
	        return "無此物品";
	    }
	    if (title == null || title.trim().isEmpty()) {
	        return "物品名稱不能為空";
	    }
	    if (status == null) {
	        return "必須設定物品狀態";
	    }

	    int result = productDao.updateById(product);
	    
	    return result > 0 ? null : "更新失敗";
	}

	@Override
	public String deleteProduct(Product product) {
		Integer productId = product.getProductId();

	    if (productId == null) {
	        return "無此物品";
	    }

	    int result = productDao.deleteById(product);
	    
	    return result > 0 ? null : "刪除失敗";
	}
}
