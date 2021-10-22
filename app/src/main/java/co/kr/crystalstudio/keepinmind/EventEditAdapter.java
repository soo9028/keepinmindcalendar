package co.kr.crystalstudio.keepinmind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class EventEditAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{

    Context context;
    ArrayList<Item> items;

    public EventEditAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_week_view, parent, false);
        CalendarViewHolder vh = new CalendarViewHolder(itemView);
        return vh;
    }

    public void onBindViewHolder(EventEditAdapter.)

}
