package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.FavoriteCD_Dto;
import bitc.fullstack405.fun_spring.dto.FavoriteDto;
import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.repository.FavoriteRepository;
import bitc.fullstack405.fun_spring.repository.ProjectRepository;
import bitc.fullstack405.fun_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    // 프로젝트에 좋아요 누른 유저 수
    @Override
    public int getFavoriteUserCount(int projectId) {

        return favoriteRepository.countByProject_ProjectId(projectId);
    }

    // 자신이 좋아요 누른 프로젝트 리스트
    @Override
    public List<FavoriteEntity> getFavoriteListByUserId(String userId) {

        return favoriteRepository.findAllByUser_UserId(userId);
    }

    // 좋아요 생성
    @Override
    public void createFavorite(FavoriteCD_Dto favorite) {
        UserEntity user = userRepository.findByUserId(favorite.userId());
        ProjectEntity project = projectRepository.findByProjectId(favorite.projectId());

        favoriteRepository.save(favorite.toEntity(user, project));
    }

    // 좋아요 삭제
    @Override
    public void deleteFavorite(FavoriteCD_Dto FavoriteCD_Dto) {

        favoriteRepository.deleteByProject_ProjectIdAndUser_UserId(FavoriteCD_Dto.projectId(), FavoriteCD_Dto.userId());
    }
}
