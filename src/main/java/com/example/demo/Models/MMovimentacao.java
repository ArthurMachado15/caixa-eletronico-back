package com.example.demo.Models;

public class MMovimentacao {
  private Float valor;
  private Integer codigo;

  private MMovimentacao() {
  }

  public static MMovimentacao Build() {
    return new MMovimentacao();
  }

  public Float getValor() {
    return valor;
  }

  public MMovimentacao setValor(Float valor) {
    this.valor = valor;
    return this;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public MMovimentacao setCodigo(Integer codConta) {
    this.codigo = codConta;
    return this;
  }
}
