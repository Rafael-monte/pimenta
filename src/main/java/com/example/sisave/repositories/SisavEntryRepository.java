package com.example.sisave.repositories;

import com.example.sisave.models.SisavEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SisavEntryRepository extends JpaRepository<SisavEntryModel, Long> {
    List<SisavEntryModel> findByUsuarioUserId(Long user_id);
}
