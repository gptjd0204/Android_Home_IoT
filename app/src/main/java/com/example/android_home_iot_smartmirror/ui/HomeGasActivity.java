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
import com.example.android_home_iot_smartmirror.ui.apicall.GetGasShadow;
import com.example.android_home_iot_smartmirror.ui.apicall.UpdateShadow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class HomeGasActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Toolbar toolbar;
    //Button startGetBtn;
    //Button stopGetBtn;
    //Button homeGetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("gasShadowURL");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); //타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               new GetGasShadow(HomeGasActivity.this, urlStr).execute();
                           }
                       },
                0,2000);
        /*
        startGetBtn = findViewById(R.id.startGetBtn);
        startGetBtn.setEnabled(true);
        startGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       new GetLightShadow(HomeLightActivity.this, urlStr).execute();
                                   }
                               },
                        0,2000);

                startGetBtn.setEnabled(false);
                stopGetBtn.setEnabled(true);
            }
        });

        stopGetBtn = findViewById(R.id.stopGetBtn);
        stopGetBtn.setEnabled(false);
        stopGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();
                startGetBtn.setEnabled(true);
                stopGetBtn.setEnabled(false);
            }
        });
*/
        // 앱에서 충격 감지 실행 (아두이노 디바이스 제어)
        Button updateRunBtn = findViewById(R.id.updateRunBtn);
        updateRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_gas = "ON";

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String gas_input = edit_gas;
                    if (gas_input != null && !gas_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "SERVO_STATE");
                        tag1.put("tagValue", gas_input);

                        jsonArray.put(tag1);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 ) {
                    new UpdateShadow(HomeGasActivity.this, urlStr).execute(payload);
                    Toast.makeText(HomeGasActivity.this,"가스밸브를 켭니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(HomeGasActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });

        // 앱에서 충격 감지 중지 (아두이노 디바이스 제어)
        Button updateStopBtn = findViewById(R.id.updateStopBtn);
        updateStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_gas = "OFF";

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String gas_input = edit_gas;
                    if (gas_input != null && !gas_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "SERVO_STATE");
                        tag1.put("tagValue", gas_input);

                        jsonArray.put(tag1);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 ) {
                    new UpdateShadow(HomeGasActivity.this, urlStr).execute(payload);
                    Toast.makeText(getApplicationContext(),"가스밸브를 끕니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(HomeGasActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });

        Button homeIotBtn = findViewById(R.id.homeIotBtn);
        homeIotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();

                Intent intent = new Intent(HomeGasActivity.this, HomeIotActivity.class);
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

                Intent intent = new Intent(HomeGasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void clearTextView() {
        TextView reported_gas = findViewById(R.id.reported_gas);
        reported_gas.setText("");
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
