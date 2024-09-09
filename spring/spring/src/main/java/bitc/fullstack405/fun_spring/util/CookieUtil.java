package bitc.fullstack405.fun_spring.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

  public static void makeCookie(HttpServletResponse resp, String cName, String cValue, int cTime) {
    Cookie cookie = new Cookie(cName, cValue);
    cookie.setPath("/");
    cookie.setMaxAge(cTime);
    resp.addCookie(cookie);
  }

  public static String readCookie(HttpServletRequest req, String cName) {
    String cookieValue = "";

    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie c : cookies) {
        String cookieName = c.getName();

        if (cName.equals(cookieName)) {
          cookieValue = c.getValue();
        }
      }
    }
    return cookieValue;
  }

  public static void deleteCookie(HttpServletResponse resp, String cName) {
    makeCookie(resp, cName, "", 0);
  }
}
