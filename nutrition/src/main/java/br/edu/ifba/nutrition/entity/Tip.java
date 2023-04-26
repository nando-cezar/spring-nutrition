package br.edu.ifba.nutrition.entity;

import java.util.List;

import br.edu.ifba.nutrition.domain.enums.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tips")
public class Tip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String photo;
    private String text;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToMany(
        cascade = CascadeType.REMOVE,
        mappedBy="tip",
        orphanRemoval = true
    )
    private List<Comment> comments;

    public Tip(String title, String photo, String text, Category category, List<Comment> comments) {
        this.title = title;
        this.photo = photo;
        this.text = text;
        this.category = category;
        this.comments = comments;
    }
    
}
