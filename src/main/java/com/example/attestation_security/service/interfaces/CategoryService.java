package com.example.attestation_security.service.interfaces;

import com.example.attestation_security.models.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    /** Создание категории товаров
     *
     * @param category категория для создания
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> createCategory(Category category);

    ResponseEntity<?> showAllCategories();

     /** Изменение названия категории товаров
     *
     * @param id категории
     * @title новое название категории
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> updateCategory(Long id, Category category);

    /** Удаление категории при условии, что в ней нет товаров
     *
     * @param id категории
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> deleteCategory(Long id);
}
