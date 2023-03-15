package com.gascharge.taemin.app.controller.user;

import com.gascharge.taemin.domain.enums.user.UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockUser {
    UserAuthority authority() default UserAuthority.ROLE_USER;
}
