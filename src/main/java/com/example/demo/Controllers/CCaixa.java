package com.example.demo.Controllers;

import java.sql.*;

import com.example.demo.DataBase.Conexao;
import com.example.demo.Helper.HttpRetorno;
import com.example.demo.Interfaces.IControllerBase;
import com.example.demo.Models.MCaixa;
import com.example.demo.Models.MMovimentacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CCaixa implements IControllerBase<MCaixa> {
  private Connection ConnectionUser = Conexao.PegaInst().PegarConexao();

  @PostMapping("/caixa/obter")
  public ResponseEntity<HttpRetorno> Obter(@RequestBody int Codigo) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {

      String sqlConsulta = "Select * from CAIXA where CODIGO = ?";
      PreparedStatement qryConsulta = ConnectionUser.prepareStatement(sqlConsulta);
      qryConsulta.setInt(1, Codigo);

      ResultSet dados = qryConsulta.executeQuery();
      if (dados.next()) {
        MCaixa caixa = MCaixa.Build().setCodigo(dados.getInt("CODIGO")).setNome(dados.getString("NOME"))
            .setTotal(dados.getFloat("TOTAL"));

        retorno.setCodigo(200).setMensagem("ok").setRetorno(caixa);
        return ResponseEntity.status(retorno.getCodigo()).body(retorno);
      }

      retorno.setCodigo(200).setMensagem("fail").setRetorno(null);
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      retorno.setMensagem("Erro ao pegar caixa").setErro(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @PostMapping("/caixa/total")
  public ResponseEntity<HttpRetorno> Incluir(@RequestBody MMovimentacao Movimento) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {
      String sqlConsulta = "Select TOTAL from CAIXA where CODIGO = ?";
      PreparedStatement qryConsulta = ConnectionUser.prepareStatement(sqlConsulta);
      qryConsulta.setInt(1, Movimento.getCodigo());

      float Total = 0;
      ResultSet dados = qryConsulta.executeQuery();
      if (dados.next()) {
        Total = dados.getFloat(1) + Movimento.getValor();
      }
      String sqlGravacao = "Update CAIXA set TOTAL = ? where CODIGO = ?";
      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sqlGravacao);
      qryGravacao.setFloat(1, Total);
      qryGravacao.setInt(2, Movimento.getCodigo());
      qryGravacao.executeUpdate();

      retorno.setMensagem("ok").setCodigo(200).setRetorno(Total);
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      retorno.setErro(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @Override
  public ResponseEntity<HttpRetorno> Incluir(MCaixa corpo) {

    return null;
  }

  @Override
  public ResponseEntity<HttpRetorno> Listar() {

    return null;
  }

}
