package co.kr.crystalstudio.keepinmind;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"0aee7527829b1dd41d48579dc4246c6b");
    }
}
