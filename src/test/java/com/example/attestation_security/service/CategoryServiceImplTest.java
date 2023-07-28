package com.example.attestation_security.service;

import com.example.attestation_security.models.Category;
import com.example.attestation_security.models.answers.AnswerModel;
import com.example.attestation_security.repo.CategoryRepository;
import com.example.attestation_security.service.interfaces.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceImplTest {
    @Autowired
    CategoryService categoryService;
    @MockBean
    CategoryRepository categoryRepositoryMock;

    @Test
    void createCategory() {
    }

    @Test
    void showAllCategories() {
        Mockito.when(categoryRepositoryMock.findAll()).then()
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}