package com.example.android_home_iot_smartmirror.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_home_iot_smartmirror.R;
import com.example.android_home_iot_smartmirror.ui.apicall.GetGasUsage;

import java.util.Calendar;

public class HomeGasUsageActivity extends AppCompatActivity {
    String getGasLogsURL;

    private TextView textView_Date1;
    private TextView textView_Date2;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    final static String TAG = "AndroidAPITest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_usage);

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
}
