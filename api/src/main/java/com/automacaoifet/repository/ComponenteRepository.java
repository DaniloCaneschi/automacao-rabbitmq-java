package com.automacaoifet.repository;

import com.automacaoifet.entity.ComponenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComponenteRepository extends JpaRepository<ComponenteEntity, Long> {
  Optional<ComponenteEntity> findByIdentificador(String identificador);
}
