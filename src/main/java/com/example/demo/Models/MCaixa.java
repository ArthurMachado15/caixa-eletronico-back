package com.example.demo.Models;

public class MCaixa {
  private int Codigo;
  private String Nome;
  private float Total;

  private MCaixa() {
  }

  public static MCaixa Build() {
    return new MCaixa();
  }

  public int getCodigo() {
    return Codigo;
  }

  public MCaixa setCodigo(int codigo) {
    Codigo = codigo;
    return this;
  }

  public String getNome() {
    return Nome;
  }

  public MCaixa setNome(String nome) {
    Nome = nome;
    return this;
  }

  public float getTotal() {
    return Total;
  }

  public MCaixa setTotal(float total) {
    Total = total;
    return this;
  }

}