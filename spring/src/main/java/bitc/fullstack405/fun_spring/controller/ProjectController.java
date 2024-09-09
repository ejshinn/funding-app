package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.ProjectServiceImpl;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.EntityProjection;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    // 상세보기
    @GetMapping("/project/{projectId}")
    public ProjectDto getProjectDetail(@PathVariable(name = "projectId") int projectId) {
        ProjectDto project;
        project = projectService.getProjectDetail(projectId);

        return project;
    }

    // 리스트
    @GetMapping("/list")
    public Object getProjectList() {
        List<ProjectDto> list;
        list = projectService.getProjectList();

        return list;
    }

    // 프로젝트 인기순
//    @CrossOrigin(origins = "http://10.100.105.203:8080")
    @GetMapping("/list/ranking")
    public Object getProjectRankingList() {
        List<ProjectDto> list;
        list = projectService.getProjectListRanking();

        return list;
    }

    // 검색 Key로 시작하는 title을 가진 프로젝트 리스트
    @GetMapping("/search")
    public Object getProjectSearch(@RequestBody String project) {
        List<ProjectDto> list;
        list = projectService.getProjectListSearch(project);

        return list;
    }

    // 프로젝트 작성
    @GetMapping("/write")
    public void writeProject(
            @RequestBody ProjectDto projectDto) {



        projectService.getWriteProject(projectDto);
    }

    @GetMapping("/project/deadline")
    public List<ProjectDto> deadlineProject(){
        var list = projectService.getProjectListByDeadLine();
        return list;
    }
}
