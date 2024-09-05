package com.wo.agregadordeinvestimentos.controller.dto;

import com.wo.agregadordeinvestimentos.entity.User;

import java.util.Optional;
import java.util.UUID;

public record UserResponseIdDto(UUID userId, String username, String email) {
}
