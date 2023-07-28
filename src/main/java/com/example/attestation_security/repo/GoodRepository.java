package com.example.attestation_security.repo;

import com.example.attestation_security.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {
    /** Поиск товара по артиклу
     *
     * @param vendorCode артикл
     * @return
     */
    Good findGoodByVendorCode(String vendorCode);

    /** Поиск товаров по ID категории
     *
     * @param id категории
     * @return
     */
    List<Good> findGoodsByCategory_CategoryId(long id);
}
