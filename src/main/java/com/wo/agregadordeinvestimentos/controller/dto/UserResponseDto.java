package com.wo.agregadordeinvestimentos.controller.dto;

import java.util.UUID;

public record UserResponseDto(UUID userId, String username, String email) {
}
