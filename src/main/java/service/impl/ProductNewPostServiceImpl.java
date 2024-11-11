package service.impl;

import dao.ProductNewPostDao;
import service.ProductNewPostService;
import vo.Product;

public class ProductNewPostServiceImpl implements ProductNewPostService {
    private ProductNewPostDao productNewPostDao;

    public ProductNewPostServiceImpl(ProductNewPostDao productNewPostDao) {
        this.productNewPostDao = productNewPostDao;
    }

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        try {
            // 如果 imageList 為空或 null，可以跳過圖片相關操作
            if (product.getImageList() == null || product.getImageList().isEmpty()) {
                System.out.println("No images provided for product, proceeding without images.");
            } else {
                System.out.println("Images provided, proceeding to save images.");
                // 如果有其他圖片相關操作，這裡可以處理
            }

            productNewPostDao.insertProduct(product);
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
