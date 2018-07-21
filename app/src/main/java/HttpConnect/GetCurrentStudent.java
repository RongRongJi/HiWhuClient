package HttpConnect;
/**
 * created by 刘劭荣
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import data.staticData;
import entity.Student;
import okhttp3.Call;
import okhttp3.Response;

public class GetCurrentStudent {
    public static boolean lock = false;//线程锁
    private String url = staticData.getUrl()+"/GetCurrentStudentServlet?studentID="+staticData.getStudentID();
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
        staticData.student = gson.fromJson(jsonData,new TypeToken<Student>(){}.getType());
        GetCurrentStudent.lock = true;
        Log.e("student is:",staticData.student.getStudentID()+" "+
                staticData.student.getHeadProtrait()+" "+staticData.student.getUserName());
    }

    public static void GetStudentInit(){
        GetCurrentStudent gcs = new GetCurrentStudent();
        gcs.sendRequestWithOkHttp();
        while(!GetCurrentStudent.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetCurrentStudent.lock=false;
    }
}
