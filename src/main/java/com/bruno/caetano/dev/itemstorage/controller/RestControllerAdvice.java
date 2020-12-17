package com.bruno.caetano.dev.itemstorage.controller;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.LINK_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.PAGE_NUMBER_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.PAGE_SIZE_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SEMI_COLON_DELIMITER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.SERVICE_OPERATION_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TOTAL_ELEMENTS_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TOTAL_PAGES_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.TRACE_ID_HEADER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.UNDEFINED_SERVICE_OPERATION;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.WHITE_SPACE_DELIMITER;
import static java.util.stream.Collectors.joining;

import com.bruno.caetano.dev.itemstorage.entity.response.out.OperationErrorResponse;
import com.bruno.caetano.dev.itemstorage.error.InvalidResourceStatusException;
import com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice implements ResponseBodyAdvice<Object> {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<OperationErrorResponse> handleNotFoundError(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<OperationErrorResponse> handleBadRequestMethodArgument(MethodArgumentNotValidException e) {
		List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
				.map(f -> String.join(WHITE_SPACE_DELIMITER, f.getField(), f.getDefaultMessage()))
				.collect(Collectors.toList());
		return buildErrorMessageResponseEntity(String.join(SEMI_COLON_DELIMITER.concat(WHITE_SPACE_DELIMITER), fieldErrors),
				HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidResourceStatusException.class)
	public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidResourceStatus(
			InvalidResourceStatusException e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<OperationErrorResponse> handleConflict(DataIntegrityViolationException e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<OperationErrorResponse> handleInternalError(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<OperationErrorResponse> buildErrorMessageResponseEntity(String msg, HttpStatus httpStatus) {
		log.error(msg);
		return new ResponseEntity<>(
				OperationErrorResponse.builder()
						.message(msg)
						.code(httpStatus.value())
						.traceId(MDC.get(ItemStorageConstant.TRACE_ID))
						.operation(MDC.get(ItemStorageConstant.SERVICE_OPERATION))
						.build(),
				httpStatus);
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {

		serverHttpResponse.getHeaders().add(TRACE_ID_HEADER, MDC.get(TRACE_ID));
		serverHttpResponse.getHeaders().add(SERVICE_OPERATION_HEADER,
				Optional.ofNullable(MDC.get(SERVICE_OPERATION)).orElse(UNDEFINED_SERVICE_OPERATION));

		if (body instanceof PagedModel<?>) {
			PagedModel pagedModel = (PagedModel) body;
			serverHttpResponse.getHeaders()
					.add(LINK_HEADER, pagedModel.getLinks().stream().map(Link::toString).collect(joining(SEMI_COLON_DELIMITER)));
			serverHttpResponse.getHeaders().add(PAGE_NUMBER_HEADER, String.valueOf(pagedModel.getMetadata().getNumber()));
			serverHttpResponse.getHeaders().add(PAGE_SIZE_HEADER, String.valueOf(pagedModel.getMetadata().getSize()));
			serverHttpResponse.getHeaders()
					.add(TOTAL_ELEMENTS_HEADER, String.valueOf(pagedModel.getMetadata().getTotalElements()));
			serverHttpResponse.getHeaders().add(TOTAL_PAGES_HEADER, String.valueOf(pagedModel.getMetadata().getTotalPages()));
			return pagedModel.getContent();
		}

		return body;
	}
}