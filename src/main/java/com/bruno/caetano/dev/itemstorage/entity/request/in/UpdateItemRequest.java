package com.bruno.caetano.dev.itemstorage.entity.request.in;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.EMPTY_OR_BLANK_STRING_REGEX;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.INVALID_EMPTY_OR_BLANK_STRING_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.INVALID_MARKET_FIELD_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ISO_3166_1_ALPHA_2_REGEX;

import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import com.bruno.caetano.dev.itemstorage.utils.annotation.ItemStatusSubset;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

	@JsonIgnore
	private String id;
	@Pattern(regexp = EMPTY_OR_BLANK_STRING_REGEX, message = INVALID_EMPTY_OR_BLANK_STRING_MSG)
	private String name;
	@Pattern(regexp = EMPTY_OR_BLANK_STRING_REGEX, message = INVALID_EMPTY_OR_BLANK_STRING_MSG)
	private String description;
	@Pattern(regexp = ISO_3166_1_ALPHA_2_REGEX, message = INVALID_MARKET_FIELD_MSG)
	private String market;
	@Positive
	private BigDecimal price;
	@PositiveOrZero
	private BigInteger stock;
	@ItemStatusSubset(anyOf = {ItemStatus.ACTIVE, ItemStatus.INACTIVE})
	private String status;

}
