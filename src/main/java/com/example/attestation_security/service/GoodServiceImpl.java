package com.example.attestation_security.service;

import com.example.attestation_security.models.BaseModel;
import com.example.attestation_security.models.Category;
import com.example.attestation_security.models.Good;
import com.example.attestation_security.models.answers.AnswerModel;
import com.example.attestation_security.repo.CategoryRepository;
import com.example.attestation_security.repo.GoodRepository;
import com.example.attestation_security.service.interfaces.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    private static final String ENTITY_GOOD="Good";
    private static final String ENTITY_CATEGORY="Category";
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GoodRepository goodRepository;

    @Override
    public ResponseEntity<?> showAllGoods() {
        List<Good> all = goodRepository.findAll();
        return !all.isEmpty() // если лист не пустой
                ? new ResponseEntity<>(all, HttpStatus.OK)
                : AnswerModel.noFindEntity(ENTITY_GOOD);
    }



    @Override
    public ResponseEntity<?> createGood(long id, Good good) {
        Category categoryFind = categoryRepository.findById(id).orElse(null);
        if(categoryFind != null){ // если нашли категорию, в которую хотим добавить товар
            if(BaseModel.isRequestBodyGoodCorrect(good)){
                // проверяем, есть ли товар с таким артикулом
                if(goodRepository.findGoodByVendorCode(good.getVendorCode()) == null){
                    good.setCategory(categoryFind); // добавляем категорию товару
                    goodRepository.save(good); // сохраняем товар
                    return  AnswerModel.createEntity(ENTITY_GOOD + " '" + good.getTitle() + "'");
                }
                return AnswerModel.noCreateEntity(ENTITY_GOOD);
            }
            return  AnswerModel.badRequestBody();
        }
        return AnswerModel.noFindEntity(ENTITY_CATEGORY);
    }

    @Override
    public ResponseEntity<?> updateGood(long id, String property, Good good) {
        Good goodFind = goodRepository.findById(id).orElse(null);
        if(goodFind != null){ // нашли товар для обновления по ID
            switch (property){
                case "title":
                    if(good.getTitle() != null && !good.getTitle().isEmpty()){
                        goodFind.setTitle(good.getTitle()); //  обновили название
                        break;
                    }
                    return AnswerModel.badRequestBody();
                case "count":
                    if(good.getCount() > 0){
                        goodFind.setCount(good.getCount()); // обновили количество
                        break;
                    }
                    return AnswerModel.badRequestBody();
                case "price":
                    if(good.getPrice() > 0){
                        goodFind.setPrice(good.getPrice()); // стоимость
                        break;
                    }
                    return AnswerModel.badRequestBody();
                case "info":
                    if(good.getInfo() != null && !good.getInfo().isEmpty()){
                        goodFind.setInfo(good.getInfo()); //  информацию
                        break;
                    }
                    return AnswerModel.badRequestBody();
                case "vendorCode":
                    if (good.getVendorCode()!=null && !good.getVendorCode().isEmpty()){
                        if(goodRepository.findGoodByVendorCode(good.getVendorCode()) == null){
                            goodFind.setVendorCode(good.getVendorCode()); // артикула не нашли такого же
                            break;
                        }
                        return AnswerModel.noUpdateEntity(ENTITY_GOOD);
                    }
                    return AnswerModel.badRequestBody();
                default:
                    return AnswerModel.badRequestBody();
            }
            goodRepository.save(goodFind);
            return AnswerModel.updateEntity(ENTITY_GOOD);
        }
        return AnswerModel.noFindEntity(ENTITY_GOOD);
    }

    @Override
    public ResponseEntity<?> updateGoodCategory(long categoryId, long goodId) {
        Category categoryFind = categoryRepository.findById(categoryId).orElse(null);
        if(categoryFind != null){ // если нашли категорию
            Good goodFind = goodRepository.findById(goodId).orElse(null);
            if (goodFind != null){ // если нашли товар
                goodFind.setCategory(categoryFind); // обновили категорию
                goodRepository.save(goodFind);
                return AnswerModel.updateEntity(ENTITY_GOOD);
            }
            return AnswerModel.noFindEntity(ENTITY_GOOD);
        }
        return AnswerModel.noFindEntity(ENTITY_CATEGORY);
    }


    @Override
    public ResponseEntity<?> showGoodsByCategory(long id) {
        List <Good> goods = goodRepository.findGoodsByCategory_CategoryId(id);
        return !goods.isEmpty()
                ? new ResponseEntity<>(goods, HttpStatus.OK)
                : AnswerModel.noFindEntity(ENTITY_GOOD);
    }

    @Override
    public ResponseEntity<?> showAllGoodsByAllCategories() {
        List<Good> goods = goodRepository.findAll(Sort.by("category"));
        return !goods.isEmpty()
                ? new ResponseEntity<>(goods, HttpStatus.OK)
                : AnswerModel.noFindEntity(ENTITY_GOOD);
    }

    @Override
    public ResponseEntity<?> shipmentGood(Good good) {
        Good goodFind = goodRepository.findById(good.getGoodId()).orElse(null);
        if(goodFind != null){
            if (goodFind.getCount()>good.getCount()){
                goodFind.setCount(goodFind.getCount()- good.getCount());
                goodRepository.save(goodFind);
                return AnswerModel.updateEntity("Товар отгружен. " + ENTITY_GOOD);
            } else if (goodFind.getCount() == good.getCount()) {
                goodFind.setCategory(null); // удаляем категорию у товара
                goodRepository.deleteById(goodFind.getGoodId()); // удаляем товар без связи
                return AnswerModel.deleteEntity("Товар отгружен полностью. " + ENTITY_GOOD);
            } else {
                return AnswerModel.noUpdateEntity("Недостаточно товара для отгрузки. " + ENTITY_GOOD);
            }
        }
        return AnswerModel.noFindEntity(ENTITY_GOOD);
    }

    @Override
    public ResponseEntity<?> supplyGood(Good good) {
        Good goodFind = goodRepository.findById(good.getGoodId()).orElse(null);
        if(goodFind != null){
            goodFind.setCount(goodFind.getCount() + good.getCount());
            goodRepository.save(goodFind);
            return AnswerModel.updateEntity("Товар поставлен. " + ENTITY_GOOD);
        }
        return AnswerModel.noFindEntity(ENTITY_GOOD);
    }

    @Override
    public ResponseEntity<?> deleteGood(Long id) {
        Good goodFind = goodRepository.findById(id).orElse(null);
        if(goodFind != null){
            goodFind.setCategory(null); // удаляем категорию у товара
            goodRepository.deleteById(id); // удаляем товар без связи
            return AnswerModel.deleteEntity(ENTITY_GOOD);
        }
        return AnswerModel.noDeleteEntity(ENTITY_GOOD);
    }
}
