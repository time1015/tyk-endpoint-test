package com.pccw.tyk.apitest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.client.ClientBuilder;

public class ClientBuilderFactory {
  public static ClientBuilder forHttp() {
    return ClientBuilder.newBuilder();
  }

  public static ClientBuilder forHttps() throws Throwable {
    return ClientBuilder.newBuilder().trustStore(trustStore()).hostnameVerifier(noOpHostnameVerifier());
  }

  private static KeyStore trustStore() throws Throwable {
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(keyStoreFileStream(), keyStorePassword());
    return keyStore;
  }

  private static FileInputStream keyStoreFileStream() throws FileNotFoundException {
    File file = new File("api-gateway-test-keystore");
    if (!file.exists())
      throw new FileNotFoundException("KeyStore (" + file.getAbsolutePath() + ") not found.");
    return new FileInputStream(file);
  }

  private static char[] keyStorePassword() {
    return "apigwtest".toCharArray();
  }

  private static HostnameVerifier noOpHostnameVerifier() {
    return (hostname, session) -> true;
  }
}
