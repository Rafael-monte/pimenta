package com.example.sisave.repositories;

import com.example.sisave.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getUsuarioByEmail(String email);

    Optional<Usuario> getUsuarioByUserId(Long userId);

}
