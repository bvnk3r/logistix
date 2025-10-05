package org.logistix.cache.repository;

import org.logistix.cache.model.ClientInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientInfoRepository extends CrudRepository<ClientInfo, UUID> {
}
