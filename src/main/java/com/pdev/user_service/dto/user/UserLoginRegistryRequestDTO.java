package com.pdev.user_service.dto.user;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.internal.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserLoginRegistryRequestDTO {
    private User user;
    private Integer isLogin;
    private ApplicationScope applicationScope;
}
