package service;

public interface WishItemRegisterService {
    // 切換登記狀態（如果已登記則取消，如果未登記則新增）
    boolean toggleRegister(Integer registrantUserId, Integer wishitemId) throws Exception;
    // 檢查是否已登記
    boolean isAlreadyRegistered(Integer registrantUserId, Integer wishitemId) throws Exception;
}