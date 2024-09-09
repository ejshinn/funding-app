package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.service.FavoriteService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    // 좋아요 누른 유저 수
    @GetMapping("/favorite/user")
    public Object getFavoriteUserCount(@RequestBody int projectId) {
        return favoriteService.getFavoriteUserCount(projectId);
    }

    // 자신이 좋아요한 프로젝트 리스트
    @GetMapping("/favorite/project")
    public List<ProjectEntity> getFavoriteProject(@RequestBody String userId) {
//        List<FavoriteEntity> project = favoriteService.getFavoriteListByUserId(userId);

        return userService.findByUserId(userId).getProjectList();
    }

    // 좋아요 생성
    @PostMapping("/favorite")
    public void createFavorite(@RequestBody FavoriteEntity favorite) {

        favoriteService.createFavorite(favorite);
    }

    // 좋아요 삭제
    @DeleteMapping("favorite/delete")
    public void deleteFavorite(@RequestBody int projectId, @RequestBody String userId) {

        favoriteService.deleteFavorite(projectId, userId);
    }
}
