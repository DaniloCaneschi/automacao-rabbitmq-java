package com.automacaoifet.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuario")
@FilterDef(name = "filtroUsuario", parameters = {@ParamDef(name = "usuarioCodigo", type = "long")})
@Filter(name = "filtroUsuario", condition = "codigo = :usuarioCodigo")
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long codigo;

  @Column(length = 150, unique = true, nullable = false)
  private String email;

  @Column(length = 100, nullable = false)
  private String senha;

  @Column(length = 50, nullable = false)
  private String perfil;

  public UsuarioEntity(long codigo){
    this.codigo = codigo;
  }

  public UsuarioEntity(){
  }

  public UsuarioEntity(String email, String senha, String perfil){
    this.email = email;
    this.senha = senha;
    this.perfil = perfil;
  }

  public long getCodigo(){
    return codigo;
  }

  public void setCodigo(long codigo){
    this.codigo = codigo;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getSenha(){
    return senha;
  }

  public void setSenha(String senha){
    this.senha = senha;
  }

  public String getPerfil(){
    return perfil;
  }

  public void setPerfil(String perfil){
    this.perfil = perfil;
  }

  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UsuarioEntity that = (UsuarioEntity) o;
    return codigo == that.codigo;
  }

  @Override
  public int hashCode(){
    return Objects.hash(codigo);
  }

  @PostPersist
  private void criptografarSenha(){
    this.setSenha(new BCryptPasswordEncoder().encode(this.senha));
  }
}
