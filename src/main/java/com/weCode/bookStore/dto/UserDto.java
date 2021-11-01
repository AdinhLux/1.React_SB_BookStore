package com.weCode.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    @NotNull(message = "No provided name")
    private String name;

    @NotNull(message = "No provided email")
    private String email;

    @NotNull(message = "No provided password")
    private int password;
}
