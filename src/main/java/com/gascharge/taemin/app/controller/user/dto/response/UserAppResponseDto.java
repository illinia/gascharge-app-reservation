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
    private UserAuthority userControllerAuthority;

    @Builder
    public UserAppResponseDto(String name, String email, String image, UserAuthority userControllerAuthority) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.userControllerAuthority = userControllerAuthority;
    }

    public static UserAppResponseDto getUserAppResponseDto(UserServiceResponseDto dto) {
        return UserAppResponseDto.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .image(dto.getImageUrl())
                .userControllerAuthority(dto.getAuthority())
                .build();
    }
}
