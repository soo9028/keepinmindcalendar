package co.kr.crystalstudio.keepinmindcalendar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    //서버에 POST방식으로 데이터를 보내는 기능메소드 이름
    @FormUrlEncoded
    @POST("/AndroidAppCalendar/eventInsertDB.php")
    Call<String> saveDataToServer(@Field("userID") String userID, @Field("eventName") String eventName, @Field("eventDate") String userDate);

    //서버에서  GET방식으로 데이터를 요청하는 메소드 추상메소드
    @GET("/AndroidAppCalendar/eventLoadDB.php")
    Call<ArrayList<Item>> loadDataFromServer(@Query("userID") String userID);

    //서버에 저장된 데이터를 삭제하는 기능메소드
    @GET("/AndroidAppCalendar/eventDeleteDB.php")
    Call<String> deleteDataFromServer(@Query("no") String no);

    //서버에 저장된 데이터를 수정하는 기능메소드
    @FormUrlEncoded
    @POST("/AndroidAppCalendar/eventEditDB.php")
    Call<String> editDataToServer(@Field("no") String no, @Field("eventName") String eventName);



}



