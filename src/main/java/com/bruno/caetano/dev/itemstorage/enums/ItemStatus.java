package com.bruno.caetano.dev.itemstorage.enums;

import com.bruno.caetano.dev.itemstorage.error.InvalidResourceStatusException;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ITEM;

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
