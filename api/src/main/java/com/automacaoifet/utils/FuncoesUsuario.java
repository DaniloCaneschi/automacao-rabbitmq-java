package com.automacaoifet.utils;

import com.automacaoifet.security.UsuarioDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 */
public class FuncoesUsuario {
  public static long getUsuarioAtual(){
    UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return usuarioDetails.getUsuario().getCodigo();
  }
}
