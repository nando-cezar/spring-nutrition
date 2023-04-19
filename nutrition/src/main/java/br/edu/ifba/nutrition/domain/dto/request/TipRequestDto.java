package br.edu.ifba.nutrition.domain.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.nutrition.domain.enums.Category;
import br.edu.ifba.nutrition.entity.Tip;

public record TipRequestDto(Long id, String title, String photo, String text, Category category, List<CommentRequestDto> comments) {
    
    public Tip toEntity(){
        return new Tip(
            title, 
            photo, 
            text, 
            category, 
            comments.stream()
                .map(e -> e.toEntity())
                .collect(Collectors.toList()));
    }
}
