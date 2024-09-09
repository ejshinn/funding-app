package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.Login;
import bitc.fullstack405.fun_spring.service.LoginService;
import bitc.fullstack405.fun_spring.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    //  로그인 프로세스
    @PostMapping("/login")
    public ModelAndView loginProcess(@RequestParam(defaultValue = "N", required = false, value = "rememberId") List<String> rememberId, @RequestParam("userId")String userId, @RequestParam("userPw") String userPw, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView();

        int result = loginService.isUserInfo(userId, userPw);

        Login login = loginService.findUserIdForProfile(userId);

        if (userId != null && userPw != null && result == 1) {
            if (rememberId.get(0) != null && rememberId.get(0).equals("Y")) {
                CookieUtil.makeCookie(resp, "userId", userId, (60 * 60 * 24 * 7));
            }
            else {
                CookieUtil.deleteCookie(resp, "userId");
            }

            Login log = loginService.findUserIdForProfile(userId);

            HttpSession session = req.getSession();

            session.setAttribute("userId", userId);
            session.setAttribute("userPw", userPw);
//            session.setAttribute("userEmail", log.getEmail());

            session.setMaxInactiveInterval(60 * 60 * 1);

            mv.setViewName("redirect:/home");
        }
        else {
            mv.setViewName("redirect:/login?error=loginFailed");
        }

        return mv;
    }

    //  로그아웃 프로세스
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/login");
        HttpSession session = req.getSession();

        session.removeAttribute("userId");
        session.removeAttribute("userPw");

        session.invalidate();

        return mv;
    }

    //  화원가입 프로세스
    @PostMapping("/signIn")
    public ModelAndView signInProcess(Login log, @RequestParam("userPwChk") String userPwChk, @RequestParam("userId") String userId, @RequestParam("email") String email) throws Exception {

        ModelAndView mv = new ModelAndView();

        Login login = loginService.findByUserIdCheckSignOut(userId);

        if (Objects.equals(userPwChk, loginService.getUserPw())) {
            if (loginService.userIdCheck(userId) == 0) {
                if (loginService.userEmailCheck(email) == 0) {
                    loginService.insertUser(log);
                    mv.setViewName("redirect:/login");
                }
                else {
                    mv.setViewName("redirect:/signIn?error=existEmail");
                }
            }
            else {
                mv.setViewName("redirect:/signIn?error=existId");
            }
        }
        else {
            mv.setViewName("redirect:/signIn?error=pwChk");
        }
        return mv;
    }

//    //  비밀번호 변경 프로세스
//    @PutMapping("/changePassword")
//    public ModelAndView changePassword(@RequestParam("userId") String userId, @RequestParam("userPw") String userPw, @RequestParam("userPwChk") String userPwChk) throws Exception {
//        ModelAndView mv = new ModelAndView();
//
//        Login login = loginService.findByUserIdCheckSignOut(userId);
//
//        if (login.getDeletedYn() == 'N') {
//            if (userPw.equals(userPwChk)) {
//                loginService.updateUserPw(userId, userPw);
//                mv.setViewName("redirect:/login");
//            }
//            else {
//                mv.setViewName("redirect:/changePassword?error=pwChk");
//            }
//        }
//        else {
//            mv.setViewName("redirect:/changePassword?error=signOutUser");
//        }
//
//        return mv;
//    }

//    //  id 찾기 프로세스
//    @ResponseBody
//    @PostMapping("/findId")
//    public ModelAndView findId(@RequestParam("email") String email, @RequestParam("userPw") String userPw, @RequestParam(required = false, value = "error") String error) throws Exception {
//        ModelAndView mv = new ModelAndView();
//
//        if (error != null) {
//            mv.addObject("error", error);
//        }
//
//        Login userEntity = loginService.findUserId(email, userPw);
//
//
//        if (userEntity != null && email.equals(userEntity.getEmail()) && userPw.equals(userEntity.getUserPw())) {
//            if (userEntity.getDeletedYn() == 'N') {
//                mv.addObject("userId", userEntity.getUserId());
//                mv.setViewName("/login/foundUserId");
//            }
//            else {
//                mv.setViewName("redirect:/findId?error=signOutUser");
//            }
//        }
//        else {
//            mv.setViewName("redirect:/findId?error=notFoundUser");
//        }
//
//        return mv;
//    }
}
