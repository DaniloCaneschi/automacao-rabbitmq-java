package com.automacaoifet.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CompartilhaComponenteDto {
  @NotNull(message = "Componentes deve ser informado")
  public List<String> componentes;

  @Email(message = "Email inválido")
  @NotBlank(message = "Email deve ser informado")
  public String email;
}
