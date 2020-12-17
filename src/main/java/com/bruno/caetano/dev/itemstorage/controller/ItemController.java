package com.bruno.caetano.dev.itemstorage.controller;

import static com.bruno.caetano.dev.itemstorage.utils.HttpUtils.buildHttpResponseHeaders;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.CREATE_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.CREATE_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.CREATE_ITEM_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DELETE_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DELETE_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DELETE_ITEM_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DISPATCH_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DISPATCH_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.DISPATCH_ITEM_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.FRONT_SLASH_DELIMITER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEMS_COUNT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEMS_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEMS_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.GET_ITEM_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ITEMS;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ITEM_API_DESCRIPTION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.RESTOCK_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.RESTOCK_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.RESTOCK_ITEM_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.UPDATE_ITEM_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.UPDATE_ITEM_RESULT_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.UPDATE_ITEM_SERVICE_OPERATION;
import static org.springframework.hateoas.IanaLinkRelations.FIRST;
import static org.springframework.hateoas.IanaLinkRelations.LAST;
import static org.springframework.hateoas.IanaLinkRelations.NEXT;
import static org.springframework.hateoas.IanaLinkRelations.PREVIOUS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import com.bruno.caetano.dev.itemstorage.entity.request.in.CreateItemRequest;
import com.bruno.caetano.dev.itemstorage.entity.request.in.DispatchItemRequest;
import com.bruno.caetano.dev.itemstorage.entity.request.in.RestockItemRequest;
import com.bruno.caetano.dev.itemstorage.entity.request.in.UpdateItemRequest;
import com.bruno.caetano.dev.itemstorage.entity.response.out.CreateItemResponse;
import com.bruno.caetano.dev.itemstorage.entity.response.out.GetItemResponse;
import com.bruno.caetano.dev.itemstorage.entity.response.out.UpdateItemResponse;
import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.service.ItemServiceContract;
import com.bruno.caetano.dev.itemstorage.utils.annotation.ServiceOperation;
import com.bruno.caetano.dev.itemstorage.utils.properties.ItemStorageProperties;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ITEMS)
@Tag(name = ITEMS, description = ITEM_API_DESCRIPTION)
public class ItemController {

