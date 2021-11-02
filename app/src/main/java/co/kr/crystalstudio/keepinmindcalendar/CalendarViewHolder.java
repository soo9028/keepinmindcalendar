package co.kr.crystalstudio.keepinmindcalendar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    public TextView tv_eventCell;
    private final CalendarAdapter.OnItemListener onItemListener;
    public Context context;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        tv_eventCell = itemView.findViewById(R.id.tv_eventCell);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
        context = itemView.getContext().getApplicationContext();
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
        if(Global.activityNum != 1){
            Intent intent = new Intent(context,WeekViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}