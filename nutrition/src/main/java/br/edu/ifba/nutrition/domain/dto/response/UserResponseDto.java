package br.edu.ifba.nutrition.domain.dto.response;

import br.edu.ifba.nutrition.entity.User;

public record UserResponseDto(Long id, String username) {

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getUsername());
    }
    
}
