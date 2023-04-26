package br.edu.ifba.nutrition.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.nutrition.domain.enums.Category;
import br.edu.ifba.nutrition.entity.Tip;

public record TipResponseWithCommentDto(Long id, String title, String photo, String text, Category category, List<CommentResponseDto> comments) {

    public TipResponseWithCommentDto(Tip tip){
        this(tip.getId(), tip.getTitle(), tip.getPhoto(), tip.getText(), tip.getCategory(), CommentResponseDto.toListDto(tip.getComments()));
    }

    public static List<TipResponseWithCommentDto> toListDto(List<Tip> list) {
        return list.stream().map(TipResponseWithCommentDto::new).collect(Collectors.toList());
    }
    
}
