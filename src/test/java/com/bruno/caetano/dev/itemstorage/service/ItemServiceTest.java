package com.bruno.caetano.dev.itemstorage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.repository.ItemRepository;
import com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageTestConstant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest extends ItemStorageTestConstant {

	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Test
	void findAll() {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("market", ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());
		when(itemRepository.findAll(Example.of(Item.builder().build(), exampleMatcher), PageRequest.of(0, 5))).thenReturn(
				new PageImpl(
						Arrays.asList(
								Item.builder()
										.id(ITEM_ONE_ID)
										.name(ITEM_ONE_NAME)
										.description(ITEM_ONE_DESCRIPTION)
										.market(ITEM_ONE_MARKET)
										.price(BigDecimal.TEN)
										.stock(BigInteger.TEN)
										.status(ItemStatus.ACTIVE)
										.build(),
								Item.builder()
										.id(ITEM_TWO_ID)
										.name(ITEM_TWO_NAME)
										.description(ITEM_TWO_DESCRIPTION)
										.market(ITEM_TWO_MARKET)
										.price(BigDecimal.TEN)
										.stock(BigInteger.TEN)
										.status(ItemStatus.ACTIVE)
										.build(),
								Item.builder()
										.id(ITEM_THREE_ID)
										.name(ITEM_THREE_NAME)
										.description(ITEM_THREE_DESCRIPTION)
										.market(ITEM_THREE_MARKET)
										.price(BigDecimal.TEN)
										.stock(BigInteger.TEN)
										.status(ItemStatus.ACTIVE)
										.build(),
								Item.builder()
										.id(ITEM_FOUR_ID)
										.name(ITEM_FOUR_NAME)
										.description(ITEM_FOUR_DESCRIPTION)
										.market(ITEM_FOUR_MARKET)
										.price(BigDecimal.TEN)
										.stock(BigInteger.TEN)
										.status(ItemStatus.ACTIVE)
										.build(),
								Item.builder()
										.id(ITEM_FIVE_ID)
										.name(ITEM_FIVE_NAME)
										.description(ITEM_FIVE_DESCRIPTION)
										.market(ITEM_FIVE_MARKET)
										.price(BigDecimal.TEN)
										.stock(BigInteger.TEN)
										.status(ItemStatus.ACTIVE)
										.build()
						), PageRequest.of(0, 5), 20l));
		Page<Item> items = itemService.findAll(Item.builder().build(), PageRequest.of(0, 5));
		assertNotNull(items);
		assertEquals(20l, items.getTotalElements());
		assertEquals(5, items.getNumberOfElements());
		assertEquals(ITEM_ONE_ID, items.getContent().get(0).getId());
		assertEquals(ITEM_ONE_NAME, items.getContent().get(0).getName());
		assertEquals(ITEM_ONE_DESCRIPTION, items.getContent().get(0).getDescription());
		assertEquals(ITEM_ONE_MARKET, items.getContent().get(0).getMarket());
		assertEquals(BigDecimal.TEN, items.getContent().get(0).getPrice());
		assertEquals(BigInteger.TEN, items.getContent().get(0).getStock());
		assertEquals(ItemStatus.ACTIVE, items.getContent().get(0).getStatus());
		assertEquals(ITEM_TWO_ID, items.getContent().get(1).getId());
		assertEquals(ITEM_TWO_NAME, items.getContent().get(1).getName());
		assertEquals(ITEM_TWO_DESCRIPTION, items.getContent().get(1).getDescription());
		assertEquals(ITEM_TWO_MARKET, items.getContent().get(1).getMarket());
		assertEquals(BigDecimal.TEN, items.getContent().get(1).getPrice());
		assertEquals(BigInteger.TEN, items.getContent().get(1).getStock());
		assertEquals(ItemStatus.ACTIVE, items.getContent().get(1).getStatus());
		assertEquals(ITEM_THREE_ID, items.getContent().get(2).getId());
		assertEquals(ITEM_THREE_NAME, items.getContent().get(2).getName());
		assertEquals(ITEM_THREE_DESCRIPTION, items.getContent().get(2).getDescription());
		assertEquals(ITEM_THREE_MARKET, items.getContent().get(2).getMarket());
		assertEquals(BigDecimal.TEN, items.getContent().get(2).getPrice());
		assertEquals(BigInteger.TEN, items.getContent().get(2).getStock());
		assertEquals(ItemStatus.ACTIVE, items.getContent().get(2).getStatus());
		assertEquals(ITEM_FOUR_ID, items.getContent().get(3).getId());
		assertEquals(ITEM_FOUR_NAME, items.getContent().get(3).getName());
		assertEquals(ITEM_FOUR_DESCRIPTION, items.getContent().get(3).getDescription());
		assertEquals(ITEM_FOUR_MARKET, items.getContent().get(3).getMarket());
		assertEquals(BigDecimal.TEN, items.getContent().get(3).getPrice());
		assertEquals(BigInteger.TEN, items.getContent().get(3).getStock());
		assertEquals(ItemStatus.ACTIVE, items.getContent().get(3).getStatus());
		assertEquals(ITEM_FIVE_ID, items.getContent().get(4).getId());
		assertEquals(ITEM_FIVE_NAME, items.getContent().get(4).getName());
		assertEquals(ITEM_FIVE_DESCRIPTION, items.getContent().get(4).getDescription());
		assertEquals(ITEM_FIVE_MARKET, items.getContent().get(4).getMarket());
		assertEquals(BigDecimal.TEN, items.getContent().get(4).getPrice());
		assertEquals(BigInteger.TEN, items.getContent().get(4).getStock());
		assertEquals(ItemStatus.ACTIVE, items.getContent().get(4).getStatus());
	}

	@Test
	void findBydId() {
		Item persistedItem = Item.builder()
				.id(ITEM_ONE_ID)
				.name(ITEM_ONE_NAME)
				.description(ITEM_ONE_DESCRIPTION)
				.market(ITEM_ONE_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();

		when(itemRepository.findById(ITEM_ONE_ID)).thenReturn(Optional.of(persistedItem));
		when(itemRepository.findById(ITEM_TWO_ID)).thenReturn(Optional.empty());
		persistedItem = itemService.findBydId(ITEM_ONE_ID);
		assertNotNull(persistedItem);
		assertEquals(ITEM_ONE_ID, persistedItem.getId());
		assertEquals(ITEM_ONE_NAME, persistedItem.getName());
		assertEquals(ITEM_ONE_DESCRIPTION, persistedItem.getDescription());
		assertEquals(ITEM_ONE_MARKET, persistedItem.getMarket());
		assertEquals(BigDecimal.TEN, persistedItem.getPrice());
		assertEquals(BigInteger.TEN, persistedItem.getStock());
		assertEquals(ItemStatus.ACTIVE, persistedItem.getStatus());
		assertThrows(EntityNotFoundException.class, () -> itemService.findBydId(ITEM_TWO_ID));
	}

	@Test
	void save() {
		Item item = Item.builder()
				.name(ITEM_ONE_NAME)
				.description(ITEM_ONE_DESCRIPTION)
				.market(ITEM_ONE_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();

		Item persistedItem = Item.builder()
				.id(ITEM_ONE_ID)
				.name(ITEM_ONE_NAME)
				.description(ITEM_ONE_DESCRIPTION)
				.market(ITEM_ONE_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();

		when(itemRepository.save(item)).thenReturn(persistedItem);
		persistedItem = itemRepository.save(item);
		assertNotNull(persistedItem);
		assertEquals(ITEM_ONE_ID, persistedItem.getId());
		assertEquals(ITEM_ONE_NAME, persistedItem.getName());
		assertEquals(ITEM_ONE_DESCRIPTION, persistedItem.getDescription());
		assertEquals(ITEM_ONE_MARKET, persistedItem.getMarket());
		assertEquals(BigDecimal.TEN, persistedItem.getPrice());
		assertEquals(BigInteger.TEN, persistedItem.getStock());
		assertEquals(ItemStatus.ACTIVE, persistedItem.getStatus());
	}

	@Test
	void update() {
		Item item = Item.builder()
				.id(ITEM_TWO_ID)
				.stock(BigInteger.ONE)
				.status(ItemStatus.INACTIVE)
				.build();

		Item persistedItem = Item.builder()
				.id(ITEM_TWO_ID)
				.name(ITEM_TWO_NAME)
				.description(ITEM_TWO_DESCRIPTION)
				.market(ITEM_TWO_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();

		Item updatedItem = Item.builder()
				.id(ITEM_TWO_ID)
				.name(ITEM_TWO_NAME)
				.description(ITEM_TWO_DESCRIPTION)
				.market(ITEM_TWO_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.ONE)
				.status(ItemStatus.INACTIVE)
				.build();

		when(itemRepository.findById(ITEM_TWO_ID)).thenReturn(Optional.of(persistedItem));
		when(itemRepository.save(updatedItem)).thenReturn(updatedItem);
		updatedItem = itemService.update(item);
		assertNotNull(updatedItem);
		assertEquals(ITEM_TWO_ID, updatedItem.getId());
		assertEquals(ITEM_TWO_NAME, updatedItem.getName());
		assertEquals(ITEM_TWO_DESCRIPTION, updatedItem.getDescription());
		assertEquals(ITEM_TWO_MARKET, updatedItem.getMarket());
		assertEquals(BigDecimal.TEN, updatedItem.getPrice());
		assertEquals(BigInteger.ONE, updatedItem.getStock());
		assertEquals(ItemStatus.INACTIVE, updatedItem.getStatus());
	}

	@Test
	void deleteById() {
		itemService.deleteById(ITEM_THREE_ID);
		verify(itemRepository, times(1)).deleteById(ITEM_THREE_ID);
	}

	@Test
	void restock() {
		Item persistedItem = Item.builder()
				.id(ITEM_FOUR_ID)
				.name(ITEM_FOUR_NAME)
				.description(ITEM_FOUR_DESCRIPTION)
				.market(ITEM_FOUR_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();
		Item restockedItem = Item.builder()
				.id(ITEM_FOUR_ID)
				.name(ITEM_FOUR_NAME)
				.description(ITEM_FOUR_DESCRIPTION)
				.market(ITEM_FOUR_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.valueOf(20l))
				.status(ItemStatus.ACTIVE)
				.build();

		when(itemRepository.findById(ITEM_FOUR_ID)).thenReturn(Optional.of(persistedItem));
		itemService.restock(ITEM_FOUR_ID, BigInteger.TEN);
		verify(itemRepository, times(1)).save(restockedItem);
	}

	@Test
	void dispatch() {
		Item persistedItem = Item.builder()
				.id(ITEM_FIVE_ID)
				.name(ITEM_FIVE_NAME)
				.description(ITEM_FIVE_DESCRIPTION)
				.market(ITEM_FIVE_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.TEN)
				.status(ItemStatus.ACTIVE)
				.build();
		Item dispatchedItem = Item.builder()
				.id(ITEM_FIVE_ID)
				.name(ITEM_FIVE_NAME)
				.description(ITEM_FIVE_DESCRIPTION)
				.market(ITEM_FIVE_MARKET)
				.price(BigDecimal.TEN)
				.stock(BigInteger.ZERO)
				.status(ItemStatus.ACTIVE)
				.build();

		when(itemRepository.findById(ITEM_FIVE_ID)).thenReturn(Optional.of(persistedItem));
		itemService.dispatch(ITEM_FIVE_ID, BigInteger.TEN);
		verify(itemRepository, times(1)).save(dispatchedItem);
	}
}