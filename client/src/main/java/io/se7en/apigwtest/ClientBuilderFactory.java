package io.se7en.apigwtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStore.LoadStoreParameter;

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
    return KeyStore.getInstance(keyStoreFile(), keyStoreParameter());
  }

  private static File keyStoreFile() throws FileNotFoundException {
    File file = new File("api-gateway-test-keystore");
    if (!file.exists())
      throw new FileNotFoundException("KeyStore (" + file.getAbsolutePath() + ") not found.");
    return file;
  }

  private static LoadStoreParameter keyStoreParameter() {
    return () -> new KeyStore.PasswordProtection("apigwtest".toCharArray());
  }

  private static HostnameVerifier noOpHostnameVerifier() {
    return (hostname, session) -> true;
  }
}
