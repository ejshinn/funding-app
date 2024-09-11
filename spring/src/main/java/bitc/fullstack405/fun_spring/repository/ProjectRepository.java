package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {


    //find
    //save
    //delete

    ProjectEntity findByProjectId(int projectId);

    List<ProjectEntity> findAllByTitleStartingWith(String key);


    List<ProjectEntity> findTop8ByOrderByStartDate();
    //
//    List<ProjectEntity> findTop13OrderBy();

//    List<ProjectEntity> findTop13();


    List<ProjectEntity> findAllByCategory_CategoryId(int categoryId);

    List<ProjectEntity> findTop6By();


    @Query("select p from ProjectEntity as p order by p.startDate")
    List<ProjectEntity> querySelectAllOrderByStartDate();



    // 마감 임박순으로 size 만큼
    @Query(value = "select p from ProjectEntity as p order by (p.endDate - now())")
    List<ProjectEntity> findTop9By(Pageable pageable);

    @Query("select p from ProjectEntity as p where p.title between :e1 and :e2")
    List<ProjectEntity> querySearchTitle(String e1, String e2);
}
