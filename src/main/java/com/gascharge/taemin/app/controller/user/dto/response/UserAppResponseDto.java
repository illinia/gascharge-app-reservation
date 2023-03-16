package com.gascharge.taemin.app.controller.user.dto.response;

import com.gascharge.taemin.domain.enums.user.UserAuthority;
import com.gascharge.taemin.service.user.dto.UserServiceResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
public class UserAppResponseDto {
    private String name;
    private String email;
    private String image;
    private UserAuthority userAuthority;

    @Builder
    public UserAppResponseDto(String name, String email, String image, UserAuthority userAuthority) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.userAuthority = userAuthority;
    }

    public static UserAppResponseDto getUserAppResponseDto(UserServiceResponseDto dto) {
        return UserAppResponseDto.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .image(dto.getImageUrl())
                .userAuthority(dto.getAuthority())
                .build();
    }
}
