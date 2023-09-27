package com.example.sisave.repositories;

import com.example.sisave.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> getUsuarioByEmail(String email);

    Optional<UsuarioModel> getUsuarioByUserId(Long userId);

}
