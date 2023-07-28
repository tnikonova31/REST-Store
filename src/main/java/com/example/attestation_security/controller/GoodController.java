package com.example.attestation_security.controller;

import com.example.attestation_security.domain.JwtAuthentication;
import com.example.attestation_security.domain.Role;
import com.example.attestation_security.models.BaseModel;
import com.example.attestation_security.models.Good;
import com.example.attestation_security.models.answers.AnswerModel;
import com.example.attestation_security.service.AuthServiceDB;
import com.example.attestation_security.service.interfaces.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/goods")
@RequiredArgsConstructor // генерирует конструктор с 1 параметром для каждого поля
public class GoodController {
    private final GoodService goodService;
    private final AuthServiceDB authServiceDB;

    /** Просмотр всех товаров - доступен всем пользователям
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')") // защита метода - доступно администратору и пользователю
    @GetMapping("")
    public ResponseEntity<?> showAllGoods(){
        return goodService.showAllGoods();
    }

    /** Просмотр товаров одной категории - доступен всем пользователям
     *
     * @param categoryId ID категории
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')") // защита метода - доступно администратору и пользователю
    @GetMapping("/{id}")
    public ResponseEntity<?> showGoodsByCategory(@PathVariable(value = "id") long categoryId){
        return goodService.showGoodsByCategory(categoryId);
    }

    /** Просмотр товаров с сортировкой по категориям
     * - доступен всем пользователям
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')") // защита метода - доступно администратору и пользователю
    @GetMapping("/sortByCategories")
    public ResponseEntity<?> sortByCategories(){
        return goodService.showAllGoodsByAllCategories();
    }

    /** Создание товара - доступно только для администратора
     *
     * @param categoryId - категория, в которую добавляется товар
     * @param good данные товара для добавления
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @PostMapping("/create/{categoryId}")
    public ResponseEntity<?> createGood(@PathVariable(value = "categoryId") long categoryId,
                                        @RequestBody Good good){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        return  authInfo.getAuthorities().contains(Role.ADMIN) ?
                goodService.createGood(categoryId, good):
                AnswerModel.noAccess();
    }

    /** Обновление товара - доступно только для администратора
     *
     * @param goodId товара для обновления
     * @param property свойство товара, которое обновляем
     * @param good
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @PutMapping("/update/{goodId}/{property}")
    public ResponseEntity<?> updateGood(@PathVariable(value = "goodId") long goodId,
                                        @PathVariable(value = "property") String property,
                                        @RequestBody @NotNull Good good){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            if(BaseModel.isRequestBodyGoodCorrect(good)) {
                return goodService.updateGood(goodId, property, good); // пытаем обновить товар
            }
            return AnswerModel.badRequestBody();
        }
        return  AnswerModel.noAccess();
    }


    /** Обновление категории у товара
     * - доступно только для администратора
     * @param categoryId
     * @param goodId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @PutMapping("/update/{goodId}&{categoryId}")
    public ResponseEntity<?> updateGoodCategory(@PathVariable(value = "categoryId") long categoryId,
                                                @PathVariable(value = "goodId") long goodId){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            return goodService.updateGoodCategory(categoryId, goodId); // пытаем обновить категорию товар
        }
        return  AnswerModel.noAccess();
    }

    /** Отгрузка товара - доступно только администратору
     *
     * @param good - данные для отгрузки (ID товара и количество)
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @PutMapping("/shipment")
    public ResponseEntity<?> shipmentGood(@RequestBody Good good){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            if(BaseModel.ifRequestBodyShipmentSupplyCorrect(good)){
                return goodService.shipmentGood(good); // отгрузка товара
            }
            AnswerModel.badRequestBody();
        }
        return  AnswerModel.noAccess();
    }

    /** Приход товара
     *
     * @param good - данные для прихода (ID товара и количество)
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @PutMapping("/supply")
    public ResponseEntity<?> supplyGood(@RequestBody Good good){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            if(BaseModel.ifRequestBodyShipmentSupplyCorrect(good)){
                return goodService.supplyGood(good); // отгрузка товара
            }
            AnswerModel.badRequestBody();
        }
        return  AnswerModel.noAccess();
    }

    /** Удаление товара - доступно только администратору
     *
     * @param goodId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // защита метода - доступно администратору
    @DeleteMapping("/delete/{goodId}")
    public ResponseEntity<?> deleteGood(@PathVariable(value = "goodId") Long goodId){
        final JwtAuthentication authInfo = authServiceDB.getAuthInfo(); // получаем информацию о пользователе
        if(authInfo.getAuthorities().contains(Role.ADMIN)){
            return  goodService.deleteGood(goodId);
        }
        return  AnswerModel.noAccess();
    }

}
