package com.pdev.user_service.dto.mfa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MFARegistryDTO {
    private String mfaStatus;
    private String refValue;
    private String remark;
}
