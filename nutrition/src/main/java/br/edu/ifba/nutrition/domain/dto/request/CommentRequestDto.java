package br.edu.ifba.nutrition.domain.dto.request;

import java.time.LocalDateTime;

import br.edu.ifba.nutrition.entity.Comment;

public record CommentRequestDto(Long id, LocalDateTime dateTime, String text, Long userId, Long tipId) {

    public Comment toEntity(){
        return new Comment(dateTime, text);
    }

}
