package br.edu.ifba.nutrition.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.nutrition.domain.dto.request.CommentRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.CommentResponseDto;
import br.edu.ifba.nutrition.entity.Comment;
import br.edu.ifba.nutrition.repository.CommentRepository;
import br.edu.ifba.nutrition.repository.TipRepository;
import br.edu.ifba.nutrition.repository.UserRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TipRepository tipRepository;

    public Optional<CommentResponseDto> save(CommentRequestDto data){
        var dataEntity = data.toEntity();
        return this.persist(data, dataEntity);
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

    public Optional<CommentResponseDto> update(Long id, CommentRequestDto data){
        var dataEntity = data.toEntity();
        dataEntity.setId(id);
        return this.persist(data, dataEntity);
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }

    private Optional<CommentResponseDto> persist(CommentRequestDto data, Comment dataEntity){

        var userData = userRepository.findById(data.userId());
        var tipData = tipRepository.findById(data.tipId());
        
        if(userData.isPresent() && tipData.isPresent()){
            dataEntity.setUser(userData.get());
            dataEntity.setTip(tipData.get());
            return Optional.of(CommentResponseDto.toDto(commentRepository.save(dataEntity)));
        }
        return Optional.empty();
    }

}
