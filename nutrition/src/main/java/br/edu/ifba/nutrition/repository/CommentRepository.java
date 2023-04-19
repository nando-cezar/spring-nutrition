package br.edu.ifba.nutrition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.nutrition.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{}
