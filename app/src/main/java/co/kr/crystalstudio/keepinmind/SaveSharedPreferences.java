package co.kr.crystalstudio.keepinmind;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;




public class SaveSharedPreferences {

    static final String PREF_USER_NAME = "username";
    static final String PREF_USER_PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    // 로그인 체크상태 저장하기
    public static void setLoginSaved(Context ctx, boolean isChecked){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean("LoginChecked", isChecked);
        editor.commit();
    }

    // 로그인 체크상태 가져오기
    public static boolean getLoginSaved(Context ctx){
        return getSharedPreferences(ctx).getBoolean("LoginChecked", false);
    }

    //계정 비밀번호 저장
    public static void setUserPassword(Context ctx, String userPassword){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASSWORD, userPassword);
        editor.commit();
    }

    //계정 비밀번호 가져오기
    public static String getUserPassword(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD,"");
    }

    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}