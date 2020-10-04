package com.bruno.caetano.dev.itemstorage.entity.response.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemResponse {

    private String name;
    private String description;
    private String market;
    private BigDecimal price;
    private BigInteger stock;
    private String status;

}
