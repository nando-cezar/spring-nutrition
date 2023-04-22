package br.edu.ifba.nutrition.domain.dto.request;

import java.util.ArrayList;

import br.edu.ifba.nutrition.domain.enums.Category;
import br.edu.ifba.nutrition.entity.Comment;
import br.edu.ifba.nutrition.entity.Tip;

public record TipRequestDto(Long id, String title, String photo, String text, Category category) {
    
    public Tip toEntity(){
        return new Tip(
            title, 
            photo, 
            text, 
            category,
            new ArrayList<Comment>()
            );
    }
}
