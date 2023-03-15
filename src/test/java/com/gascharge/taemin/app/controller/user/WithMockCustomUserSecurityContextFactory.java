package com.gascharge.taemin.app.controller.user;

import com.gascharge.taemin.domain.entity.user.User;
import com.gascharge.taemin.domain.entity.user.UserTestData;
import com.gascharge.taemin.domain.enums.user.UserAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser authority) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = null;

        if (authority.authority().equals(UserAuthority.ROLE_USER)) {
            user = UserTestData.getCloneUser();
        }

        if (authority.authority().equals(UserAuthority.ROLE_ADMIN)) {
            user = UserTestData.getCloneAdmin();
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getUserAuthority().toString())));
        context.setAuthentication(auth);
        return context;
    }
}
