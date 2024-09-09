package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

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
    public void createFavorite(FavoriteEntity favorite) {

        favoriteRepository.save(favorite);
    }

    // 좋아요 삭제
    @Override
    public void deleteFavorite(int projectId, String userId) {

        favoriteRepository.deleteByProject_ProjectIdAndUser_UserId(projectId, userId);
    }
}
