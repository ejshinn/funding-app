package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.HomeInitDto;
import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.dto.UserFavoriteDto;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping({"", "/"})
    public HomeInitDto getHomeInitData(){

        return HomeInitDto.of(
                new ArrayList<String>(),
                projectService.getProjectListRanking(),
                projectService.getProjectListByDeadLine(),
                projectService.getHomeScrollProject(0)
        );
    }

    @GetMapping({"/homeScroll"})
    public List<ProjectDto> getHomeScrollProject(@RequestBody int pageNum){
        return projectService.getHomeScrollProject(pageNum);
    }

    @GetMapping("/page/favorite")
    public UserFavoriteDto getUserFavorite(@RequestBody String userId){
        return userService.getUserFavorite(userId);
    }
}
