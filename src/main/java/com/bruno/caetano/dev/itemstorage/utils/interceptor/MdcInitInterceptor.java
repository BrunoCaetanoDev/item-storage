package com.bruno.caetano.dev.itemstorage.utils.interceptor;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.UNDEFINED_SERVICE_OPERATION;

import com.bruno.caetano.dev.itemstorage.utils.annotation.ServiceOperation;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class MdcInitInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		MDC.put(TRACE_ID, Optional.ofNullable(request.getHeader(TRACE_ID_HEADER)).orElse(UUID.randomUUID().toString()));
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Optional<ServiceOperation> serviceOperationAnnotation = Optional
					.ofNullable(handlerMethod.getMethodAnnotation(ServiceOperation.class));
			MDC.put(SERVICE_OPERATION, serviceOperationAnnotation.isPresent() ? serviceOperationAnnotation.get().value()
					: UNDEFINED_SERVICE_OPERATION);
		}
		return true;
	}

}
