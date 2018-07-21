package HttpConnect;
/**
 * created by 刘劭荣
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import data.staticData;
import entity.Activity;
import entity.Sponsor;
import okhttp3.Call;
import okhttp3.Response;

public class GetCurrentSponsor {
    public static boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetCurrentSponsorServlet?sponsorID="+staticData.getSponsorID();
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
        staticData.sponsor = gson.fromJson(jsonData,new TypeToken<Sponsor>(){}.getType());
        GetCurrentSponsor.lock = true;
        Log.e("sponsor is:",staticData.sponsor.getSponsorID()+" "+
            staticData.sponsor.getSponsorName()+" "+staticData.sponsor.getHeadProtrait());
    }

    public static void GetSponsorInit(){
        GetCurrentSponsor gcs = new GetCurrentSponsor();
        gcs.sendRequestWithOkHttp();
        while(!GetCurrentSponsor.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetCurrentSponsor.lock=false;
    }
}
