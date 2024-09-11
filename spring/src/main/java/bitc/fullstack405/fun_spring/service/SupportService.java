package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.SupportEntity;

import java.util.List;

public interface SupportService {

    int getSupportUserCount(int projectId);

    List<ProjectDto> getSupportingListByUserId(String userId);

    void supportCancel(int projectId, String userId);

    void createSupport(int projectId, String userId);
}
