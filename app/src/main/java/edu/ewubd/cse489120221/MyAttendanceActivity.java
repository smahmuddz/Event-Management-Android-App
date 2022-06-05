package edu.ewubd.cse489120221;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MyAttendanceActivity extends Activity {
    private WebView webView;
    private String URL="http:/www.muthosoft.com/univ/attendance/report.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);
        webView =findViewById(R.id.webview);
        String[] keys={"CSE489-Lab","year","semester","course","section","sid"};
        String[] values={"true","2022","1","CSE489","1","2019360112"};
        httpRequest(keys,values);
    }
    @SuppressLint("StaticFieldLeak")
    private void httpRequest(final String[] keys, String[] values) {
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... param) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for (int i = 0; i < keys.length; i++) {
                        params.add(new BasicNameValuePair(keys[i], values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", params);
                    return data;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){

                if(data!=null)
                {
                    try{
                        webView.loadDataWithBaseURL(null,data,"text/html","UTF-8",null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }
}