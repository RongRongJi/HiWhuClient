package HttpConnect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import data.staticData;
import entity.CommentWithActivity;
import entity.Message;
import entity.Stu_apply_activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetAllAppliedActivity {
    public boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetMessageServlet?studentID="+staticData.getStudentID();

    public List<Message> commentCountList=null;
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
        commentCountList = gson.fromJson(jsonData,new TypeToken<List<Message>>(){}.getType());
        lock = true;
    }

    public static GetAllAppliedActivity GetActivityInit(){
        GetAllAppliedActivity gcs = new GetAllAppliedActivity();
        gcs.sendRequestWithOkHttp();
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
