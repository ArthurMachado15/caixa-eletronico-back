package com.example.demo.Models;

public class MUsuario {
  private Integer codigo;
  private String nome;
  private String login;
  private String senha;

  private MUsuario() {
  }

  public static MUsuario Build() {
    return new MUsuario();
  }

  public Integer getCodigo() {
    return codigo;
  }

  public MUsuario setCodigo(Integer codigo) {
    this.codigo = codigo;
    return this;
  }

  public String getLogin() {
    return login;
  }

  public MUsuario setLogin(String login) {
    this.login = login;
    return this;
  }

  public String getNome() {
    return nome;
  }

  public MUsuario setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public String getSenha() {
    return senha;
  }

  public MUsuario setSenha(String senha) {
    this.senha = senha;
    return this;
  }
}
