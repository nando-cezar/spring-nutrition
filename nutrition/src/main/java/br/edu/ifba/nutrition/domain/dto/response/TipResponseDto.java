package br.edu.ifba.nutrition.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.nutrition.domain.enums.Category;
import br.edu.ifba.nutrition.entity.Tip;

public record TipResponseDto(Long id, String title, String photo, String text, Category category, List<CommentResponseDto> comments) {

    public TipResponseDto(Tip tip){
        this(tip.getId(), tip.getTitle(), tip.getPhoto(), tip.getText(), tip.getCategory(), CommentResponseDto.toListDto(tip.getComments()));
    }

    public static List<TipResponseDto> toListDto(List<Tip> list) {
        return list.stream().map(TipResponseDto::new).collect(Collectors.toList());
    }

    public static TipResponseDto toDto(Tip data) {
        return new TipResponseDto(data);
    }

    public Tip toEntity(){
        return new Tip(
            id,
            title, 
            photo, 
            text, 
            category, null);
    }
    
}
