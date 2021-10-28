package co.kr.crystalstudio.keepinmind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    boolean isEdit = false;
    String no;
//    private EditText eventDateET, eventTimeET;

//    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        eventNameET = findViewById(R.id.eventNameET);

        //수정 버튼을 눌렀을 때, 전달되어 온 no,eventName을 받기
        no = getIntent().getStringExtra("no");
        String eventName = getIntent().getStringExtra("eventName");
        if(eventName!=null) {
            eventNameET.setText(eventName);
            isEdit = true;
        }

//        time = LocalTime.now();
//        eventDateET.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
//        eventTimeET.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
//        eventDateET = findViewById(R.id.eventDateTV);
//        eventTimeET = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();

        if (isEdit==false){
            saveEventData(eventName, CalendarUtils.selectedDate);
        }else{
            editEventData(no,eventName);
        }
    }

    void saveEventData(String eventName, LocalDate selectedDate){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://soo9028.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call= retrofitService.saveDataToServer(Global.userID, eventName, selectedDate.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(EventEditActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EventEditActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    void editEventData(String no, String eventName) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://soo9028.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<String> call= retrofitService.editDataToServer(no,eventName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(EventEditActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EventEditActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    }

