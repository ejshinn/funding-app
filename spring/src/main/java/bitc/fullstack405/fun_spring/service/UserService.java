package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.UserEntity;

public interface UserService {
    UserEntity findUserByUserIdAndPw(String userId, String userPw);

    void saveUser(UserEntity user);

    Boolean isUserInfo(String userId, String userPw);

    UserEntity findByUserId(String userId);

}
