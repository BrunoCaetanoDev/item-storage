package com.bruno.caetano.dev.itemstorage.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ItemStatus {

    ACTIVE, INACTIVE;

    public static ItemStatus fromName(String name) {
        Optional<ItemStatus> itemStatus = Arrays.stream(values()).filter(e -> e.name().equals(name)).findFirst();
        return itemStatus.orElse(null);
    }

}
