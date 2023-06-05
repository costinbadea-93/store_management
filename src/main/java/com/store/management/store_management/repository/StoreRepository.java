package com.store.management.store_management.repository;

import com.store.management.store_management.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
