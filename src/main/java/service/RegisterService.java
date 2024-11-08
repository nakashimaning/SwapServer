package service;

public interface RegisterService {
    boolean toggleRegister(Integer userId, Integer wishItemId) throws Exception;
    boolean isUserRegistered(Integer userId, Integer wishItemId) throws Exception;
    
}