package co.kr.crystalstudio.keepinmind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static co.kr.crystalstudio.keepinmind.CalendarUtils.daysInMonthArray;
import static co.kr.crystalstudio.keepinmind.CalendarUtils.daysInWeekArray;
import static co.kr.crystalstudio.keepinmind.CalendarUtils.monthYearFromDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadData();
        setEventAdpater();
    }

    void loadData(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://soo9028.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Item>> call = retrofitService.loadDataFromServer(Global.userID);
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
               ArrayList<Item> datas= response.body();
               Event.eventsList.clear();
               eventAdapter.notifyDataSetChanged();

               for(Item item:datas){
                   String eventDate = item.eventDate;
                   String[] date= eventDate.split("-");
                   int year = Integer.parseInt(date[0]);
                   int month = Integer.parseInt(date[1]);
                   int day = Integer.parseInt(date[2]);

                   String eventName = item.eventName;
                   Event event = new Event(eventName, LocalDate.of(year, month, day));
                   Event.eventsList.add(event);
                   eventAdapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Toast.makeText(WeekViewActivity.this, "" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setEventAdpater()
    {
        final ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeekViewActivity.this);
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dailyEvents.remove(position);
                        eventAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("아니오",null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }



    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void monthlyAction(View view) {finish();}



}