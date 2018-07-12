package HttpConnect;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.staticData;
import entity.Activity;
import okhttp3.Call;
import okhttp3.Response;

public class GetActivityBySponsorID {
    public static boolean lock = false;//线程锁
    public List<Activity> activityList = null;//主办方所有活动列表
    public List<Activity> registerAcitivtylist = null;//需要审核的活动列表

    private String url = staticData.getUrl()+"/GetActivityBySponsorIDServlet?sponsorID=";
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
        activityList = gson.fromJson(jsonData,new TypeToken<List<Activity>>(){}.getType());
        GetActivityBySponsorID.lock = true;

    }

    public static GetActivityBySponsorID GetActivityInit(String sponsorID){
        GetActivityBySponsorID gcs = new GetActivityBySponsorID();
        gcs.url += sponsorID;
        gcs.sendRequestWithOkHttp();
        while(!GetActivityBySponsorID.lock){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GetActivityBySponsorID.lock=false;
        gcs.SortTheActivity();
        return gcs;
    }

    public void SortTheActivity(){
        if(registerAcitivtylist==null) registerAcitivtylist=new ArrayList<Activity>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time=df.format(new Date());
        for(Activity a : activityList){
            if(compare(time,a.getRegistrationEndTime())){
                registerAcitivtylist.add(a);
            }
        }
    }

    public static boolean compare(String time1,String time2){
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        java.util.Date a= null;
        java.util.Date b= null;
        try {
            a = sdf.parse(time1);
            b=sdf.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(a.before(b))
            return true;
        else
            return false;
		/*
		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
		if(a.getTime()-b.getTime()<0)
			return true;
		else
			return false;
		*/
    }
}
