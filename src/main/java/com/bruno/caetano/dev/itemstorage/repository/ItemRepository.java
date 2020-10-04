package com.bruno.caetano.dev.itemstorage.repository;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

}
