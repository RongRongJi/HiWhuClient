package HttpConnect;
/**
 * created by 刘劭荣
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import data.staticData;
import entity.Activity;
import entity.Stu_collect_activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetCurrentCollection {
    public static boolean lock = false;//线程锁
    public static List<String> list= null;
    private String url = staticData.getUrl()+"/GetCurrentCollectionServlet?studentID=";
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
        if(list==null) list = new ArrayList<String>();
        List<Stu_collect_activity> sList = gson.fromJson(jsonData,new TypeToken<List<Stu_collect_activity>>(){}.getType());
        for(Stu_collect_activity tmp : sList){
            String str = tmp.getActivityID();
            list.add(str);
        }
        GetCurrentCollection.lock = true;
    }

    public static void GetCollectionInit(String studentID){
        GetCurrentCollection gcs = new GetCurrentCollection();
        gcs.url += studentID;
        gcs.sendRequestWithOkHttp();
        while(!GetCurrentCollection.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetCurrentCollection.lock=false;
    }

    public static void addStarList(String activityId){
        list.add(activityId);
    }

    public static void removeStarList(String activityId){
        list.remove(activityId);
    }

    public static boolean isStar(String activityId){
        return (list!=null) && (list.contains(activityId));
    }
}
