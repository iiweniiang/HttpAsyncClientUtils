package Test;

import Util.HttpClientUtil.AsyncHttpClientCallback;
import Util.HttpClientUtil.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.util.ArrayList;
import java.util.List;

/**
 * http client 使用
 * */
public class HttClientUseDemo extends HttpClientUtil {

    public static void main(String[] args) {


        new HttClientUseDemo().getConfCall();
    }

    public void getConfCall() {

        String url = "http://127.0.0.1:8080/cloudai-web/app/macList";

        String urlParams = "uuid=P1QRM7ZS3Q";

        JSONObject json = new JSONObject();
        json.put("uuid","P1QRM7ZS3Q");

        List<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair("uuid","P1QRM7ZS3Q"));

        list.add(new BasicNameValuePair("onoff","1"));
//        String result = HttpClientUtil.doPost(url,json.toString());

        try {

//            httpSyncPost(url, json.toString());

//            httpSyncGet("http://127.0.0.1:8080/cloudai-web/rpc/lightOnOff1",list);
            httpAsyncPost(url, json.toString(), urlParams,  new AsyncHttpClientCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}