package com.example.android_home_iot_smartmirror.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_home_iot_smartmirror.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 홈 상태 조회 및 변경
        Button thingShadowBtn = findViewById(R.id.thingShadowBtn);
        thingShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "홈 상태 조회/변경 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, HomeIotActivity.class);
                intent.putExtra("thingShadowURL", urlstr);
                startActivity(intent);

            }
        });

        Button thingUsageBtn = findViewById(R.id.thingUsageBtn);
        thingUsageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices";
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "홈 상태 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, HomeIotUsageActivity.class);
                intent.putExtra("thingUsageURL", urlstr);
                startActivity(intent);

            }
        });

    }
}