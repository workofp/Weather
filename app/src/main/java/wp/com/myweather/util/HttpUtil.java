package wp.com.myweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by WP on 2017/7/28.
 * 网络工具类
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //创建OKHttpClient客户端
        OkHttpClient client=new OkHttpClient();
        //创建Request对象
        Request request=new Request.Builder().url(address).build();
        //发起网络请求
        client.newCall(request).enqueue(callback);
    }
}
