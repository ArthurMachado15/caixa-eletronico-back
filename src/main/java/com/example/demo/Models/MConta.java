package com.example.demo.Models;

public class MConta {
  private Integer codigo;
  private Integer codUsuario;
  private Integer numero;
  private Integer senha;
  private Float saldo;
  private Boolean Status;

  private MConta() {
  }

  public static MConta Build() {
    return new MConta();
  }

  public Boolean getStatus() {
    return Status;
  }

  public MConta setStatus(Boolean status) {
    Status = status;
    return this;
  }

  public Integer getCodUsuario() {
    return codUsuario;
  }

  public MConta setCodUsuario(Integer codUsuario) {
    this.codUsuario = codUsuario;
    return this;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public MConta setCodigo(Integer codigo) {
    this.codigo = codigo;
    return this;
  }

  public Integer getNumero() {
    return numero;
  }

  public MConta setNumero(Integer numero) {
    this.numero = numero;
    return this;
  }

  public Integer getSenha() {
    return senha;
  }

  public MConta setSenha(Integer senha) {
    this.senha = senha;
    return this;
  }

  public Float getSaldo() {
    return saldo;
  }

  public MConta setSaldo(Float saldo) {
    this.saldo = saldo;
    return this;
  }

}
