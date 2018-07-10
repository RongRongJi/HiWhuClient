package HttpConnect;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class Upload extends AsyncTask<String,Void,String> {
    File file;
    public Upload(File file){
        this.file = file;
    }
    @Override
    protected String doInBackground(String... strings) {
        return UploadImg.uploadFile(file,strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null){

            Log.e("succeed","上传成功");
        }else{
            Log.e("failed","上传失败");
        }
    }
}
