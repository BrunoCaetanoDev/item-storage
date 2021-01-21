package com.bruno.caetano.dev.itemstorage.entity.request.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestockItemRequest {

    @JsonIgnore
    private String id;
    @NotNull
    @PositiveOrZero
    private BigInteger quantity;

}
