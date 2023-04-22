package br.edu.ifba.nutrition.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.nutrition.domain.dto.request.UserRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.UserResponseDto;
import br.edu.ifba.nutrition.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository tipRepository;

    public UserResponseDto save(UserRequestDto data){
        var dataEntity = data.toEntity();
        return UserResponseDto.toDto(tipRepository.save(dataEntity)) ;
    }

    public Optional<UserResponseDto> findById(Long id){
        return tipRepository.findById(id).map(UserResponseDto::new);
    }

    public UserResponseDto update(Long id, UserRequestDto entity){
        var data = entity.toEntity();
        data.setId(id);
        return UserResponseDto.toDto(tipRepository.save(data));
    }

    public void deleteById(Long id){
        tipRepository.deleteById(id);
    }
}
