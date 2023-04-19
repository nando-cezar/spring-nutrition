package br.edu.ifba.nutrition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.nutrition.entity.Tip;

public interface TipRepository extends JpaRepository<Tip, Long>{
    public List<Tip> findByTitleContains(String title);
}
