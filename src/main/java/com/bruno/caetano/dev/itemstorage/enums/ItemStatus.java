package com.bruno.caetano.dev.itemstorage.enums;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ITEM;

import com.bruno.caetano.dev.itemstorage.error.InvalidResourceStatusException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public enum ItemStatus {

	ACTIVE, INACTIVE;

	public static ItemStatus fromName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Optional<ItemStatus> itemStatus = Arrays.stream(values()).filter(e -> e.name().equals(name)).findFirst();
		return itemStatus.orElseThrow(
				() -> new InvalidResourceStatusException(ITEM, name, Arrays.stream(values()).map(Enum::name).collect(
						Collectors.toList())));
	}

}
