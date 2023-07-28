package com.example.attestation_security.controller;

import com.example.attestation_security.domain.JwtAuthentication;
import com.example.attestation_security.domain.Role;
import com.example.attestation_security.models.BaseModel;
import com.example.attestation_security.models.Category;
import com.example.attestation_security.models.answers.AnswerModel;
import com.example.attestation_security.service.AuthServiceDB;
import com.example.attestation_security.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor // генерирует конструктор с 1 параметром для каждого поля
public class CategoryController {
    private final CategoryService categoryService;
    private final AuthServiceDB authServiceDB;

    /** Создание категории
     *
     * @param category
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - создание категории доступно только администратору
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Category category){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        return authInfo.getAuthorities().contains(Role.ADMIN) ?
                categoryService.createCategory(category) :
                AnswerModel.noAccess();
    }

    /** Просмор всех категорий
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')") // защита метода - доступно администратору и пользователю
    @GetMapping(value = "")
    public ResponseEntity<?> showAllCategory(){
        return categoryService.showAllCategories();
    }

    /** Обновление названия категории
     *
     * @param id категории
     * @param category
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно только администратору
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(value = "id") Long id,
                                            @RequestBody Category category){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            if(BaseModel.isBodyCategoryCorrect(category)){
                return categoryService.updateCategory(id, category); // пытаем обновить категорию
            }
            return AnswerModel.badRequestBody();
        }
        return  AnswerModel.noAccess();
    }

    /** Удаление категории при условии, что в ней нет товаров
     *
     * @param id категории для удаления
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно только администратору
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        return authInfo.getAuthorities().contains(Role.ADMIN) ?
                categoryService.deleteCategory(id):
                AnswerModel.noAccess();
    }

}
