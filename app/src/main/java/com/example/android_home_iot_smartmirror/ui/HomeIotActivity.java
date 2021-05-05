package com.example.android_home_iot_smartmirror.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_home_iot_smartmirror.R;

public class HomeIotActivity extends AppCompatActivity {
    String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_iot);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingShadowURL");

        // 홈 상태 조회 및 변경
        Button lightShadowBtn = findViewById(R.id.lightShadowBtn);
        lightShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR2";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotActivity.this, "실내등 상태 조회/변경 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotActivity.this, HomeLightActivity.class);
                intent.putExtra("lightShadowURL", urlstr);
                startActivity(intent);

            }
        });

        Button gasShadowBtn = findViewById(R.id.gasShadowBtn);
        gasShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String urlstr = urlStr.concat("/MyMKR2");
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR1";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeIotActivity.this, "실내등 상태 조회/변경 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeIotActivity.this, HomeGasActivity.class);
                intent.putExtra("gasShadowURL", urlstr);
                startActivity(intent);

            }
        });

        Button mainHomeBtn = findViewById(R.id.homeBtn);
        mainHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeIotActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
