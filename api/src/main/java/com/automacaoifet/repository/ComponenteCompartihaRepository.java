package com.automacaoifet.repository;

import com.automacaoifet.entity.ComponenteCompartilhadoEntity;
import com.automacaoifet.entity.ComponenteEntity;
import com.automacaoifet.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponenteCompartihaRepository extends JpaRepository<ComponenteCompartilhadoEntity, Long> {
    Optional<ComponenteCompartilhadoEntity> findByComponente_IdentificadorAndUsuario_Email(String identificador, String emailUsuario);

  List<ComponenteCompartilhadoEntity> findAllByUsuario_Codigo(long codigoUsuario);
}
