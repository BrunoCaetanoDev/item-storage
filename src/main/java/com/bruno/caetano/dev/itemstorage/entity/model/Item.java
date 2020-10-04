package com.bruno.caetano.dev.itemstorage.entity.model;

import com.bruno.caetano.dev.itemstorage.enums.ItemStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.INVALID_MARKET_FIELD_MSG;
import static com.bruno.caetano.dev.itemstorage.utils.constant.ItemStorageConstant.ISO_3166_1_ALPHA_2_REGEX;

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
