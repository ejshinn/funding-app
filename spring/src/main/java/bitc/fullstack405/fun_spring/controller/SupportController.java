package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.SupportService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class SupportController {

    @Autowired
    private SupportService supportService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    // 후원한 유저 수
    @GetMapping("/support/user")
    public Object getSupportUserCount(@RequestBody int projectId) {
        return supportService.getSupportUserCount(projectId);
    }

    // 자신이 후원한 프로젝트 리스트
    @GetMapping("/support/project")
    public List<ProjectEntity> getSupportingProject(@RequestBody String userId) {
//        List<SupportEntity> support = supportService.getSupportingListByProject(userId);

        return userService.findByUserId(userId).getProjectList();
    }

    // 후원하기
    @PostMapping("/support")
    public void CreateSupport(@RequestBody int projectId, @RequestBody String userId) {
        UserEntity user = userService.findByUserId(userId);
//
//        ProjectEntity projectEntity = projectService.getProjectDetail(projectId);
//        projectEntity.setCurrentAmount(projectEntity.getCurrentAmount() + projectEntity.getPerPrice());
//        projectService.updateProject(projectEntity);
//

//        SupportEntity supportEntity = new SupportEntity();
//        supportEntity.setUser(user);
//        supportEntity.setProject(projectEntity);
//        supportEntity.setAmount(projectEntity.getPerPrice());

//        supportService.createSupport(supportEntity);
    }

    // 후원 취소
    @DeleteMapping("/support/delete")
    public void getSupportDelete(@RequestBody int projectId, @RequestBody String userId) {

//        ProjectEntity project = projectService.getProjectDetail(projectId);
//        project.setCurrentAmount(project.getCurrentAmount() - project.getPerPrice());
//        projectService.updateProject(project);
//
//        supportService.getSupportDelete(projectId, userId);
    }

}
