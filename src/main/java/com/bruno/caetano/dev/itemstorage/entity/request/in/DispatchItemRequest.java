package com.bruno.caetano.dev.itemstorage.entity.request.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigInteger;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchItemRequest {

	@JsonIgnore
	private String id;
	@NotNull
	@PositiveOrZero
	private BigInteger quantity;

}
