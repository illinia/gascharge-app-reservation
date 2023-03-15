package com.gascharge.taemin.app.controller.user;

import com.gascharge.taemin.app.controller.user.dto.response.UserAppResponseDto;
import com.gascharge.taemin.domain.entity.user.search.UserSearchStatus;
import com.gascharge.taemin.security.entity.CurrentUser;
import com.gascharge.taemin.security.entity.UserPrincipal;
import com.gascharge.taemin.service.user.UserService;
import com.gascharge.taemin.service.user.dto.UserServiceResponseDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.gascharge.taemin.app.controller.user.dto.response.UserAppResponseDto.getUserAppResponseDto;
import static com.gascharge.taemin.domain.entity.user.UserTestData.USER_TEST_EMAIL;
import static com.gascharge.taemin.domain.entity.user.UserTestData.USER_TEST_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(
            value = "현재 인증된 유저 정보 검색", notes = "OAuth2 로 로그인한 인증된 유저 정보를 불러옵니다."
    )
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserAppResponseDto> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserServiceResponseDto responseDto = userService.findById(userPrincipal.getId());

        return ResponseEntity.ok(getUserAppResponseDto(responseDto));
    }

    @ApiOperation(
            value = "유저 전체, 다건 조회", notes = "이메일 포함, 이메일 인증 여부 일치, 이름 포함, 로그인 공급자 일치, 권한 일치 를 포함한 페이징 검색을 합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "email",
                    value = "이메일 포함 검색",
                    defaultValue = USER_TEST_EMAIL
            ),
            @ApiImplicitParam(
                    name = "email-verified",
                    value = "이메일 유효 상태 일치 검색",
                    defaultValue = "UNVERIFIED"
            ),
            @ApiImplicitParam(
                    name = "name",
                    value = "이름 포함 검색",
                    defaultValue = USER_TEST_NAME
            ),
            @ApiImplicitParam(
                    name = "provider",
                    value = "로그인 공급자 이름 일치 검색",
                    defaultValue = "google"
            ),
            @ApiImplicitParam(
                    name = "user-authority",
                    value = "인증 권한 일치 검색",
                    defaultValue = "ROLE_USER"
            )

    })
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserAppResponseDto>> getUserList(
            @RequestBody UserSearchStatus userSearchStatus,
            @NotNull Pageable pageable) {
        List<UserServiceResponseDto> all = userService.findAll(userSearchStatus, pageable);
        List<UserAppResponseDto> collect = all.stream().map(UserAppResponseDto::getUserAppResponseDto).collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(collect, pageable, all.size()));
    }
}









