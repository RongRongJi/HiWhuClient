package HttpConnect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.staticData;
import entity.Comment;
import entity.CommentAndReply;
import entity.Stu_collect_activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetCommentAndReply {
    public boolean lock = false;//线程锁
    public List<CommentAndReply> sList;
    private String url = staticData.getUrl()+"/GetCommentByActivityIDServlet?activityID=";
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
        sList = gson.fromJson(jsonData,new TypeToken<List<CommentAndReply>>(){}.getType());
        lock = true;
    }

    public static GetCommentAndReply GetCollectionInit(String ActivityID){
        GetCommentAndReply gcs = new GetCommentAndReply();
        gcs.url += ActivityID;
        Log.e("url",gcs.url);
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
