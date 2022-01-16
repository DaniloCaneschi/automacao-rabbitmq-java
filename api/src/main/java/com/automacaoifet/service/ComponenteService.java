package com.automacaoifet.service;

import com.automacaoifet.constante.SistemaConstantes;
import com.automacaoifet.dto.AtivarComponenteDto;
import com.automacaoifet.dto.CompartilhaComponenteDto;
import com.automacaoifet.dto.ComponenteDto;
import com.automacaoifet.dto.StatusComponenteDto;
import com.automacaoifet.entity.ComponenteCompartilhadoEntity;
import com.automacaoifet.entity.ComponenteEntity;
import com.automacaoifet.entity.UsuarioEntity;
import com.automacaoifet.repository.ComponenteCompartihaRepository;
import com.automacaoifet.repository.ComponenteRepository;
import com.automacaoifet.repository.UsuarioRepository;
import com.automacaoifet.utils.FuncoesUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComponenteService {

  private final ComponenteRepository           componenteRepository;
  private final ComponenteCompartihaRepository componenteCompartihaRepository;
  private final UsuarioRepository              usuarioRepository;
  private final RabbitTemplate                 rabbitTemplate;
  private final ObjectMapper                   objectMapper;

  public ComponenteService(ComponenteRepository componenteRepository, ComponenteCompartihaRepository componenteCompartihaRepository, UsuarioRepository usuarioRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper){
    this.componenteRepository = componenteRepository;
    this.componenteCompartihaRepository = componenteCompartihaRepository;
    this.usuarioRepository = usuarioRepository;
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
  }

  public List<ComponenteDto> getAll(){
    List<ComponenteCompartilhadoEntity> componentesCompartilhados = this.componenteCompartihaRepository.findAllByUsuario_Codigo(FuncoesUsuario.getUsuarioAtual());
    List<ComponenteDto> componenteDtos = new ArrayList<>();

    componentesCompartilhados.forEach(componenteEntity -> {
      ComponenteDto componenteDto = new ComponenteDto(componenteEntity.getComponente().getCodigo(),
                                                      componenteEntity.getComponente().getIdentificador(),
                                                      componenteEntity.getComponente().getNome(),
                                                      componenteEntity.getComponente().isAtivo(),
                                                      componenteEntity.getComponente().isModopulso());
      componenteDtos.add(componenteDto);
    });

    return componenteDtos;
  }

  @Transactional(rollbackFor = Exception.class)
  public void registrar(ComponenteDto componenteDto){
    Optional<ComponenteEntity> componente = componenteRepository.findByIdentificador(componenteDto.identificador);

    if (componente.isEmpty()) {
      ComponenteEntity componenteEntity = new ComponenteEntity(componenteDto.identificador, SistemaConstantes.USUARIO_ADMIN);
      ComponenteCompartilhadoEntity componenteCompartilhadoEntity = new ComponenteCompartilhadoEntity(componente.get(), new UsuarioEntity(SistemaConstantes.USUARIO_ADMIN));
      componenteRepository.save(componenteEntity);
      componenteCompartihaRepository.save(componenteCompartilhadoEntity);
    }
  }

  public void atualizar(long codigo, ComponenteDto componenteDto){
    Optional<ComponenteEntity> componente = componenteRepository.findById(codigo);

    if (componente.isPresent()) {
      ComponenteEntity componenteEntity = componente.get();
      componenteEntity.setNome(componenteDto.nome);
      componenteEntity.setModopulso(componenteDto.modopulso);

      componenteRepository.save(componenteEntity);
    }
  }

  public void atualizarStatus(StatusComponenteDto statusComponenteDto){
    Optional<ComponenteEntity> componente = componenteRepository.findByIdentificador(statusComponenteDto.identificador);

    if (componente.isPresent()) {
      ComponenteEntity componenteEntity = componente.get();
      componenteEntity.setAtivo(statusComponenteDto.ativo);

      componenteRepository.save(componenteEntity);
    }
  }

  public void ativar(StatusComponenteDto statusComponenteDto){
    Optional<ComponenteEntity> componente = componenteRepository.findByIdentificador(statusComponenteDto.identificador);

    if (componente.isPresent()) {
      ComponenteEntity componenteEntity = componente.get();

      if (componenteEntity.isModopulso())
        componenteEntity.setAtivo(false);
      else
        componenteEntity.setAtivo(statusComponenteDto.ativo);

      componenteRepository.save(componenteEntity);

      try {
        AtivarComponenteDto ativarComponenteDto = new AtivarComponenteDto(statusComponenteDto.ativo);
        this.rabbitTemplate.convertAndSend(statusComponenteDto.identificador, this.objectMapper.writeValueAsString(ativarComponenteDto));

        if (componente.get().isModopulso()) {
          Thread.sleep(1000);
          ativarComponenteDto.ativar = false;
          this.rabbitTemplate.convertAndSend(statusComponenteDto.identificador, this.objectMapper.writeValueAsString(ativarComponenteDto));
        }
      } catch (JsonProcessingException | InterruptedException e) {
        throw new RuntimeException("Falha ao ativar componente: " + e.getLocalizedMessage());
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public void compartilhar(CompartilhaComponenteDto compartilhaComponenteDto){
    Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(compartilhaComponenteDto.email);

    compartilhaComponenteDto.componentes.forEach(identificador -> {
      Optional<ComponenteEntity> componente = componenteRepository.findByIdentificador(identificador);

      if (componente.isPresent() && usuarioEntity.isPresent()) {
        ComponenteCompartilhadoEntity componenteCompartilhadoEntity = new ComponenteCompartilhadoEntity(componente.get(), usuarioEntity.get());
        componenteCompartihaRepository.save(componenteCompartilhadoEntity);
      } else {
        throw new UsernameNotFoundException("Email não encontrado");
      }
    });
  }

  public void removerCompartilhamento(CompartilhaComponenteDto compartilhaComponenteDto){
    compartilhaComponenteDto.componentes.forEach(identificador -> {
      Optional<ComponenteCompartilhadoEntity> componenteCompartilhado = componenteCompartihaRepository.findByComponente_IdentificadorAndUsuario_Email(identificador, compartilhaComponenteDto.email);
      componenteCompartilhado.ifPresent(compartilhadoEntity -> componenteCompartihaRepository.deleteById(compartilhadoEntity.getCodigo()));
    });
  }
}
