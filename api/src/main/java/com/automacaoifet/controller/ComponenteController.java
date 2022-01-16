package com.automacaoifet.controller;

import com.automacaoifet.dto.CompartilhaComponenteDto;
import com.automacaoifet.dto.ComponenteDto;
import com.automacaoifet.dto.Response;
import com.automacaoifet.dto.StatusComponenteDto;
import com.automacaoifet.service.ComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */

@RestController
@RequestMapping("componente")
public class ComponenteController {

  @Autowired
  private ComponenteService componenteService;

  @GetMapping
  private ResponseEntity<Response<List<ComponenteDto>>> getAll(){
    Response response = new Response<>();
    List<ComponenteDto> componenteDtos = this.componenteService.getAll();
    response.setData(componenteDtos);

    return ResponseEntity.ok().body(response);
  }

  @PutMapping(value = "/{codigo}")
  private ResponseEntity<Response<List<ComponenteDto>>> atualizar(@PathVariable long codigo,
                                                                  @RequestBody ComponenteDto componenteDto, BindingResult result)
  {
    Response response = new Response<>();

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> response.getErro().add(objectError.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.componenteService.atualizar(codigo, componenteDto);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "ativar")
  private ResponseEntity<Response> ativar(@RequestBody StatusComponenteDto statusComponenteDto, BindingResult result){
    Response response = new Response<>();

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> response.getErro().add(objectError.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.componenteService.ativar(statusComponenteDto);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "compartilhar")
  private ResponseEntity<Response> compartilhar(@RequestBody CompartilhaComponenteDto compartilhaComponenteDto, BindingResult result){
    Response response = new Response<>();

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> response.getErro().add(objectError.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.componenteService.compartilhar(compartilhaComponenteDto);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "removercompartilhamento")
  private ResponseEntity<Response> removerCompartilhamento(@RequestBody CompartilhaComponenteDto compartilhaComponenteDto, BindingResult result){
    Response response = new Response<>();

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> response.getErro().add(objectError.getDefaultMessage()));
      return ResponseEntity.badRequest().body(response);
    }

    this.componenteService.removerCompartilhamento(compartilhaComponenteDto);
    return ResponseEntity.ok(response);
  }
}
