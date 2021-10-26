package co.kr.crystalstudio.keepinmind;

public class Item {
    int no;
    String userID;
    String eventName;
    String eventDate;

    public Item(int no, String userID, String eventName, String eventDate){
        this.no = no;
        this.userID = userID;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }
}
