package dao;

import vo.Register;

public interface RegisterDao {
    boolean isUserRegistered(Integer wishItemId, Integer userId) throws Exception;
    void addRegister(Register register) throws Exception;
    void removeRegister(Integer wishItemId, Integer userId) throws Exception;
}