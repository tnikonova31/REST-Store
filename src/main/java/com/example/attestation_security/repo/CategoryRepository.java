package com.example.attestation_security.repo;

import com.example.attestation_security.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    /** Поиск категории по названию
     *
     * @param title название категории
     * @return категория
     */
    Category findCategoryByTitle (String title);
}
