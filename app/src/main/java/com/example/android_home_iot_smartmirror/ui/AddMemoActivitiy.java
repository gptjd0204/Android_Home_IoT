package com.example.android_home_iot_smartmirror.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android_home_iot_smartmirror.R;
import com.example.android_home_iot_smartmirror.ui.apicall.GetMemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.Timer;
import java.util.TimerTask;


import javax.net.ssl.HttpsURLConnection;

public class AddMemoActivitiy extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Toolbar toolbar;
    String ipAddress = "192.168.0.39";
    //String ipAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("메모 추가 및 삭제"); //타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //TextView mirrorIp = (TextView)findViewById(R.id.mirrorIp);
        //mirrorIp.setText(ipAddress);


        Button updateMemoBtn = findViewById(R.id.updateMemoBtn);
        updateMemoBtn.setOnClickListener(new View.OnClickListener() {
            //EditText ip = (EditText)findViewById(R.id.ipAddress);
            EditText memo = (EditText)findViewById(R.id.memo);



            //String urlStr = "http://" + test1 + ":8080/AddMemo?memoTitle=일정&item=" + test2 +"&level=INFO";
            /*String urlStr = new StringBuilder()
                    .append("http://")
                    .append(test1)
                    .append(":8080/AddMemo?memoTitle=일정&item=")
                    .append(test2)
                    .append("&level=INFO").toString();*/


            @Override
            public void onClick(View view) {
                //String test1 = ip.getText().toString();
                String test2 = memo.getText().toString();
                //urlStr = "http://" + test1 + ":8080/AddMemo?memoTitle=일정&item=" + test2 +"&level=INFO";
                urlStr = "http://" + ipAddress + ":8080/AddMemo?memoTitle=일정&item=" + test2 +"&level=INFO";
                new GetMemo(AddMemoActivitiy.this, urlStr).execute();
                //requestHttpGet(urlStr);
                //Toast.makeText(getApplicationContext(),urlStr, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"메모가 추가되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        Button deleteMemoBtn = findViewById(R.id.deleteMemoBtn);
        deleteMemoBtn.setOnClickListener(new View.OnClickListener() {
            //EditText ip = (EditText)findViewById(R.id.ipAddress);
            //EditText memo = (EditText)findViewById(R.id.memo);



            //String urlStr = "http://" + test1 + ":8080/AddMemo?memoTitle=일정&item=" + test2 +"&level=INFO";
            /*String urlStr = new StringBuilder()
                    .append("http://")
                    .append(test1)
                    .append(":8080/AddMemo?memoTitle=일정&item=")
                    .append(test2)
                    .append("&level=INFO").toString();*/


            @Override
            public void onClick(View view) {
                //String test1 = ip.getText().toString();
                urlStr = "http://" + ipAddress + ":8080/RemoveMemo?memoTitle=일정&numbers&item=1";
                new GetMemo(AddMemoActivitiy.this, urlStr).execute();
                //requestHttpGet(urlStr);
               // Toast.makeText(getApplicationContext(),urlStr, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"메모가 삭제되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

    }
/*
    public String requestHttpGet(String url) {
        try{
            URL reqUrl = new URL(url);
            HttpURLConnection urlConn = (HttpsURLConnection) reqUrl.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept", "/");

            int resCode = urlConn.getResponseCode();
            System.out.println("resCode: " + resCode);
            if(resCode != HttpsURLConnection.HTTP_OK) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String input;
            StringBuffer sb = new StringBuffer();

            while((input = reader.readLine()) != null) {
                sb.append(input);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.memo_ip_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.memo_settings1:
                final EditText txtEdit = new EditText(AddMemoActivitiy.this);
                txtEdit.setText(ipAddress);
                txtEdit.setHint("192.168.0.39");

                AlertDialog.Builder clsBuilder = new AlertDialog.Builder(AddMemoActivitiy.this);
                clsBuilder.setTitle( "미러 IP 설정" );
                clsBuilder.setView( txtEdit );
                clsBuilder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog, int which) {
                                ipAddress = txtEdit.getText().toString();
                                // QQQ: 입력된 문자열 처리하기
                                //TextView mirrorIp = (TextView)findViewById(R.id.mirrorIp);
                                //mirrorIp.setText(ipAddress);
                            }
                        });
                clsBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                clsBuilder.show();
                return true;
            case R.id.memo_settings2:

                AlertDialog.Builder clsBuilder1 = new AlertDialog.Builder(AddMemoActivitiy.this);
                clsBuilder1.setTitle( "현재 미러 IP 주소" );
                clsBuilder1.setMessage(ipAddress);

                clsBuilder1.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick( DialogInterface dialog, int which) {
                            }
                        });
                clsBuilder1.show();
                return true;
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
