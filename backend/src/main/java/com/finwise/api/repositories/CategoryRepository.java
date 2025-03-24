package com.finwise.api.repositories;

import com.finwise.api.models.Category;
import com.finwise.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrDefaultCategoryIsTrue(User user);
    List<Category> findByUserIsNullAndDefaultCategoryIsTrue();
    List<Category> findByParentCategoryIsNull();
}