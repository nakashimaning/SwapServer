package service.impl;

import java.sql.SQLException;
import java.util.List;

import dao.impl.ProductListSectionDaoImpl;
import service.ProductListSectionService;
import vo.ProductListSection;

public class ProductListSectionServiceImpl implements ProductListSectionService {
    private ProductListSectionDaoImpl productDAO;

    public ProductListSectionServiceImpl() {
        this.productDAO = new ProductListSectionDaoImpl();
    }

    @Override
    public List<ProductListSection> getProductsAppliedByUserId(int userId) throws SQLException {
        return productDAO.getProductsAppliedByUserId(userId);
    }
}