package com.bruno.caetano.dev.itemstorage.utils;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SERVICE_OPERATION_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID_HEADER;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

public class HttpUtils {

	public static HttpHeaders buildHttpResponseHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(TRACE_ID_HEADER, MDC.get(TRACE_ID));
		headers.add(SERVICE_OPERATION_HEADER, MDC.get(SERVICE_OPERATION));
		return headers;
	}

}
