package br.edu.ifba.nutrition.domain.dto.request;

import br.edu.ifba.nutrition.entity.User;

public record UserRequestDto(String username, String password) {
    
    public User toEntity(){
        return new User(username, password);
    }
}
