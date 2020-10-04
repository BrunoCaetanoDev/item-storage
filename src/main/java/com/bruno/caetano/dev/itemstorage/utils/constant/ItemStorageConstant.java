package com.bruno.caetano.dev.itemstorage.utils.constant;

public class ItemStorageConstant {


    /**
     * Operations
     */
    public static final String GET_ITEMS_SERVICE_OPERATION = "GetItems";
    public static final String GET_ITEM_SERVICE_OPERATION = "GetItem";
    public static final String CREATE_ITEM_SERVICE_OPERATION = "CreateItem";
    public static final String UPDATE_ITEM_SERVICE_OPERATION = "UpdateItem";
    public static final String DELETE_ITEM_SERVICE_OPERATION = "DeleteItem";
    public static final String RESTOCK_ITEM_SERVICE_OPERATION = "RestockItem";
    public static final String DISPATCH_ITEM_SERVICE_OPERATION = "DispatchItem";
    public static final String UNDEFINED_SERVICE_OPERATION = "Undefined";

    /**
     * MDC Keys
     */
    public static final String SERVICE_OPERATION = "operation";
    public static final String TRACE_ID = "trace-id";

    /**
     * Header Names
     */
    public static final String TRACE_ID_HEADER = "Trace-Id";
    public static final String SERVICE_OPERATION_HEADER = "Service-Operation";
    public static final String LINK_HEADER = "Link";
    public static final String PAGE_NUMBER_HEADER = "Page-Number";
    public static final String PAGE_SIZE_HEADER = "Page-Size";
    public static final String TOTAL_ELEMENTS_HEADER = "Total-Elements";
    public static final String TOTAL_PAGES_HEADER = "Total-Pages";

    /**
     * Messages
     */
    public static final String GET_ITEMS_MSG = "Getting items";
    public static final String GET_ITEMS_COUNT_MSG = "Got {} items out of {}";
    public static final String GET_ITEM_MSG = "Getting item";
    public static final String GET_ITEM_RESULT_MSG = "Got item id::{}";
    public static final String CREATE_ITEM_MSG = "Creating item";
    public static final String CREATE_ITEM_RESULT_MSG = "Created item id::{}";
    public static final String UPDATE_ITEM_MSG = "Updating item id::{}";
    public static final String UPDATE_ITEM_RESULT_MSG = "Updated item id::{}";
    public static final String DELETE_ITEM_MSG = "Deleting item id::{}";
    public static final String DELETE_ITEM_RESULT_MSG = "Deleted item id::{}";
    public static final String RESTOCK_ITEM_MSG = "Restocking item id::{}";
    public static final String RESTOCK_ITEM_RESULT_MSG = "Restocked item id::{} by amount {}";
    public static final String DISPATCH_ITEM_MSG = "Dispatching item id::{}";
    public static final String DISPATCH_ITEM_RESULT_MSG = "Dispatched item id::{} by amount {}";
    public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP {} Request to {} at {}";
    public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status {} at {}";
    public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time {} ms";
    public static final String INVALID_MARKET_FIELD_MSG = "market field should match ISO 3166-1 alpha-2 specification";
    public static final String INVALID_EMPTY_OR_BLANK_STRING_MSG = "cannot be empty or blank";
    public static final String ENTITY_NOT_FOUND_MSG = "Entity %s id::{%s} not found.";

    /**
     * Miscellaneous
     */
    public static final String FRONT_SLASH_DELIMITER = "/";
    public static final String WHITE_SPACE_DELIMITER = " ";
    public static final String SEMI_COLON_DELIMITER = ";";
    public static final String ITEM_API_DESCRIPTION = "A public Restful Api that allows to manage the various item resources.";
    public static final String ISO_3166_1_ALPHA_2_REGEX = "^[A-Z]{2}$";
    public static final String EMPTY_OR_BLANK_STRING_REGEX = "^(?!\\s*$).+";
    public static final String ITEMS = "items";
    public static final String ITEM = "item";

}
