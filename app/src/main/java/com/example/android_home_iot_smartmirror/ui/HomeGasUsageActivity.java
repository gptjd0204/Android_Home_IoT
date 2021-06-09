package com.example.android_home_iot_smartmirror.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android_home_iot_smartmirror.R;
import com.example.android_home_iot_smartmirror.ui.apicall.GetGasUsage;

import java.util.Calendar;

public class HomeGasUsageActivity extends AppCompatActivity {
    String getGasLogsURL;
    Toolbar toolbar;

    private TextView textView_Date1;
    private TextView textView_Date2;
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    final static String TAG = "AndroidAPITest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_usage);

        //textView_Date1 = (TextView)findViewById(R.id.textView_gas_date1);
       // textView_Date2 = (TextView)findViewById(R.id.textView_gas_date2);
       // textView_Date1.setVisibility(View.INVISIBLE);
        //textView_Date2.setVisibility(View.INVISIBLE);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("가스 사용량 조회"); //타이틀 없음
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        Intent intent = getIntent();
        getGasLogsURL = intent.getStringExtra("getGasLogsURL");
        Log.i(TAG, "getGasLogsURL="+getGasLogsURL);

        // 일별 실내등 사용 시간 로그 조회
        Button gasDayBtn = findViewById(R.id.gas_day_button);
        gasDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackMethod = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {

                        // 사용자가 입력한 조회 시작 시간과 조회 종료 시간 표시
                        textView_Date1 = (TextView)findViewById(R.id.textView_gas_date1);
                        textView_Date2 = (TextView)findViewById(R.id.textView_gas_date2);
                        textView_Date1.setText(String.format("%d-%d-%d ", year ,monthOfYear+1,dayOfMonth)+"00:00:00");
                        textView_Date2.setText(String.format("%d-%d-%d ", year ,monthOfYear+1,dayOfMonth)+ "23:59:59");

                        //textView_Date = (TextView)findViewById(R.id.usage_text);
                        //textView_Date.setText(String.format("%d년 %d월 %d일 가스 사용량", year ,monthOfYear+1,dayOfMonth));
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(HomeGasUsageActivity.this, callbackMethod, year, month, day);

                dialog.show();
            }
        });

        // 월별 실내등 사용 시간 로그 조회
        Button gasMonthBtn = findViewById(R.id.gas_month_button);
        gasMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        Calendar calendar= Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear-1);

                        // 사용자가 입력한 조회 시작 시간과 조회 종료 시간 표시
                        textView_Date1 = (TextView)findViewById(R.id.textView_gas_date1);
                        textView_Date2 = (TextView)findViewById(R.id.textView_gas_date2);
                        textView_Date1.setText(String.format("%d-%d-%d ", year,monthOfYear, dayOfMonth)+"00:00:00");
                        textView_Date2.setText(String.format("%d-%d-%d ", year,monthOfYear, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))+ "23:59:59");

                       // textView_Date = (TextView)findViewById(R.id.usage_text);
                       // textView_Date.setText(String.format("%d년 %d월 가스 사용량", year ,monthOfYear));
                    }
                };

                MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
                pd.setListener(d);
                pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
            }
        });


        // 실내등 사용 시간 로그 조회 시작
        Button start = findViewById(R.id.gas_log_start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetGasUsage(HomeGasUsageActivity.this,getGasLogsURL).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_iot_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_setting:
                String urlstr = "https://peaypv7rkd.execute-api.ap-northeast-2.amazonaws.com/homeIoT/devices/MyMKR1/gasvalvelog";
                /*
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(HomeGasUsageActivity.this, "가스밸브 로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Intent intent = new Intent(HomeGasUsageActivity.this, HomeGasLogActivity.class);
                intent.putExtra("getGasLogsURL", urlstr);
                startActivity(intent);
                return true;
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
