package com.example.demo.Helper;

public class HttpRetorno {
  private int codigo;
  private String mensagem;
  private String erro;
  private Object retorno;

  private HttpRetorno() {
  }

  public static HttpRetorno Build() {
    return new HttpRetorno();
  }

  public int getCodigo() {
    return codigo;
  }

  public HttpRetorno setCodigo(int codigo) {
    this.codigo = codigo;
    return this;
  }

  public String getMensagem() {
    return mensagem;
  }

  public HttpRetorno setMensagem(String mensagem) {
    this.mensagem = mensagem;
    return this;
  }

  public String getErro() {
    return erro;
  }

  public HttpRetorno setErro(String erro) {
    this.erro = erro;
    return this;
  }

  public Object getRetorno() {
    return retorno;
  }

  public HttpRetorno setRetorno(Object retorno) {
    this.retorno = retorno;
    return this;
  }
}
