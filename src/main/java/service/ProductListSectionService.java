package service;

import java.sql.SQLException;
import java.util.List;
import vo.ProductListSection;

public interface ProductListSectionService {

    // Method to get products applied by a user based on their userId
    List<ProductListSection> getProductsAppliedByUserId(int userId) throws SQLException;
}
