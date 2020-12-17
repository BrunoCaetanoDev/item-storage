package com.bruno.caetano.dev.itemstorage.error;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.COLON_WHITE_SPACE_DELIMITER;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.WHITE_SPACE_DELIMITER;

import java.util.List;

public class InvalidResourceStatusException extends RuntimeException {

	private static String INVALID_RESOURCE_STATUS_MSG = "Invalid %s status %s.";
	private static String RECOGNIZED_STATUS_MSG = "Recognized status %s.";


	public InvalidResourceStatusException(String entity, String status) {
		super(getFormattedInvalidResourceStatusMessage(entity, status));
	}

	public InvalidResourceStatusException(String entity, String name, List<String> status) {
		super(String.join(WHITE_SPACE_DELIMITER, getFormattedInvalidResourceStatusMessage(entity, name),
				getFormattedRecognizedResourceStatusMessage(status)));
	}

	private static String getFormattedInvalidResourceStatusMessage(String entity, String status) {
		return String.format(INVALID_RESOURCE_STATUS_MSG, entity, status);
	}

	private static String getFormattedRecognizedResourceStatusMessage(List<String> status) {
		return String.format(RECOGNIZED_STATUS_MSG, String.join(COLON_WHITE_SPACE_DELIMITER, status));
	}

}
