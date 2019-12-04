package io.se7en.apigwtest;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.ClientBuilder;

public class ClientBuilderFactory {
  public static ClientBuilder forHttp() {
    return ClientBuilder.newBuilder();
  }

  public static ClientBuilder forHttps() throws Throwable {
    return ClientBuilder.newBuilder().sslContext(sslContext()).hostnameVerifier(noOpHostnameVerifier());
  }

  private static SSLContext sslContext() throws Throwable {
    SSLContext sslContext = SSLContext.getInstance("TLSv1");
    sslContext.init(null, noOpTrustManagers(), newRandom());
    return sslContext;
  }

  private static TrustManager[] noOpTrustManagers() {
    return new TrustManager[] {
      new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
      }
    };
  }

  private static SecureRandom newRandom() {
    return new SecureRandom();
  }

  private static HostnameVerifier noOpHostnameVerifier() {
    return (hostname, session) -> true;
  }
}
