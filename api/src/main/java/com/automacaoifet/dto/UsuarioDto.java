package com.automacaoifet.dto;

import com.automacaoifet.constante.PerfilConstantes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioDto {

  public long codigo;

  @Email(message = "Email inválido")
  @NotBlank(message = "Email deve ser informado")
  public String email;

  @NotBlank(message = "Email deve ser informado")
  public String senha;

  public String perfil = PerfilConstantes.ROLE_USER;

  public UsuarioDto(long codigo, String email, String senha, String perfil){
    this.codigo = codigo;
    this.email = email;
    this.senha = senha;
    this.perfil = perfil;
  }
}
