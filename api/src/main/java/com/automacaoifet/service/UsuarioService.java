package com.automacaoifet.service;

import com.automacaoifet.dto.UsuarioDto;
import com.automacaoifet.entity.UsuarioEntity;
import com.automacaoifet.repository.UsuarioRepository;
import com.automacaoifet.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  public UsuarioDto getUsuarioAtual() throws Exception{
    UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<UsuarioEntity> usuario = usuarioRepository.findByEmail(usuarioDetails.getUsername());

    if (usuario.isPresent())
      return new UsuarioDto(usuario.get().getCodigo(), usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getPerfil());

    throw new Exception("Falha ao encontrar o usuário");
  }

  public void salvar(UsuarioDto usuarioDto) throws Exception{
    if (!usuarioRepository.existsByEmail(usuarioDto.email)) {
      UsuarioEntity usuario = new UsuarioEntity(usuarioDto.email, usuarioDto.senha, usuarioDto.perfil);
      usuarioRepository.save(usuario);
    } else {
      throw new Exception("Email já cadastrado");
    }
  }
}
