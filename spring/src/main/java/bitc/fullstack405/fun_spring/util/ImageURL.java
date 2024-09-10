package bitc.fullstack405.fun_spring.util;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageURL {

    @Value("${image-base-url}")
    private String baseUrl;

    public ArrayList<String> bannerImages() {
        String img1 = baseUrl + "bannerList/" + "1.jpg";
        String img2 = baseUrl + "bannerList/" + "2.jpg";
        String img3 = baseUrl + "bannerList/" + "3.jpg";
        return new ArrayList<>(List.of(img1, img2, img3));
    }

    public String projectImg(String imgName) {
        return baseUrl + "projectList/" + imgName;
    }

    public static String projectImg2(String imgName) {
        return "http://10.100.105.168:8080/" + "projectList/" + imgName;
    }
}
