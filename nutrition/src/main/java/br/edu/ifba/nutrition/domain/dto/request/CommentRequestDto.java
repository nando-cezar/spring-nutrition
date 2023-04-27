package br.edu.ifba.nutrition.domain.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.nutrition.domain.dto.response.CommentResponseDto;
import br.edu.ifba.nutrition.entity.Comment;

public record CommentRequestDto(LocalDateTime dateTime, String text, Long userId, Long tipId) {

    public Comment toEntity(){
        return new Comment(dateTime, text);
    }

}
