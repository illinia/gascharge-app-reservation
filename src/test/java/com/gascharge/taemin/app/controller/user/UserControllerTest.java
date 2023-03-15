//package com.gascharge.taemin.app.controller.user;
//
//import com.gascharge.taemin.app.error.exception.GlobalExceptionHandler;
//import com.gascharge.taemin.app.error.exception.HandleException;
//import com.gascharge.taemin.domain.entity.user.User;
//import com.gascharge.taemin.domain.entity.user.UserTestData;
//import com.gascharge.taemin.domain.entity.user.search.UserSearchStatus;
//import com.gascharge.taemin.domain.enums.user.UserAuthority;
//import com.gascharge.taemin.security.config.SecurityConfig;
//import com.gascharge.taemin.security.filter.TokenAuthenticationFilter;
//import com.gascharge.taemin.service.user.UserService;
//import com.gascharge.taemin.service.user.dto.UserServiceResponseDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.gascharge.taemin.domain.entity.user.UserTestData.*;
//import static com.gascharge.taemin.service.util.user.UserUtil.getUserServiceResponseDto;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(
//        controllers = UserController.class,
//        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, TokenAuthenticationFilter.class, GlobalExceptionHandler.class, HandleException.class})
//        }
//)
//public class UserControllerTest {
//    @MockBean
//    UserService userService;
//    @Autowired
//    UserController userController;
//    @MockBean
//    UserDetailsService userDetailsService;
//
//    private MockMvc mvc;
//
//    @BeforeEach
//    void setMockMvc() {
//        this.mvc = MockMvcBuilders
//                .standaloneSetup(userController)
//                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(UserTestData.getCloneAdmin()), new PageableHandlerMethodArgumentResolver())
//                .build();
//    }
//
//    @Test
//    void getUserInfoWithAuthorization() throws Exception {
//        // given USER_TEST
//
//        // when
//        when(userService.findById(anyLong())).thenReturn(getUserServiceResponseDto(UserTestData.getCloneUser()));
//
//        MockMvc mvc = MockMvcBuilders
//                .standaloneSetup(userController)
//                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(UserTestData.getCloneUser()))
//                .build();
//
//        // then
//        mvc.perform(get("/user/me"))
//                .andExpect(jsonPath("$.name").value(USER_TEST_NAME))
//                .andExpect(jsonPath("$.email").value(USER_TEST_EMAIL))
//                .andExpect(jsonPath("$.authority").value(UserAuthority.ROLE_USER.toString()))
//                .andDo(print())
//        ;
//    }
//
//    @Test
//    void getUserListWithEmail() throws Exception {
//        // given USER_TEST
//        List<UserServiceResponseDto> userList = new ArrayList<>();
//        User cloneUser = getCloneUser();
//        userList.add(getUserServiceResponseDto(cloneUser));
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(userList);
//
//        // then
//        this.mvc.perform(get("/user")
//                        .param("email", cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].name").value(cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].email").value(cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(cloneUser.getImageUrl()))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(cloneUser.getUserAuthority().toString()))
//
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getUserListWithEmailVerified() throws Exception {
//        // given USER_TEST
//        List<UserServiceResponseDto> userList = new ArrayList<>();
//        User cloneUser = getCloneUser();
//        userList.add(getUserServiceResponseDto(cloneUser));
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(userList);
//
//        // then
//        this.mvc.perform(get("/user")
//                        .param("email-verified", cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].name").value(cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].email").value(cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(cloneUser.getImageUrl()))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(cloneUser.getUserAuthority().toString()))
//
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getUserListWithName() throws Exception {
//        // given USER_TEST
//        List<UserServiceResponseDto> userList = new ArrayList<>();
//        User cloneUser = getCloneUser();
//        userList.add(getUserServiceResponseDto(cloneUser));
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(userList);
//
//        // then
//        this.mvc.perform(get("/user")
//                        .param("name", cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].name").value(cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].email").value(cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(cloneUser.getImageUrl()))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(cloneUser.getUserAuthority().toString()))
//
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getUserListWithProvider() throws Exception {
//        // given USER_TEST
//        List<UserServiceResponseDto> userList = new ArrayList<>();
//        User cloneUser = getCloneUser();
//        userList.add(getUserServiceResponseDto(cloneUser));
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(userList);
//
//        // then
//        this.mvc.perform(get("/user")
//                        .param("provider", cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].name").value(cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].email").value(cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(cloneUser.getImageUrl()))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(cloneUser.getUserAuthority().toString()))
//
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getUserListWithUserAuthority() throws Exception {
//        // given USER_TEST
//        List<UserServiceResponseDto> userList = new ArrayList<>();
//        User cloneUser = getCloneUser();
//        userList.add(getUserServiceResponseDto(cloneUser));
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(userList);
//
//        // then
//        this.mvc.perform(get("/user")
//                        .param("user-authority", cloneUser.getUserAuthority().toString()))
//                .andExpect(jsonPath("$.content[0].name").value(cloneUser.getName()))
//                .andExpect(jsonPath("$.content[0].email").value(cloneUser.getEmail()))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(cloneUser.getImageUrl()))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(cloneUser.getEmailVerified().toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(cloneUser.getProvider().toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(cloneUser.getUserAuthority().toString()))
//
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getUserListWithPageable() throws Exception {
//        // given pageable
//
//        List<UserServiceResponseDto> users = new ArrayList<>();
//        users.add(getUserServiceResponseDto(UserTestData.getCloneUser()));
//        users.add(getUserServiceResponseDto(UserTestData.getCloneAdmin()));
//
//        Sort sort = Sort.by("name.DESC");
//
//        // when
//        when(userService.findAll(any(UserSearchStatus.class), any(Pageable.class))).thenReturn(users);
//
//        this.mvc.perform(get("/user")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .param("sort", sort.toString()))
//                .andExpect(jsonPath("$.content[0].name").value(USER_TEST_NAME))
//                .andExpect(jsonPath("$.content[0].email").value(USER_TEST_EMAIL))
//                .andExpect(jsonPath("$.content[0].imageUrl").value(USER_TEST_IMAGE_URL))
//                .andExpect(jsonPath("$.content[0].emailVerified").value(USER_TEST_EMAIL_VERIFIED.toString()))
//                .andExpect(jsonPath("$.content[0].authProvider").value(USER_TEST_AUTH_PROVIDER.toString()))
//                .andExpect(jsonPath("$.content[0].authority").value(USER_TEST_USER_AUTHORITY.toString()))
//
//                .andExpect(jsonPath("$.content[1].name").value(ADMIN_TEST_NAME))
//                .andExpect(jsonPath("$.content[1].email").value(ADMIN_TEST_EMAIL))
//                .andExpect(jsonPath("$.content[1].imageUrl").value(ADMIN_TEST_IMAGE_URL))
//                .andExpect(jsonPath("$.content[1].emailVerified").value(ADMIN_TEST_EMAIL_VERIFIED.toString()))
//                .andExpect(jsonPath("$.content[1].authProvider").value(ADMIN_TEST_AUTH_PROVIDER.toString()))
//                .andExpect(jsonPath("$.content[1].authority").value(ADMIN_TEST_USER_AUTHORITY.toString()))
//
//                .andExpect(status().isOk())
//        ;
//    }
//}
