package br.edu.ifba.nutrition.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.nutrition.entity.Comment;

public record CommentResponseDto(Long id, LocalDateTime dateTime, String text, UserResponseDto user) {

    public CommentResponseDto(Comment comment){
        this(comment.getId(), comment.getDateTime(), comment.getText(), UserResponseDto.toDto(comment.getUser()));
    }

    public static List<CommentResponseDto> toListDto(List<Comment> list) {
        return list.stream().map(CommentResponseDto::new).toList();
    }

    public static CommentResponseDto toDto(Comment data) {
        return new CommentResponseDto(data);
    }

}