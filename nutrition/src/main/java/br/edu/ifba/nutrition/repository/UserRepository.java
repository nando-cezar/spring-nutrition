package br.edu.ifba.nutrition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.nutrition.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
