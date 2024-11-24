package service.impl;

import dao.WishItemRegisterDAO;
import dao.impl.WishItemRegisterDAOImpl;
import service.WishItemRegisterService;
import vo.WishItemRegister;

public class WishItemRegisterServiceImpl implements WishItemRegisterService {
    private WishItemRegisterDAO wishItemRegisterDAO;

    // 建構子 - 初始化 DAO
    public WishItemRegisterServiceImpl() throws Exception {
        wishItemRegisterDAO = new WishItemRegisterDAOImpl();
    }

    @Override
    public boolean toggleRegister(Integer registrantUserId, Integer wishitemId) throws Exception {
        // 檢查是否已經登記
        boolean isRegistered = wishItemRegisterDAO.isAlreadyRegistered(registrantUserId, wishitemId);
        if (isRegistered) {
            // 如果已登記，則取消登記
            wishItemRegisterDAO.removeRegister(registrantUserId, wishitemId);
            return false; // 表示現在是未登記狀態
        } else {
            // 如果未登記，則新增登記
            WishItemRegister register = new WishItemRegister();
            register.setRegistrantUserId(registrantUserId);
            register.setWishitemId(wishitemId);
            wishItemRegisterDAO.addRegister(register);
            return true; // 表示現在是已登記狀態
        }
    }

    @Override
    public boolean isAlreadyRegistered(Integer registrantUserId, Integer wishitemId) throws Exception {
        return wishItemRegisterDAO.isAlreadyRegistered(registrantUserId, wishitemId);
    }
}