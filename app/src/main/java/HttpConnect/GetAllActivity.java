package HttpConnect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import data.staticData;
import entity.Activity;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class GetAllActivity {
    public static boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetAllActivityServlet";
    public void sendRequestWithOkHttp() {
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

    private void pareseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        staticData.activityList = gson.fromJson(jsonData,new TypeToken<List<Activity>>(){}.getType());
        GetAllActivity.lock = true;
        for(Activity act : staticData.activityList){
            Log.e("activity is ",act.getActivityID()+" "+act.getActivityProfile()+" "+
            act.getEndTime()+" "+act.getLocation()+" "+act.getSponsorID()+" "+act.getType());
        }
    }

    public static void GetActivityInit(){
        GetAllActivity gaa = new GetAllActivity();
        gaa.sendRequestWithOkHttp();
        while(!GetAllActivity.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetAllActivity.lock=false;
    }

    public static String[] HandleTime(String time){
        String times[] = time.split(" ");
        String result[] = new String[2];
        result[0]=times[0];
        String tmp[] = times[1].split(":");
        result[1]=tmp[0]+":"+tmp[1];
        return result;
    }

}
