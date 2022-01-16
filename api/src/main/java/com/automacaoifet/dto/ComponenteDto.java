package com.automacaoifet.dto;

import javax.validation.constraints.NotBlank;

public class ComponenteDto {
  public long codigo;

  public String identificador;

  @NotBlank(message = "Nome não pode estar vazio")
  public String nome;

  public boolean ativo;

  public boolean modopulso;

  public ComponenteDto(){
  }

  public ComponenteDto(String identificador){
    this.identificador = identificador;
  }

  public ComponenteDto(long codigo, String identificador, String nome, boolean ativo, boolean modopulso){
    this.codigo = codigo;
    this.identificador = identificador;
    this.nome = nome;
    this.ativo = ativo;
    this.modopulso = modopulso;
  }
}
