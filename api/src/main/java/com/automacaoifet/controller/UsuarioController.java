package com.automacaoifet.controller;

import com.automacaoifet.dto.Response;
import com.automacaoifet.dto.UsuarioDto;
import com.automacaoifet.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Serve apenas para validar a autenticação
 */

@RestController
@RequestMapping("usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping(value = "atual")
  private ResponseEntity<Response<UsuarioDto>> getUsuarioAtual() throws Exception{
    Response response = new Response<UsuarioDto>();
    UsuarioDto usuarioDto = usuarioService.getUsuarioAtual();
    response.setData(usuarioDto);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  private ResponseEntity<Response> cadastrar(@RequestBody UsuarioDto usuarioDto){
    Response response = new Response<>();

    try {
      usuarioService.salvar(usuarioDto);
    } catch (Exception e) {
      response.getErro().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok().body(response);
  }
}
