package Util.HttpClientUtil;

/**
 *
 * httpclient 工厂类
 * */
public class HttpClientFactory {

    private static HttpAsyncClient httpAsyncClient = new HttpAsyncClient();

    private static HttpSyncClient httpSyncClient = new HttpSyncClient();

    private static OkClient okClient = new OkClient();

    private HttpClientFactory() {
    }

    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    public static HttpClientFactory getInstance() {

        return httpClientFactory;

    }

    protected HttpAsyncClient getHttpAsyncClientPool() {
        return httpAsyncClient;
    }

    protected HttpSyncClient getHttpSyncClientPool() {
        return httpSyncClient;
    }

    protected OkClient getOkClientPool() {
        return okClient;
    }

}