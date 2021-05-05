package com.example.android_home_iot_smartmirror.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_home_iot_smartmirror.R;

public class HomeIotUsageActivity extends AppCompatActivity {
    String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_iot_usage);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingUsageURL");

        // 실내등 로그 조회
        Button lightLogBtn = findViewById(R.id.lightLogBtn);
        lightLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR2/log";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotUsageActivity.this, "실내등 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotUsageActivity.this, HomeLightLogActivity.class);
                intent.putExtra("getLightLogsURL", urlstr);
                startActivity(intent);
            }
        });

        // 실내등 사용량 조회
        Button lightUsageBtn = findViewById(R.id.lightUsageBtn);
        lightUsageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR2/log";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotUsageActivity.this, "실내등 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotUsageActivity.this, HomeLightUsageActivity.class);
                intent.putExtra("getLightLogsURL", urlstr);
                startActivity(intent);
            }
        });

        // 가스 로그 조회
        Button gasLogBtn = findViewById(R.id.gasLogBtn);
        gasLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR1/log";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotUsageActivity.this, "가스밸브 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotUsageActivity.this, HomeGasLogActivity.class);
                intent.putExtra("getGasLogsURL", urlstr);
                startActivity(intent);
            }
        });

        // 가스 사용량 조회
        Button gasUsageBtn = findViewById(R.id.gasUsageBtn);
        gasUsageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR1/log";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotUsageActivity.this, "가스밸브 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotUsageActivity.this, HomeGasUsageActivity.class);
                intent.putExtra("getGasLogsURL", urlstr);
                startActivity(intent);
            }
        });

    }
}
