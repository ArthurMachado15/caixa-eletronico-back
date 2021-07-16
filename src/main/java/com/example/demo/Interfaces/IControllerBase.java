package com.example.demo.Interfaces;

import com.example.demo.Helper.HttpRetorno;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IControllerBase<X> {
  public ResponseEntity<HttpRetorno> Incluir(@RequestBody X corpo);

  public ResponseEntity<HttpRetorno> Listar();
}
