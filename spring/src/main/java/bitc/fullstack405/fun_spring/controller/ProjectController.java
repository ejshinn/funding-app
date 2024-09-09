package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.EntityProjection;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    // 상세보기
    @GetMapping("/project/{projectId}")
    public ProjectEntity getProjectDetail(@PathVariable(name = "projectId") int projectId) {
        ProjectEntity project;
        project = projectService.getProjectDetail(projectId);

        return project;
    }

    // 리스트
    @GetMapping("/project/list")
    public Object getProjectList() {
        List<ProjectEntity> list;
        list = projectService.getProjectList();

        return list;
    }

    // 프로젝트 인기순
    @GetMapping("/project/list/ranking")
    public Object getProjectRankingList() {
        List<ProjectEntity> list;
        list = projectService.getProjectListRanking();

        return list;
    }

    // 검색 Key로 시작하는 title을 가진 프로젝트 리스트
    @GetMapping("/project/search")
    public Object getProjectSearch(@RequestBody String project) {
        List<ProjectEntity> list;
        list = projectService.getProjectListSearch(project);

        return list;
    }

    // 프로젝트 작성
    @GetMapping("/project/write")
    public void writeProject(
            @RequestParam int projectId,
            @RequestParam int goalAmount,
            @RequestParam int currentAmount,
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String contents,
            @RequestParam int perPrice,
            @RequestParam String thumbnail) {

        ProjectEntity project = new ProjectEntity();

        UserEntity user = userService.findByUserId(userId);

        project.setProjectId(projectId);
        project.setGoalAmount(goalAmount);
        project.setCurrentAmount(currentAmount);
        project.setTitle(title);
        project.setContents(contents);
        project.setPerPrice(perPrice);
        project.setThumbnail(thumbnail);
        project.setUser(user);

        projectService.getWriteProject(project);
    }
}
