package org.logistix.cache.repository;

import org.logistix.cache.model.ProductInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductInfoRepository extends CrudRepository<ProductInfo, UUID> {

    Optional<ProductInfo> findByCode(String code);
}
