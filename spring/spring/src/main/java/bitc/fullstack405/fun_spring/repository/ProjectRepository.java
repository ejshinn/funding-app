package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
}
