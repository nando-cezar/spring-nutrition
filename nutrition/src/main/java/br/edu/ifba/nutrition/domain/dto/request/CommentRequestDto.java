package br.edu.ifba.nutrition.domain.dto.request;

import java.time.LocalDateTime;

import br.edu.ifba.nutrition.entity.Comment;

public record CommentRequestDto(Long id, LocalDateTime dateTime, String text, UserResquestDto user) {

    public Comment toEntity(){
        return new Comment(dateTime, text, user.toEntity());
    }

}
