package co.kr.crystalstudio.keepinmind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
//    private EditText eventDateET, eventTimeET;

//    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        eventNameET = findViewById(R.id.eventNameET);

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
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate);
//        ,time 뺐음
        Event.eventsList.add(newEvent);
        finish();
    }

   //***일정 작성 및 서버에 저장 메소드***

    public void serverSave(View view){
        String userID = Global.userID;
        String eventName = eventNameET.getText().toString();

        //1.Retrofit 객체 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://soo9028.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.saveDataToServer(userID,eventName);

        //실제 네트워크 작업 시작
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               //응답된 글씨
               String data = response.body();
                Toast.makeText(EventEditActivity.this, "일정 저장됨", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EventEditActivity.this, "일정 저장 실패: 서버 에러", Toast.LENGTH_SHORT).show();
            }
        });

    }

}