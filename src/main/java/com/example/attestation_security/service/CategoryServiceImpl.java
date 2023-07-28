package com.example.attestation_security.service;

import com.example.attestation_security.models.Category;
import com.example.attestation_security.models.answers.AnswerModel;
import com.example.attestation_security.repo.CategoryRepository;
import com.example.attestation_security.repo.GoodRepository;
import com.example.attestation_security.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String ENTITY="Category";
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Override
    public ResponseEntity<?> createCategory(Category category) {
        Category categoryFind = categoryRepository.findCategoryByTitle(category.getTitle());
        if(categoryFind == null){ // если категории с таким названием нет
            Category categoryEntity = new Category(category.getCategoryId(), category.getTitle());
            categoryRepository.save(categoryEntity); // создаём
            return AnswerModel.createEntity(ENTITY + " '" + category.getTitle() + "'"); // отправляем ответ
        }
        return AnswerModel.noCreateEntity(ENTITY); // не создали категорию
    }

    @Override
    public ResponseEntity<?> showAllCategories() {
        Iterable<Category> all = categoryRepository.findAll();
        return  all.iterator().hasNext() // если итератор не пустой
                ? new ResponseEntity<>(all, HttpStatus.OK)
                : AnswerModel.noFindEntity(ENTITY);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, Category category) {
        Category categoryFindId = categoryRepository.findById(id).orElse(null);
        if(categoryFindId != null){ // нашли категорию по ID для обновления
            Category categoryFindTitle = categoryRepository.findCategoryByTitle(category.getTitle());
            if(categoryFindTitle == null){ // не нашли дублирующегося названия категории
                categoryFindId.setTitle(category.getTitle()); // устанавливаем новое название категории
                categoryRepository.save(categoryFindId); // сохраняем
                return AnswerModel.updateEntity(ENTITY);
            } else{
                return AnswerModel.noUpdateEntity(ENTITY);
            }
        }
        return AnswerModel.noFindEntity(ENTITY);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Category categoryFind = categoryRepository.findById(id).orElse(null);
        if(categoryFind != null){
            // проверяем, есть ли товары в категории
            if (goodRepository.findGoodsByCategory_CategoryId(categoryFind.getCategoryId()).iterator().hasNext()){
                return AnswerModel.canNotDeleteEntity(ENTITY);
            }
            categoryRepository.deleteById(id); // удаляем, если в категории нет товаров
            return AnswerModel.deleteEntity(ENTITY);
        }
        return AnswerModel.noDeleteEntity(ENTITY);
    }
}
