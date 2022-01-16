package com.automacaoifet.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "componente_compartilhado")
@FilterDef(name = "filtroUsuario", parameters = {@ParamDef(name = "usuarioCodigo", type = "long")})
@Filter(name = "filtroUsuario", condition = "usuario.codigo = :usuarioCodigo")
public class ComponenteCompartilhadoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long codigo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private UsuarioEntity usuario;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private ComponenteEntity componente;

  public ComponenteCompartilhadoEntity(){
  }

  public ComponenteCompartilhadoEntity(ComponenteEntity componente, UsuarioEntity usuario){
    this.componente = componente;
    this.usuario = usuario;
  }

  public long getCodigo(){
    return codigo;
  }

  public void setUsuario(UsuarioEntity usuario){
    this.usuario = usuario;
  }

  public void setComponente(ComponenteEntity componente){
    this.componente = componente;
  }

  public UsuarioEntity getUsuario(){
    return usuario;
  }

  public ComponenteEntity getComponente(){
    return componente;
  }

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ComponenteCompartilhadoEntity that = (ComponenteCompartilhadoEntity) o;
    return codigo == that.codigo;
  }

  @Override
  public int hashCode(){
    return Objects.hash(codigo);
  }
}
