package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.util.List;

public interface FavoriteService {

    int getFavoriteUserCount(int projectId);

    void createFavorite(FavoriteEntity favorite);

    void deleteFavorite(int projectId, String userId);

    List<FavoriteEntity> getFavoriteListByUserId(String userId);

}
