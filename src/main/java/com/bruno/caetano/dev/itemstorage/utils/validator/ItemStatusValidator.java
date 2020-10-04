package com.bruno.caetano.dev.itemstorage.utils.validator;

import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.utils.annotation.ItemStatusSubset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ItemStatusValidator implements ConstraintValidator<ItemStatusSubset, String> {
    private ItemStatus[] subset;

    @Override
    public void initialize(ItemStatusSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || Arrays.asList(subset).stream().map(ItemStatus::name).anyMatch(s -> s.equals(value));
    }
}
