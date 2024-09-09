package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.HomeInitDto;
import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private ProjectService projectService;

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
    public List<ProjectDto> getHomeScrollProject(@Param("pageNum") int pageNum){
        return projectService.getHomeScrollProject(pageNum);
    }
}
