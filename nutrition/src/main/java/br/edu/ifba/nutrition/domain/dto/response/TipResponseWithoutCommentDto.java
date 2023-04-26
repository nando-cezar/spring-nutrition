package br.edu.ifba.nutrition.domain.dto.response;

import br.edu.ifba.nutrition.domain.enums.Category;
import br.edu.ifba.nutrition.entity.Tip;

public record TipResponseWithoutCommentDto(Long id, String title, String photo, String text, Category category) {

    public TipResponseWithoutCommentDto(Tip tip){
        this(tip.getId(), tip.getTitle(), tip.getPhoto(), tip.getText(), tip.getCategory());
    }

    public static TipResponseWithoutCommentDto toDto(Tip data) {
        return new TipResponseWithoutCommentDto(data);
    }
    
}
