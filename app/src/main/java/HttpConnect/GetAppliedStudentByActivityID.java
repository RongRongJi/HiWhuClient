package HttpConnect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import data.staticData;
import entity.Student;
import okhttp3.Call;
import okhttp3.Response;

public class GetAppliedStudentByActivityID {
    public boolean lock = false;//线程锁
    public List<Student> applylist = null;//该活动所有申请列表

    private String url = staticData.getUrl()+"/GetAppliedStudentByActivityIDServlet?activityID=";
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
        applylist = gson.fromJson(jsonData,new TypeToken<List<Student>>(){}.getType());
        lock = true;

    }

    public static GetAppliedStudentByActivityID GetApplyInit(String activityID){
        GetAppliedStudentByActivityID gcs = new GetAppliedStudentByActivityID();
        gcs.url += activityID;
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
