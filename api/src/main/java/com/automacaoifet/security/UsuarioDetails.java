package com.automacaoifet.security;

import com.automacaoifet.entity.UsuarioEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 *
 */
public class UsuarioDetails implements UserDetails {

  private UsuarioEntity usuario;

  public UsuarioDetails(UsuarioEntity usuario){
    this.usuario = usuario;
  }

  public UsuarioEntity getUsuario(){
    return usuario;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return Collections.singleton(new SimpleGrantedAuthority(this.usuario.getPerfil()));
  }

  @Override
  public String getPassword(){
    return usuario.getSenha();
  }

  @Override
  public String getUsername(){
    return usuario.getEmail();
  }

  @Override
  public boolean isAccountNonExpired(){
    return true;
  }

  @Override
  public boolean isAccountNonLocked(){
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired(){
    return true;
  }

  @Override
  public boolean isEnabled(){
    return true;
  }
}
