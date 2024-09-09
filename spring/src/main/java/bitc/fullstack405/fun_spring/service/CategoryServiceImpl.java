package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.entity.CategoryEntity;
import bitc.fullstack405.fun_spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryEntity getCategory(int categoryId) {

        return categoryRepository.findByCategoryId(categoryId);
    }
}
