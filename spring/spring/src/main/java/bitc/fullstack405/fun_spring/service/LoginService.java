package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.Login;

public interface LoginService {
    int isUserInfo(String userId, String userPw);

    Login findUserIdForProfile(String userId);

    Login findByUserIdCheckSignOut(String userId);

    int userIdCheck(String userId);

    int userEmailCheck(String email);

    void insertUser(Login log);

    Object getUserPw(); // xxx

    void updateUserPw(String userId, String userPw);

    Login findUserId(String email, String userPw);
}
