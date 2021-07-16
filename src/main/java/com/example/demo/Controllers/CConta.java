package com.example.demo.Controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DataBase.Conexao;
import com.example.demo.Helper.HttpRetorno;
import com.example.demo.Interfaces.IControllerBase;
import com.example.demo.Models.MConta;
import com.example.demo.Models.MMovimentacao;

@RestController
public class CConta implements IControllerBase<MConta> {
  private Connection ConnectionUser = Conexao.PegaInst().PegarConexao();

  @GetMapping("/conta/listar")
  public ResponseEntity<HttpRetorno> Listar() {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {
      Statement qryConsulta = ConnectionUser.createStatement();
      ResultSet dados = qryConsulta.executeQuery("select * from CONTAS");

      List<MConta> Contas = new ArrayList<MConta>();
      while (dados.next()) {
        MConta user = MConta.Build().setCodigo(dados.getInt("CODIGO")).setNumero(dados.getInt("NUMERO"))
            .setSenha(dados.getInt("SENHA")).setStatus(dados.getBoolean("STATUS"))
            .setCodUsuario(dados.getInt("COD_USUARIO")).setSaldo(dados.getFloat("SALDO"));

        Contas.add(user);
      }
      retorno.setMensagem("ok").setRetorno(Contas).setCodigo(200);

      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      retorno.setMensagem(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @PostMapping("/conta/contasUsuario")
  public ResponseEntity<List<MConta>> ListarContasUsuarios(@RequestBody int codigo) {
    try {
      String sql = "Select * from CONTAS where COD_USUARIO = ?";

      PreparedStatement qryConsulta = ConnectionUser.prepareStatement(sql);
      qryConsulta.setInt(1, codigo);

      ResultSet retorno = qryConsulta.executeQuery();

      List<MConta> Contas = new ArrayList<MConta>();
      while (retorno.next()) {
        MConta user = MConta.Build().setCodigo(retorno.getInt("CODIGO")).setNumero(retorno.getInt("NUMERO"))
            .setSenha(retorno.getInt("SENHA")).setStatus(retorno.getBoolean("STATUS"))
            .setCodUsuario(retorno.getInt("COD_USUARIO")).setSaldo(retorno.getFloat("SALDO"));

        Contas.add(user);
      }

      return ResponseEntity.status(200).body(Contas);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @PostMapping("/conta/desativar")
  public ResponseEntity<MConta> Desativar(@RequestBody MConta Conta) {
    try {
      String sql = "Update CONTAS set STATUS = False where CODIGO = ?";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql);
      qryGravacao.setInt(1, Conta.getCodigo());
      qryGravacao.executeUpdate();

      return ResponseEntity.status(200).body(Conta);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(500).body(Conta);
    }
  }

  @PostMapping("/conta/reativar")
  public ResponseEntity<MConta> Reativar(@RequestBody MConta Conta) {
    try {
      String sql = "Update CONTAS set STATUS = True where CODIGO = ?";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql);
      qryGravacao.setInt(1, Conta.getCodigo());
      qryGravacao.executeUpdate();

      return ResponseEntity.status(200).body(Conta);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(500).body(Conta);
    }
  }

  @PostMapping("/conta/login")
  public ResponseEntity<HttpRetorno> Login(@RequestBody MConta usuario) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(200);
    try {
      String sql = "Select * from CONTAS where (CODIGO = ?) and (SENHA = ?)";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      qryGravacao.setInt(1, usuario.getCodigo());
      qryGravacao.setInt(2, usuario.getSenha());

      ResultSet dados = qryGravacao.executeQuery();

      if (dados.next()) {
        retorno.setMensagem("ok");
        retorno.setRetorno(dados.getInt("CODIGO"));
        return ResponseEntity.status(retorno.getCodigo()).body(retorno);
      }
      retorno.setMensagem("fail");
      retorno.setRetorno(0);
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      retorno.setCodigo(500).setMensagem(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @PostMapping("/conta/movimentar")
  public ResponseEntity<HttpRetorno> Movimentar(@RequestBody MMovimentacao Movimento) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {

      String sqlConsulta = "Select SALDO from CONTAS where CODIGO = ?";
      PreparedStatement qryConsulta = ConnectionUser.prepareStatement(sqlConsulta);
      qryConsulta.setInt(1, Movimento.getCodigo());

      float Saldo = 0;
      ResultSet dados = qryConsulta.executeQuery();
      if (dados.next()) {
        Saldo = dados.getFloat(1) + Movimento.getValor();
      }

      String sqlGravacao = "Update CONTAS set SALDO = ? where CODIGO = ?";
      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sqlGravacao);
      qryGravacao.setFloat(1, Saldo);
      qryGravacao.setInt(2, Movimento.getCodigo());
      qryGravacao.executeUpdate();

      retorno.setCodigo(200).setMensagem("ok").setRetorno(dados.getFloat("SALDO") + Movimento.getValor());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      retorno.setMensagem("Erro ao depositar").setErro(e.getMessage());

      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @PostMapping("/conta/incluir")
  public ResponseEntity<HttpRetorno> Incluir(@RequestBody MConta Conta) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {
      String sql = "Insert into CONTAS (NUMERO, SENHA, SALDO, COD_USUARIO) values(?,?,?,?)";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      qryGravacao.setInt(1, Conta.getNumero());
      qryGravacao.setInt(2, Conta.getSenha());
      qryGravacao.setFloat(3, Conta.getSaldo());
      qryGravacao.setInt(4, Conta.getCodUsuario());
      qryGravacao.executeUpdate();

      ResultSet dados = qryGravacao.getGeneratedKeys();
      if (dados.next()) {
        Conta.setCodigo(dados.getInt(1));
        Conta.setStatus(true);
        retorno.setMensagem("ok").setRetorno(Conta).setCodigo(200);
      }

      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

}
