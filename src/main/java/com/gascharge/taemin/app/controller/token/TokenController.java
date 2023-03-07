package com.gascharge.taemin.app.controller.token;

import com.gascharge.taemin.service.token.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @ApiOperation(
            value = "토근 값 조회", notes = "유저권한, 어드민권한 토큰 값 조회하는 컨트롤러입니다. 스웨거 페이지 상단에 Authorize 버튼을 눌러서 토큰값 입력하고 테스트하세요."
    )
    @GetMapping("/token")
    public ResponseEntity getTokens() {
        return new ResponseEntity(tokenService.getTokens(), HttpStatus.OK);
    }
}
