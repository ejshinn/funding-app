package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportRepository supportRepository;

    @Override
    public int getSupportUserCount(int projectId) {

        return supportRepository.countByProject_ProjectId(projectId);
    }

    @Override
    public List<SupportEntity> getSupportingListByProject(String userId) {

        return supportRepository.findAllByUser_UserId(userId);
    }

    @Override
    public void createSupport(SupportEntity support) {

        supportRepository.save(support);
    }

    @Override
    public void getSupportDelete(int projectId, String userId) {

        supportRepository.deleteByProject_ProjectIdAndUser_UserId(projectId, userId);
    }
}
