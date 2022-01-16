package com.automacaoifet.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "componente")
@FilterDef(name = "filtroUsuario", parameters = {@ParamDef(name = "usuarioCodigo", type = "long")})
@Filter(name = "filtroUsuario", condition = "usuario.codigo = :usuarioCodigo")
public class ComponenteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long codigo;

  @Column(length = 30)
  private String nome;

  @Column(length = 20, unique = true, nullable = false)
  private String identificador;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UsuarioEntity usuario;

  @Column(nullable = false)
  private boolean ativo;

  @Column(nullable = false)
  private boolean modopulso;

  public ComponenteEntity(){
  }

  public ComponenteEntity(String identificador, long usuario){
    this.identificador = identificador;
    this.usuario = new UsuarioEntity(usuario);
  }

  public long getCodigo(){
    return codigo;
  }

  public void setCodigo(long codigo){
    this.codigo = codigo;
  }

  public String getIdentificador(){
    return identificador;
  }

  public void setIndentificador(String identificador){
    this.identificador = identificador;
  }

  public boolean isAtivo(){
    return ativo;
  }

  public void setAtivo(boolean ativo){
    this.ativo = ativo;
  }

  public String getNome(){
    return nome;
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public boolean isModopulso(){
    return modopulso;
  }

  public void setModopulso(boolean modopulso){
    this.modopulso = modopulso;
  }

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ComponenteEntity that = (ComponenteEntity) o;
    return codigo == that.codigo;
  }

  @Override
  public int hashCode(){
    return Objects.hash(codigo);
  }
}