	private final ItemServiceContract itemService;
	private final ItemStorageProperties itemStorageProperties;
	private final ModelMapper modelMapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ServiceOperation(GET_ITEMS_SERVICE_OPERATION)
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List the items",
			content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = GetItemResponse.class)))}
	)})
	public ResponseEntity<PagedModel<GetItemResponse>> getItems(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "market", required = false) String market,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "20") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		log.trace(GET_ITEMS_MSG);
		ItemStatus itemStatus = ItemStatus.fromName(status);
		Page<Item> itemPage = itemService.findAll(Item.builder().name(name).market(market).status(itemStatus).build(),
				PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy)));
		log.info(GET_ITEMS_COUNT_MSG, itemPage.getNumberOfElements(), itemPage.getTotalElements());
		return ResponseEntity.ok().body(buildPagedModel(itemPage, name, market, status, sortBy, direction));
	}

	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ServiceOperation(GET_ITEM_SERVICE_OPERATION)
	public ResponseEntity<GetItemResponse> getItem(@PathVariable("id") String id) {
		log.trace(GET_ITEM_MSG);
		Item item = itemService.findBydId(id);
		log.info(GET_ITEM_RESULT_MSG, id);
		return ResponseEntity.ok(modelMapper.map(item, GetItemResponse.class));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	@ServiceOperation(CREATE_ITEM_SERVICE_OPERATION)
	public ResponseEntity<CreateItemResponse> createItem(@RequestBody @Valid CreateItemRequest request) {
		log.trace(CREATE_ITEM_MSG);
		Item item = itemService.save(modelMapper.map(request, Item.class));
		log.info(CREATE_ITEM_RESULT_MSG, item.getId());
		return ResponseEntity.created(URI.create(
				String.join(FRONT_SLASH_DELIMITER, itemStorageProperties.getServletContextPath(), ITEMS, item.getId())))
				.body(modelMapper.map(item, CreateItemResponse.class));
	}

	@PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ServiceOperation(UPDATE_ITEM_SERVICE_OPERATION)
	public ResponseEntity<UpdateItemResponse> updateItem(@PathVariable("id") String id,
			@RequestBody @Valid UpdateItemRequest request) {
		log.trace(UPDATE_ITEM_MSG, id);
		request.setId(id);
		Item item = itemService.update(modelMapper.map(request, Item.class));
		log.info(UPDATE_ITEM_RESULT_MSG, id);
		return ResponseEntity.ok(modelMapper.map(item, UpdateItemResponse.class));
	}

	@DeleteMapping("{id}")
	@ServiceOperation(DELETE_ITEM_SERVICE_OPERATION)
	public ResponseEntity deleteItem(@PathVariable("id") String id) {
		log.trace(DELETE_ITEM_MSG, id);
		itemService.deleteById(id);
		log.info(DELETE_ITEM_RESULT_MSG, id);
		return ResponseEntity.ok(buildHttpResponseHeaders());
	}

	@PostMapping(value = "{id}/restock", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ServiceOperation(RESTOCK_ITEM_SERVICE_OPERATION)
	public ResponseEntity restockItem(@PathVariable("id") String id, @RequestBody @Valid RestockItemRequest request) {
		log.trace(RESTOCK_ITEM_MSG, id);
		itemService.restock(id, request.getQuantity());
		log.info(RESTOCK_ITEM_RESULT_MSG, id, request.getQuantity());
		return ResponseEntity.ok(buildHttpResponseHeaders());
	}

	@PostMapping(value = "{id}/dispatch", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ServiceOperation(DISPATCH_ITEM_SERVICE_OPERATION)
	public ResponseEntity dispatchItem(@PathVariable("id") String id, @RequestBody @Valid DispatchItemRequest request) {
		log.trace(DISPATCH_ITEM_MSG, id);
		itemService.dispatch(id, request.getQuantity());
		log.info(DISPATCH_ITEM_RESULT_MSG, id, request.getQuantity());
		return ResponseEntity.ok(buildHttpResponseHeaders());
	}

	private PagedModel<GetItemResponse> buildPagedModel(Page<Item> itemPage, String name, String market, String status,
			String sortBy, String direction) {
		List<GetItemResponse> getItemResponseList = itemPage.getContent().stream()
				.map(i -> modelMapper.map(i, GetItemResponse.class)).collect(Collectors.toList());
		PagedModel<GetItemResponse> getItemResponsePagedModel = PagedModel.of(getItemResponseList,
				new PagedModel.PageMetadata(itemPage.getSize(), itemPage.getNumber(), itemPage.getTotalElements(),
						itemPage.getTotalPages()));
		getItemResponsePagedModel.add(linkTo(methodOn(ItemController.class)
				.getItems(name, market, status, itemPage.getNumber(), itemPage.getSize(), sortBy, direction)).withSelfRel()
				.expand());
		if (itemPage.hasNext()) {
			getItemResponsePagedModel.add(linkTo(methodOn(ItemController.class)
					.getItems(name, market, status, itemPage.nextPageable().getPageNumber(),
							itemPage.nextPageable().getPageSize(), sortBy, direction))
					.withRel(NEXT)
					.expand());
		}
		if (itemPage.hasPrevious()) {
			getItemResponsePagedModel.add(linkTo(methodOn(ItemController.class)
					.getItems(name, market, status, itemPage.previousPageable().getPageNumber(),
							itemPage.previousPageable().getPageSize(), sortBy, direction))
					.withRel(PREVIOUS)
					.expand());
		}
		if (!itemPage.isFirst()) {
			getItemResponsePagedModel.add(linkTo(methodOn(ItemController.class)
					.getItems(name, market, status, 0, itemPage.getSize(), sortBy, direction))
					.withRel(FIRST)
					.expand());
		}
		if (!itemPage.isLast()) {
			getItemResponsePagedModel.add(linkTo(methodOn(ItemController.class)
					.getItems(name, market, status, itemPage.getTotalPages() - 1, itemPage.getSize(), sortBy, direction))
					.withRel(LAST)
					.expand());
		}
		return getItemResponsePagedModel;
	}


}
