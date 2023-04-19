package br.edu.ifba.nutrition.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.nutrition.domain.dto.request.CommentRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.CommentResponseDto;
import br.edu.ifba.nutrition.repository.CommentRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public CommentResponseDto save(CommentRequestDto data){
        var dataEntity = data.toEntity();
        return CommentResponseDto.toDto(commentRepository.save(dataEntity)) ;
    }

    public Optional<List<CommentResponseDto>> findByTipId(Long id){
        return Optional.of(CommentResponseDto.toListDto(commentRepository.findByTipId(id)));
    }

    public Optional<List<CommentResponseDto>> findByUserId(Long id){
        return Optional.of(CommentResponseDto.toListDto(commentRepository.findByUserId(id)));
    }

    public Optional<CommentResponseDto> findById(Long id){
        return commentRepository.findById(id).map(CommentResponseDto::new);
    }

    public CommentResponseDto update(Long id, CommentRequestDto entity){
        var data = entity.toEntity();
        data.setId(id);
        return CommentResponseDto.toDto(commentRepository.save(data));
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }
}
