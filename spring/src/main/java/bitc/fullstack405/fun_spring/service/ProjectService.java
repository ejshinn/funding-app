package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.util.List;

public interface ProjectService {
    ProjectEntity getProjectDetail(int project);

    List<ProjectEntity> getProjectList();

    List<ProjectEntity> getProjectListRanking();

    List<ProjectEntity> getProjectListSearch(String project);

    void getWriteProject(ProjectEntity project);


    void updateProject(ProjectEntity project);
}
