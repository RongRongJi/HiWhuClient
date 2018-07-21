package HttpConnect;
/**
 * created by 刘劭荣
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import data.staticData;
import entity.Activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetCurrentActivity {
    public static boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetCurrentActivityServlet?activityID=";
    public void sendRequestWithOkHttp() throws IOException{
        HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
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

    private void pareseJSONWithGSON(String jsonData) throws IOException{
        Gson gson = new Gson();
        staticData.activity = gson.fromJson(jsonData,new TypeToken<Activity>(){}.getType());
        if(staticData.activity==null) throw new IOException();
        GetCurrentActivity.lock = true;
        Log.e("activity is:",staticData.activity.getActivityID()+" "+
                staticData.activity.getSponsorID()+" "+staticData.activity.getImage());
    }

    public static void GetActivityInit(String activityID) throws IOException{
        GetCurrentActivity gcs = new GetCurrentActivity();
        gcs.url += activityID;
        gcs.sendRequestWithOkHttp();
        while(!GetCurrentActivity.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetCurrentActivity.lock=false;
    }
}
