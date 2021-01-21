package com.bruno.caetano.dev.itemstorage.controller;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.service.ItemService;
import com.bruno.caetano.dev.itemstorage.utils.annotation.SpringIntegrationTest;
import com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageTestConstant;
import com.bruno.caetano.dev.itemstorage.utils.interceptor.HttpLoggerInterceptor;
import com.bruno.caetano.dev.itemstorage.utils.interceptor.MdcInitInterceptor;
import com.bruno.caetano.dev.itemstorage.utils.properties.ItemStorageProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringIntegrationTest
@ExtendWith(SpringExtension.class)
class ItemControllerTest extends ItemStorageTestConstant {

    private static String Q_PARAM_URI = "?name=%s&market=%s&status=%s&sort=%s";
    @Autowired
    private ItemController itemController;
    @Autowired
    private RestControllerAdvice restControllerAdvice;
    @Autowired
    private MdcInitInterceptor mdcInitInterceptor;
    @Autowired
    private HttpLoggerInterceptor httpLoggerInterceptor;
    @Autowired
    private ItemStorageProperties itemStorageProperties;
    @MockBean
    private ItemService itemService;
    private MockMvc mockMvc;
    @Value("classpath:samples/requests/createItemWhenValidReturn201Created.json")
    private Resource createItemWhenValidReturn200OkRequest;
    @Value("classpath:samples/responses/createItemWhenValidReturn201Created.json")
    private Resource createItemWhenValidReturn200OkResponse;
    @Value("classpath:samples/responses/getItemWhenExistsReturn200Ok.json")
    private Resource getItemWhenExistsReturn200Ok;
    @Value("classpath:samples/responses/getItemsWhenExistsReturn200Ok.json")
    private Resource getItemsWhenExistsReturn200Ok;
    @Value("classpath:samples/requests/updateItemWhenValidReturn200Ok.json")
    private Resource updateItemWhenValidReturn200OkRequest;
    @Value("classpath:samples/responses/updateItemWhenValidReturn200Ok.json")
    private Resource updateItemWhenValidReturn200OkResponse;
    @Value("classpath:samples/requests/restockItemWhenValidReturn200Ok.json")
    private Resource restockItemWhenValidReturn200OkRequest;
    @Value("classpath:samples/requests/dispatchItemWhenValidReturn200Ok.json")
    private Resource dispatchItemWhenValidReturn200OkRequest;
    @Value("classpath:samples/responses/getItemsWhenInvalidStatusFilter400BadRequest.json")
    private Resource getItemsWhenInvalidStatusFilter400BadRequest;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice(restControllerAdvice)
                .addInterceptors(mdcInitInterceptor, httpLoggerInterceptor)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getItems() throws Exception {
        Item persistedItem = Item.builder()
                .id(ITEM_ONE_ID)
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .market(ITEM_ONE_MARKET)
                .price(BigDecimal.TEN)
                .stock(BigInteger.ONE).build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(getItemsWhenExistsReturn200Ok.getFile()));
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.fromString(Sort.Direction.ASC.name()), "id"));
        when(itemService.findAll(Item.builder().build(), pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(persistedItem), pageRequest, 5));

        mockMvc.perform(get(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS)).concat("?size=1&sort=id,asc"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(LINK_HEADER))
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_ITEMS_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void getItemsWithQueryParams() throws Exception {
        Item persistedItem = Item.builder()
                .id(ITEM_ONE_ID)
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .market(ITEM_ONE_MARKET)
                .price(BigDecimal.TEN)
                .stock(BigInteger.ONE).build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(getItemsWhenExistsReturn200Ok.getFile()));
        PageRequest pageRequest = PageRequest
                .of(0, 20, Sort.by(Sort.Direction.fromString(Sort.Direction.ASC.name()), "name"));
        when(itemService
                .findAll(Item.builder().name(ITEM_ONE_NAME).market(ITEM_ONE_MARKET).status(ItemStatus.ACTIVE).build(),
                        pageRequest))
                .thenReturn(new PageImpl<>(Collections.singletonList(persistedItem), pageRequest, 5));

        mockMvc.perform(get(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS))
                .concat(String.format(Q_PARAM_URI, ITEM_ONE_NAME, ITEM_ONE_MARKET, ItemStatus.ACTIVE.name(), "name")))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(LINK_HEADER))
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_ITEMS_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void getItemsWithInvalidStatusQParam() throws Exception {

        String responseContent = FileCopyUtils
                .copyToString(new FileReader(getItemsWhenInvalidStatusFilter400BadRequest.getFile()));
        mockMvc
                .perform(get(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS)).concat("?status=invalid"))
                        .header(TRACE_ID_HEADER, "getItemsWithInvalidStatusQParam")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(TRACE_ID_HEADER, "getItemsWithInvalidStatusQParam"))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_ITEMS_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void getItem() throws Exception {
        Item persistedItem = Item.builder()
                .id(ITEM_ONE_ID)
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .market(ITEM_ONE_MARKET)
                .price(BigDecimal.TEN)
                .stock(BigInteger.ONE).build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(getItemWhenExistsReturn200Ok.getFile()));

        when(itemService.findBydId(ITEM_ONE_ID)).thenReturn(persistedItem);

        mockMvc.perform(get(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS, ITEM_ONE_ID)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_ITEM_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void createItem() throws Exception {
        Item item = Item.builder()
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .price(BigDecimal.TEN)
                .market(ITEM_ONE_MARKET)
                .stock(BigInteger.ONE).build();

        Item persistedItem = Item.builder()
                .id(ITEM_ONE_ID)
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .market(ITEM_ONE_MARKET)
                .price(BigDecimal.TEN)
                .stock(BigInteger.ONE).build();

        when(itemService.save(item)).thenReturn(persistedItem);

        String requestContent = FileCopyUtils.copyToString(new FileReader(createItemWhenValidReturn200OkRequest.getFile()));
        String responseContent = FileCopyUtils
                .copyToString(new FileReader(createItemWhenValidReturn200OkResponse.getFile()));

        mockMvc.perform(post(FRONT_SLASH_DELIMITER.concat(ITEMS))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION,
                        String.join(FRONT_SLASH_DELIMITER, itemStorageProperties.getServletContextPath(), ITEMS, ITEM_ONE_ID)))
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_ITEM_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void updateItem() throws Exception {
        Item item = Item.builder()
                .id(ITEM_ONE_ID)
                .price(BigDecimal.ONE)
                .build();

        Item persistedItem = Item.builder()
                .id(ITEM_ONE_ID)
                .name(ITEM_ONE_NAME)
                .description(ITEM_ONE_DESCRIPTION)
                .market(ITEM_ONE_MARKET)
                .price(BigDecimal.ONE)
                .stock(BigInteger.ONE).build();

        when(itemService.update(item)).thenReturn(persistedItem);

        String requestContent = FileCopyUtils.copyToString(new FileReader(updateItemWhenValidReturn200OkRequest.getFile()));
        String responseContent = FileCopyUtils
                .copyToString(new FileReader(updateItemWhenValidReturn200OkResponse.getFile()));

        mockMvc.perform(patch(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS, ITEM_ONE_ID)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, UPDATE_ITEM_SERVICE_OPERATION))
                .andExpect(content().json(responseContent));
    }

    @Test
    void deleteItem() throws Exception {
        doNothing().when(itemService).deleteById(ITEM_ONE_ID);
        mockMvc.perform(delete(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS, ITEM_ONE_ID))))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, DELETE_ITEM_SERVICE_OPERATION));
    }

    @Test
    void restockItem() throws Exception {
        String requestContent = FileCopyUtils
                .copyToString(new FileReader(restockItemWhenValidReturn200OkRequest.getFile()));
        doNothing().when(itemService).restock(ITEM_ONE_ID, BigInteger.TEN);

        mockMvc
                .perform(post(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS, ITEM_ONE_ID, "restock")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, RESTOCK_ITEM_SERVICE_OPERATION));
    }

    @Test
    void dispatchItem() throws Exception {
        String requestContent = FileCopyUtils
                .copyToString(new FileReader(dispatchItemWhenValidReturn200OkRequest.getFile()));
        doNothing().when(itemService).dispatch(ITEM_ONE_ID, BigInteger.TEN);

        mockMvc
                .perform(post(FRONT_SLASH_DELIMITER.concat(String.join(FRONT_SLASH_DELIMITER, ITEMS, ITEM_ONE_ID, "dispatch")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, DISPATCH_ITEM_SERVICE_OPERATION));
    }
}