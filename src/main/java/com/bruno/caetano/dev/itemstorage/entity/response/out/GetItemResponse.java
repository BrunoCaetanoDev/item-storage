package com.bruno.caetano.dev.itemstorage.entity.response.out;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetItemResponse {

	private String id;
	private String name;
	private String description;
	private String market;
	private BigDecimal price;
	private BigInteger stock;
	private String status;

}
