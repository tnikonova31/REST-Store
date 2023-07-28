package com.example.attestation_security.service.interfaces;

import com.example.attestation_security.models.Good;
import org.springframework.http.ResponseEntity;

public interface GoodService {
    /** Создание нового товара
     *
     * @param id категории, в которую добавляем товар
     * @param good товар для создания
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> createGood(long id, Good good);

    /** Обновление данных о товаре
     *
     * @param id товара
     * @param property свойство товара для обновления
     * @param good новые данные
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> updateGood(long id, String property, Good good);

    /** Обновление категории товара
     *
     * @param goodId ID товара для обновления
     * @param categoryId ID категории для обновления
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> updateGoodCategory(long categoryId, long goodId);

    /** Просмотр всех товаров
     *
     * @return список товаров или код ответа HTTP, сообщение об ошибке
     */
    ResponseEntity<?> showAllGoods();

    /** Просмотр товаров определённой категории
     *
     * @param id категории
     * @return список товаров или код ответа HTTP, сообщение об ошибке
     */
    ResponseEntity<?> showGoodsByCategory(long id);

    /** Просмотр товаров с сортировкой категорий
     *  по возрастанию
     * @return список товаров или код ответа HTTP, сообщение об ошибке
     */
    ResponseEntity<?> showAllGoodsByAllCategories();

    /** Отгрузка товара
     *
     * @param good данные для отгрузки товара
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> shipmentGood(Good good);

    /** Приход товара
     *
     * @param good
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> supplyGood(Good good);

    /** Удаление товара
     *
     * @param id товара для удаления
     * @return код HTTP ответа и сообщение
     */
    ResponseEntity<?> deleteGood(Long id);

}
