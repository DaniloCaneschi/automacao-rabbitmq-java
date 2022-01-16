package com.automacaoifet.config;

import com.automacaoifet.dto.Response;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 *
 */
@RestControllerAdvice
public class TrataErros {

  @ExceptionHandler(ImmediateAcknowledgeAmqpException.class)
  private Response handlerImmediateAcknowledgeAmqpException(ImmediateAcknowledgeAmqpException e){
    Response<Object> response = new Response<>();
    response.setErro(Arrays.asList(e.getLocalizedMessage()));
    return response;
  }

  @ExceptionHandler(Exception.class)
  private Response handlerException(Exception e){
    Response<Object> response = new Response<>();
    response.setErro(Arrays.asList(e.getLocalizedMessage()));
    return response;
  }
}
