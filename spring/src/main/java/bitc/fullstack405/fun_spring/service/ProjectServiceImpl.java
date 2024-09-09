package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.repository.CategoryRepository;
import bitc.fullstack405.fun_spring.repository.ProjectRepository;
import bitc.fullstack405.fun_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // 상세보기
    @Override
    public ProjectDto getProjectDetail(int projectId) {
        ProjectEntity entity =  projectRepository.findByProjectId(projectId);

        return ProjectDto.of(entity);
    }

    // 리스트
    @Override
    public List<ProjectDto> getProjectList() {
        return projectRepository.findAll().stream().map(ProjectDto::of).collect(Collectors.toCollection(LinkedHashSet::new)).stream().toList();
    }

    // 인기순 리스트
    @Override
    public List<ProjectDto> getProjectListRanking() {

        return projectRepository.findTop8ByOrderByStartDate().stream().map(ProjectDto::of).collect(Collectors.toCollection(LinkedHashSet::new)).stream().toList();
    }

    // 검색    (  임시로 만듦  )
    @Override
    public List<ProjectDto> getProjectListSearch(String key) {

        return projectRepository.findAllByTitleStartingWith(key).stream().map(ProjectDto::of).collect(Collectors.toCollection(LinkedList::new));
    }

    // 작성
    @Override
    public void getWriteProject(ProjectDto project) {

        projectRepository.save(
                project.toEntity(
                        userRepository.findByUserId(project.user().userId()),
                        categoryRepository.findByCategoryId(project.category().categoryId())
                )
        );
        //projectRepository.save(project);
    }


//    @Transactional
    @Override
    public void updateProject(ProjectDto project) {
//        ProjectEntity p = projectRepository.findByProjectId(project.getProjectId());
//        p.setCurrentAmount(project.getCurrentAmount());
//        projectRepository.save(project);
    }


    @Override
    public List<ProjectDto> getProjectListByDeadLine() {
//        projectRepository.findAll

        return projectRepository.findTop9By().stream().map(ProjectDto::of).collect(Collectors.toCollection(LinkedList::new));
//        return projectRepository.findAllOrderByDeadLine(size);
    }

    @Override
    public List<ProjectDto> getHomeScrollProject(int pageNum) {
        Page<ProjectEntity> page = projectRepository.findAll(
                PageRequest.of(pageNum, 6, Sort.Direction.ASC, "startDate")
        );

        return page.getContent().stream().map(ProjectDto::of).collect(Collectors.toCollection(LinkedList::new));
    }
}
