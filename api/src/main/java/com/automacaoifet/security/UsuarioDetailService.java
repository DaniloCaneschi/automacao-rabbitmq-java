package com.automacaoifet.security;

import com.automacaoifet.entity.UsuarioEntity;
import com.automacaoifet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetailService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
    Optional<UsuarioEntity> usuario = usuarioRepository.findByEmail(s);

    if (usuario.isPresent()) {
      return new UsuarioDetails(usuario.get());
    }

    throw new UsernameNotFoundException("Usuário não encontrado");
  }
}
