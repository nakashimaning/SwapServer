package service;

import java.sql.SQLException;
import java.util.List;

import vo.WishItemListSection;

public interface WishItemListSectionService {

    // Method to get the list of wish items by registrant user ID
    List<WishItemListSection> getWishItemsByRegistrantUserId(int userId) throws SQLException;
}
