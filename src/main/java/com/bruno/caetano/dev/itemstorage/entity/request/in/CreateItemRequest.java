package com.bruno.caetano.dev.itemstorage.entity.request.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.INVALID_MARKET_FIELD_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ISO_3166_1_ALPHA_2_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

    @NotNull
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    @Pattern(regexp = ISO_3166_1_ALPHA_2_REGEX, message = INVALID_MARKET_FIELD_MSG)
    private String market;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @PositiveOrZero
    private BigInteger stock;

}
