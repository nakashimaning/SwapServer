package service.impl;

import dao.RegisterDao;
import dao.impl.RegisterDAOImpl;
import service.RegisterService;
import vo.Register;

public class RegisterServiceImpl implements RegisterService {

    private RegisterDao registerDAO;

    public RegisterServiceImpl() throws Exception {
        registerDAO = new RegisterDAOImpl();
    }

    @Override
    public boolean toggleRegister(Integer userId, Integer wishItemId) throws Exception {
        boolean isRegistered = registerDAO.isUserRegistered(wishItemId, userId);

        if (isRegistered) {
            registerDAO.removeRegister(wishItemId, userId);
            return false; // Now unregistered
        } else {
            Register register = new Register();
            register.setWishitem_id(wishItemId);
            register.setRegistrant_user_id(userId);
            registerDAO.addRegister(register);
            return true; // Now registered
        }
    }

    @Override
    public boolean isUserRegistered(Integer userId, Integer wishItemId) throws Exception {
        return registerDAO.isUserRegistered(wishItemId, userId);
    }
}