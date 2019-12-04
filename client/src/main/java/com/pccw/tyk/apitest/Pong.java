package com.pccw.tyk.apitest;

public class Pong {
  private String response;
  private String message;
  private int nonce;

  public Pong() {
    this("<no response>", "<no message>", -1);
  }

  public Pong(String response, String message, int nonce) {
    setResponse(response);
    setMessage(message);
    setNonce(nonce);
  }

  public String getResponse() {
    return response;
  }

  public String getMessage() {
    return message;
  }

  public int getNonce() {
    return nonce;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setNonce(int nonce) {
    this.nonce = nonce;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("[")
      .append("Pong")
      .append(" ")
      .append("response")
      .append("=")
      .append(response)
      .append(" ")
      .append("message")
      .append("=")
      .append("<(")
      .append(message)
      .append(")>")
      .append(" ")
      .append("nonce")
      .append("=")
      .append(nonce)
      .append("]")
      .toString();
  }
}
