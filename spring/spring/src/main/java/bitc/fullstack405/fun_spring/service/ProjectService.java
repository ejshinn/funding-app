package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.util.List;

public interface ProjectService {
    List<ProjectEntity> getProjectList() throws Exception;
}
