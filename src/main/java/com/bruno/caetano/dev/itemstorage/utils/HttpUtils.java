package com.bruno.caetano.dev.itemstorage.utils;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.*;

public class HttpUtils {

    public static HttpHeaders buildHttpResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(TRACE_ID_HEADER, MDC.get(TRACE_ID));
        headers.add(SERVICE_OPERATION_HEADER, MDC.get(SERVICE_OPERATION));
        return headers;
    }

}
