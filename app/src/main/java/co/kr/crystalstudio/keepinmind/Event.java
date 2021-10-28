package co.kr.crystalstudio.keepinmind;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name; //일정 제목
    private LocalDate date;
    private String no;
//    private LocalTime time;

    public Event(String name, LocalDate date, String no)
//  LocalTime time값 없앰
    {
        this.name = name;
        this.date = date;
        this.no = no;

//        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    //    public LocalTime getTime()
//    {
//        return time;
//    }
//
//    public void setTime(LocalTime time)
//    {
//        this.time = time;
//    }
}