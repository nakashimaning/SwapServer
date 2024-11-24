package dao;

import vo.WishItemRegister;

public interface WishItemRegisterDAO {
    // 檢查用戶是否已經登記了該心願商品
    boolean isAlreadyRegistered(Integer registrantUserId, Integer wishitemId) throws Exception;
    // 新增心願商品登記
    void addRegister(WishItemRegister register) throws Exception;
    // 移除心願商品登記
    void removeRegister(Integer registrantUserId, Integer wishitemId) throws Exception;
}