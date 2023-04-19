package br.edu.ifba.nutrition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.nutrition.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    public List<Comment> findByTipId(Long id);
    public List<Comment> findByUserId(Long id);
}
