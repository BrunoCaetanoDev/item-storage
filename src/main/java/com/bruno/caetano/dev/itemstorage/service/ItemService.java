package com.bruno.caetano.dev.itemstorage.service;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ENTITY_NOT_FOUND_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ITEM;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.repository.ItemRepository;
import java.math.BigInteger;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService implements ItemServiceContract {


	private final ItemRepository itemRepository;

	@Override
	public Page<Item> findAll(Item item, Pageable pageRequest) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("market", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());
		return itemRepository.findAll(Example.of(item, exampleMatcher), pageRequest);
	}

	@Override
	public Item findBydId(String id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, ITEM, id)));
	}

	@Override
	public Item save(Item item) {
		item.setStatus(ItemStatus.ACTIVE);
		return itemRepository.save(item);
	}

	@Override
	public Item update(Item item) {
		Item persistedItem = findBydId(item.getId());
		if (Optional.ofNullable(item.getName()).isPresent()) {
			persistedItem.setName(item.getName());
		}
		if (Optional.ofNullable(item.getDescription()).isPresent()) {
			persistedItem.setDescription(item.getDescription());
		}
		if (Optional.ofNullable(item.getStatus()).isPresent()) {
			persistedItem.setStatus(item.getStatus());
		}
		if (Optional.ofNullable(item.getStock()).isPresent()) {
			persistedItem.setStock(item.getStock());
		}
		if (Optional.ofNullable(item.getPrice()).isPresent()) {
			persistedItem.setPrice(item.getPrice());
		}
		return itemRepository.save(persistedItem);
	}

	@Override
	public void deleteById(String id) {
		itemRepository.deleteById(id);
	}

	@Override
	public void restock(String id, BigInteger qty) {
		Item item = findBydId(id);
		BigInteger stock = item.getStock().add(qty);
		item.setStock(stock);
		itemRepository.save(item);
	}

	@Override
	public void dispatch(String id, BigInteger qty) {
		Item item = findBydId(id);
		BigInteger stock = item.getStock().subtract(qty);
		item.setStock(stock);
		itemRepository.save(item);
	}
}
