package HttpConnect;
/**
 * created by 刘劭荣
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import data.staticData;
import entity.Activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetSearchResult {
    public boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/SearchActivity?keyStr=";

    public List<Activity> activityList=null;
    public void sendRequestWithOkHttp(String str) {
        try {
            str = java.net.URLEncoder.encode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url:",url+str);
        HttpUtil.sendOkHttpRequest(url+str,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error---",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.e("Return---",responseData);
                pareseJSONWithGSON(responseData);
            }
        });
    }

    private void pareseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        activityList = gson.fromJson(jsonData,new TypeToken<List<Activity>>(){}.getType());
        lock = true;
    }

    public static GetSearchResult GetActivityInit(String str){
        GetSearchResult gcs = new GetSearchResult();
        gcs.sendRequestWithOkHttp(str);
        while(!gcs.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gcs.lock=false;
        return gcs;
    }
}
