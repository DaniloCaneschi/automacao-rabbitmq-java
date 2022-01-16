package com.automacaoifet.dto;

public class StatusComponenteDto {
  public String identificador;
  public boolean ativo;

  public StatusComponenteDto(String identificador, boolean ativo){
    this.identificador = identificador;
    this.ativo = ativo;
  }
}
