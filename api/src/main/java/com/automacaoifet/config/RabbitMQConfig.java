package com.automacaoifet.config;

import com.automacaoifet.constante.TopicoConstante;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitMQConfig {

  private final AmqpAdmin amqpAdmin;

  private static final String EXCHANGE = "amq.topic";

  public RabbitMQConfig(AmqpAdmin amqpAdmin){
    this.amqpAdmin = amqpAdmin;
  }

  private Queue criarFila(String nome){
    return new Queue(nome);
  }

  private TopicExchange criarTopicExchange(String nome){
    return new TopicExchange(nome, true, false);
  }

  private Binding binding(TopicExchange topicExchange, Queue queue){
    return BindingBuilder.bind(queue).to(topicExchange).with(queue.getName());
  }

  private void adicionar(String nomeFila){
    TopicExchange exchange = criarTopicExchange(EXCHANGE);
    Queue fila = criarFila(nomeFila);

    Binding binding = binding(exchange, fila);

    amqpAdmin.declareExchange(exchange);
    amqpAdmin.declareQueue(fila);
    amqpAdmin.declareBinding(binding);
  }

  @PostConstruct
  private void criarFilas() {
    adicionar(TopicoConstante.REGISTRO_ESP);
    adicionar(TopicoConstante.STATUS_ESP);
  }
}
