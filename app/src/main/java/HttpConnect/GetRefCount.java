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
import entity.CommentWithActivity;
import okhttp3.Call;
import okhttp3.Response;

public class GetRefCount {
    public boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetCommentCountServlet?type=1&studentID="+staticData.getStudentID();

    public List<CommentWithActivity> commentCountList=null;
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
        commentCountList = gson.fromJson(jsonData,new TypeToken<List<CommentWithActivity>>(){}.getType());
        lock = true;
    }

    public static GetRefCount GetActivityInit(){
        GetRefCount gcs = new GetRefCount();
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
