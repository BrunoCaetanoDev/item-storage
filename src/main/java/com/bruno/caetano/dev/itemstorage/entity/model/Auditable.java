package com.bruno.caetano.dev.itemstorage.entity.model;

import java.time.Instant;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class Auditable {

	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private Instant modifiedAt;
	@CreatedDate
	private Instant createdAt;
	@LastModifiedBy
	private String lastModifiedBy;

}
