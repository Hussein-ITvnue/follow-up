package com.medido.followup.controller;

import com.medido.followup.exception.AuthorizationException;
import com.medido.followup.exception.ExceptionMessages;
import com.medido.followup.security.AuthUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    public void authorizationCheck(AuthUser authUser, Integer companyId) {
        if (companyId != null) {
            if (!companyId.equals(authUser.getCompanyId())) {
                throw new AuthorizationException(ExceptionMessages.CANT_MANAGE_COMPANY);
            }
        }
    }

}
