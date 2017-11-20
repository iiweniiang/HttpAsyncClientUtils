package Test;

import Util.HttpClientUtil.AsyncHttpClientCallback;
import Util.HttpClientUtil.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * http client 使用
 * */
public class HttpClientDemo extends HttpClientUtil {

    public static void main(String[] args) {
        new HttpClientDemo().getResult();
    }

    public void getResult() {

        String url = "http://127.0.0.1:8080/test";
        String urlParams = "id=1";
        JSONObject json = new JSONObject();
        json.put("id","1");
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("id","1"));

        try {
            httpSyncPost(url,list);
            httpSyncPost(url, json.toString());
            httpSyncGet(url,list);
            httpSyncGet(url,urlParams);

            httpAsyncPost(url, json.toString(), urlParams,  new AsyncHttpClientCallback());
            httpAsyncPost(url, list, list,  new AsyncHttpClientCallback());
            httpAsyncGet(url, urlParams,  new AsyncHttpClientCallback());
            httpAsyncGet(url, list,  new AsyncHttpClientCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}