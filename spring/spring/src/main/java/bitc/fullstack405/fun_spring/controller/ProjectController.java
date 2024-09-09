package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    public List<ProjectEntity> getProjectList() throws Exception {
//    public Object getProjectList() throws Exception {

//        var list = projectService.getProjectList();
        List<ProjectEntity> list = projectService.getProjectList();

        UserEntity u1 = list.get(0).getUser();
        System.out.println(u1.getUserId());

        return list;
    }

    @PostMapping("/project/write")
    public void writeProject(@RequestBody ProjectEntity project) throws Exception{
        System.out.println("--");

    }
}
