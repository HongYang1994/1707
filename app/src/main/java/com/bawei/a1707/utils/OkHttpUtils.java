package com.bawei.a1707.utils;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpUtils {
    private OkHttpClient okHttpClient;
    private OkHttpCallback okHttpCallback;
    Handler handler = new Handler();
    //单例模式
    //私有化构造函数
    private OkHttpUtils(){

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .build();
    }
    public static OkHttpUtils getInstance(){
        return OkHttpHoder.okHttpUtils;
    }
    private static class OkHttpHoder{
        private static OkHttpUtils okHttpUtils = new OkHttpUtils();
    }

    //get请求
    public void doGet(String url,OkHttpCallback okHttpCallback){
        Request request =new Request.Builder()
                .url(url)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (okHttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okHttpCallback.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (okHttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                okHttpCallback.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    public void dopost(String url, HashMap<String,String> hashMap ,OkHttpCallback okHttpCallback){
        //单独创建构造者
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> map : hashMap.entrySet()){
            builder.add(map.getKey(),map.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (okHttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okHttpCallback.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (okHttpCallback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                okHttpCallback.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    //初始化引用
    public void setOkHttpCallback(OkHttpCallback okHttpCallback){
        this.okHttpCallback = okHttpCallback;
    }
    //1.创建接口
    public interface OkHttpCallback{
        void onSuccess(String string);
        void onError(Throwable throwable);
    }
}
