package com.example.demo.Controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.DataBase.Conexao;
import com.example.demo.Helper.HttpRetorno;
import com.example.demo.Interfaces.IControllerBase;
import com.example.demo.Models.MUsuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CUsuario implements IControllerBase<MUsuario> {

  private Connection ConnectionUser = Conexao.PegaInst().PegarConexao();

  @PostMapping("/usuario/incluir")
  public ResponseEntity<HttpRetorno> Incluir(@RequestBody MUsuario usuario) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(200);
    try {
      String sql = "Insert into USUARIOS (NOME, LOGIN, SENHA) values(?,?,?)";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      qryGravacao.setString(1, usuario.getNome());
      qryGravacao.setString(2, usuario.getLogin());
      qryGravacao.setString(3, usuario.getSenha());
      qryGravacao.executeUpdate();

      ResultSet dados = qryGravacao.getGeneratedKeys();
      if (dados.next()) {
        usuario.setCodigo(dados.getInt(1));
      }
      retorno.setCodigo(200).setRetorno(usuario).setMensagem("ok");
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }

  @PostMapping("/usuario/login")
  public ResponseEntity<HttpRetorno> Login(@RequestBody MUsuario usuario) {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(200);
    try {
      String sql = "Select * from USUARIOS where (LOGIN = ?) and (SENHA = ?)";

      PreparedStatement qryGravacao = ConnectionUser.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      qryGravacao.setString(1, usuario.getLogin());
      qryGravacao.setString(2, usuario.getSenha());

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

  @GetMapping("/usuario/listar")
  public ResponseEntity<HttpRetorno> Listar() {
    HttpRetorno retorno = HttpRetorno.Build().setCodigo(500);
    try {
      Statement qryConsulta = Conexao.PegaInst().PegarConexao().createStatement();
      ResultSet dados = qryConsulta.executeQuery("select * from USUARIOS");

      List<MUsuario> Usuarios = new ArrayList<MUsuario>();
      while (dados.next()) {
        MUsuario user = MUsuario.Build().setCodigo(dados.getInt("CODIGO")).setNome(dados.getString("NOME"))
            .setLogin(dados.getString("LOGIN")).setSenha(dados.getString("SENHA"));

        Usuarios.add(user);
      }
      retorno.setCodigo(200).setRetorno(Usuarios).setMensagem("ok");
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      retorno.setMensagem(e.getMessage());
      return ResponseEntity.status(retorno.getCodigo()).body(retorno);
    }
  }
}
