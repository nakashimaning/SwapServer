package dao;

import java.sql.SQLException;
import java.util.List;
import vo.ProductListSection;

public interface ProductListSectionDao {
    List<ProductListSection> getProductsAppliedByUserId(int userId) throws SQLException;
}
