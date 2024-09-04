package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.dto.Login;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository extends JpaRepository<Login, Long> {
//    int countByUserIdAndUserPwAndDeletedYn(String userId, String userPw, char n);

    Login findByUserId(String userId);

    int countByEmail(String email);

    int countByUserId(String userId);

    Login findByEmailAndUserPw(String email, String userPw);

//    void changePw(String userPw, String userId);
}
