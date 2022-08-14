package com.expertpeople.infra.excption;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ApiException {
    private String errorCode;
    private String errorMessage;
}
