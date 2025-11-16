package org.openapitools.codegen;

import org.openapitools.codegen.config.CodegenConfigurator;
import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

public class DebugCodegenLauncher {

    public static void disableSslVerification() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
    public static void main(String[] args) throws Exception {
        disableSslVerification();
        CodegenConfigurator configurator = new CodegenConfigurator();
        configurator.setGeneratorName("typescript-angular-custom");
        configurator.setInputSpec("https://localhost:6001/swagger/docs/v1/security");
        configurator.setOutputDir("out");
        configurator.setVerbose(false);
        configurator.setValidateSpec(false);
        configurator.setAdditionalProperties(Map.of("apiName", "Security"));
        DefaultGenerator generator = new DefaultGenerator();

        generator.opts(configurator.toClientOptInput());

        generator.generate();
    }
}
