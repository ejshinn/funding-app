package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.Login;
import bitc.fullstack405.fun_spring.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public int isUserInfo(String userId, String userPw) {
//        int result = loginRepository.countByUserIdAndUserPwAndDeletedYn(userId, userPw, 'N');
//
//        return result;
        return 1;
    }

    @Override
    public Login findUserIdForProfile(String userId) {

        return loginRepository.findByUserId(userId);
    }

    @Override
    public Login findByUserIdCheckSignOut(String userId) {

        return loginRepository.findByUserId(userId);
    }

    @Override
    public int userIdCheck(String userId) {
        int result = loginRepository.countByUserId(userId);

        return result;
    }

    @Override
    public int userEmailCheck(String email) {
        int result = loginRepository.countByEmail(email);

        return result;
    }

    @Override
    public void insertUser(Login log) {
        loginRepository.save(log);
    }

    @Override
    public Object getUserPw() {
        return null;
    }

    @Override
    public void updateUserPw(String userId, String userPw) {

//        loginRepository.changePw(userPw, userId);
    }

    @Override
    public Login findUserId(String email, String userPw) {

        return loginRepository.findByEmailAndUserPw(email, userPw);
    }
}
