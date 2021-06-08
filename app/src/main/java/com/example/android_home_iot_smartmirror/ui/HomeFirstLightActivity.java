package com.example.android_home_iot_smartmirror.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android_home_iot_smartmirror.R;
import com.example.android_home_iot_smartmirror.ui.apicall.GetLightShadow;
import com.example.android_home_iot_smartmirror.ui.apicall.UpdateShadow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFirstLightActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_light);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("firstLightShadowURL");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("1번 실내등"); //타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               new GetLightShadow(HomeFirstLightActivity.this, urlStr).execute();
                           }
                       },
                0,2000);

        // 앱에서 충격 감지 실행 (아두이노 디바이스 제어)
        Button updateRunBtn = findViewById(R.id.updateRunBtn);
        updateRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_light = "ON";

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String light_input = edit_light;
                    if (light_input != null && !light_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "SERVO_STATE");
                        tag1.put("tagValue", light_input);

                        jsonArray.put(tag1);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 ) {
                    new UpdateShadow(HomeFirstLightActivity.this, urlStr).execute(payload);
                    Toast.makeText(HomeFirstLightActivity.this,"실내등을 켭니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(HomeFirstLightActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 앱에서 충격 감지 중지 (아두이노 디바이스 제어)
        Button updateStopBtn = findViewById(R.id.updateStopBtn);
        updateStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_light = "OFF";

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String light_input = edit_light;
                    if (light_input != null && !light_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "SERVO_STATE");
                        tag1.put("tagValue", light_input);

                        jsonArray.put(tag1);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 ) {
                    new UpdateShadow(HomeFirstLightActivity.this, urlStr).execute(payload);
                    Toast.makeText(getApplicationContext(),"실내등을 끕니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(HomeFirstLightActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });

        Button homeIotBtn = findViewById(R.id.homeIotBtn);
        homeIotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();

                Intent intent = new Intent(HomeFirstLightActivity.this, HomeIotActivity.class);
                startActivity(intent);
            }
        });

        Button mainHomeBtn = findViewById(R.id.homeBtn);
        mainHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();

                Intent intent = new Intent(HomeFirstLightActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void clearTextView() {
        TextView reported_light = findViewById(R.id.reported_light);
        reported_light.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings1:
                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                if (timer != null)
                    timer.cancel();
                clearTextView();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}