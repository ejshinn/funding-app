package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.repository.ProjectRepository;
import bitc.fullstack405.fun_spring.repository.SupportRepository;
import bitc.fullstack405.fun_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public int getSupportUserCount(int projectId) {

        return supportRepository.countByProject_ProjectId(projectId);
    }

    @Override
    public List<ProjectDto> getSupportingListByUserId(String userId) {

        return supportRepository.findAllByUser_UserId(userId)
                .stream()
                .map(supportEntity -> {
                    return ProjectDto.of(supportEntity.getProject());
                }).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void createSupport(int projectId, String userId) {
        ProjectEntity project = projectRepository.findByProjectId(projectId);
        UserEntity user = userRepository.findByUserId(userId);

        project.setCurrentAmount(project.getCurrentAmount() + project.getPerPrice());
        projectService.updateProject(project);

        SupportEntity support = new SupportEntity();
        support.setAmount(project.getPerPrice());
        support.setUser(user);
        support.setProject(project);

        supportRepository.save(support);
    }

    @Override
    public void supportCancel(int projectId, String userId) {
        ProjectEntity project = projectRepository.findByProjectId(projectId);

        project.setCurrentAmount(project.getCurrentAmount() - project.getPerPrice());
        projectService.updateProject(project);

        supportRepository.deleteByProject_ProjectIdAndUser_UserId(projectId, userId);
    }


}
