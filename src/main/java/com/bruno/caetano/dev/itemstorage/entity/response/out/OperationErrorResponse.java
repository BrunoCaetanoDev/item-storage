package com.bruno.caetano.dev.itemstorage.entity.response.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationErrorResponse {

    private String traceId;
    private String operation;
    private int code;
    private String message;

}
