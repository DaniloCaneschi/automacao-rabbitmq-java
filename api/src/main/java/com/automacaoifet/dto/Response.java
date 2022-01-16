package com.automacaoifet.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Response<T> {
  private T            data;
  private List<String> erro = new ArrayList<>();

  public T getData(){
    return data;
  }

  public void setData(T data){
    this.data = data;
  }

  public List<String> getErro(){
    return erro;
  }

  public void setErro(List<String> erro){
    this.erro = erro;
  }
}
