package Util.HttpClientUtil;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 同步的HTTP请求对象，支持post与get方法以及可设置代理
 */
public class HttpSyncClient {

    private Logger logger = LoggerFactory.getLogger(HttpSyncClient.class);


    private PoolingHttpClientConnectionManager poolConnManager;
    private final int maxTotalPool = 200;// 连接池最大连接数
    private final int maxConPerRoute = 20;// 每个主机的并发最多只有20
    private final int socketTimeout = 2000;// 设置等待数据超时时间5秒钟 根据业务调整
    private final int connectionRequestTimeout = 3000;
    private final int connectTimeout = 1000;// 连接超时

    // 同步httpclient
    private CloseableHttpClient httpClient;

    public HttpSyncClient(){
        try {
            this.httpClient = init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CloseableHttpClient init() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustSelfSignedStrategy())
                    .build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // Increase max total connection to 200
            poolConnManager.setMaxTotal(maxTotalPool);
            // Increase default max connection per route to 20
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
            poolConnManager.setDefaultSocketConfig(socketConfig);

            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
            if(poolConnManager!=null&&poolConnManager.getTotalStats()!=null){
                logger.info("now client pool "+poolConnManager.getTotalStats().toString());
            }
            return httpClient;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}