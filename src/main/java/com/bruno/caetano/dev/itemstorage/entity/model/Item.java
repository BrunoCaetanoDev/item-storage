package com.bruno.caetano.dev.itemstorage.entity.model;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.INVALID_MARKET_FIELD_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ISO_3166_1_ALPHA_2_REGEX;

import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Item extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	@Pattern(regexp = ISO_3166_1_ALPHA_2_REGEX, message = INVALID_MARKET_FIELD_MSG)
	private String market;
	@Positive
	private BigDecimal price;
	@PositiveOrZero
	private BigInteger stock;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ItemStatus status;

}
