package com.automacaoifet.consumer;

import com.automacaoifet.constante.TopicoConstante;
import com.automacaoifet.dto.ComponenteDto;
import com.automacaoifet.dto.StatusComponenteDto;
import com.automacaoifet.service.ComponenteService;
import org.json.JSONObject;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponenteConsumer {

  @Autowired
  private ComponenteService componenteService;

  @RabbitListener(queues = TopicoConstante.REGISTRO_ESP)
  private void criarComponente(String componente){
    try {
      JSONObject json = new JSONObject(componente);
      String identificador = json.getString("identificador");

      componenteService.registrar(new ComponenteDto(identificador));
    } catch (Exception e) {
      throw new ImmediateAcknowledgeAmqpException("Falha ao registrar o componente: " + e.getLocalizedMessage());
    }
  }

  @RabbitListener(queues = TopicoConstante.STATUS_ESP)
  private void atualizarComponente(String componente){
    try {
      JSONObject json = new JSONObject(componente);
      String identificador = json.getString("codigo");
      boolean ativo = json.getBoolean("ativo");

      componenteService.atualizarStatus(new StatusComponenteDto(identificador, ativo));
    } catch (Exception e) {
      throw new ImmediateAcknowledgeAmqpException("Falha ao atualizar o status do componente: " + e.getLocalizedMessage());
    }
  }
}
