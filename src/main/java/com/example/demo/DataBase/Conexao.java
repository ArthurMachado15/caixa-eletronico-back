package com.example.demo.DataBase;

import java.sql.*;

public class Conexao {
  private static Conexao instancia = null;
  private Connection ConexaoMySQL;

  public Connection PegarConexao() {
    return ConexaoMySQL;
  }

  public static Conexao PegaInst() {
    if (instancia == null) {
      instancia = new Conexao();
    }
    return instancia;
  }

  public void DestroiInst() {
    instancia = null;
  }

  private Conexao() {
    try {
      ConexaoMySQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/caixaeletronico", "root", "");
    } catch (Exception e) {
      System.out.println("Erro ao conectar ao banco de Dados!" + e.getMessage());
    }
  }
}
