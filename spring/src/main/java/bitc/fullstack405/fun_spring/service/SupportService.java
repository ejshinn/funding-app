package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.SupportEntity;

import java.util.List;

public interface SupportService {

    int getSupportUserCount(int projectId);

    List<SupportEntity> getSupportingListByProject(String userId);

    void getSupportDelete(int projectId, String userId);

    void createSupport(SupportEntity support);
}
