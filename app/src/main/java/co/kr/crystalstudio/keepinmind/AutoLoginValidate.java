package co.kr.crystalstudio.keepinmind;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AutoLoginValidate extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SaveSharedPreferences.getUserName(AutoLoginValidate.this).length() == 0) {
            // call Login Activity
            intent = new Intent(AutoLoginValidate.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            intent = new Intent(AutoLoginValidate.this, MainActivity.class);
            intent.putExtra("STD_NUM", SaveSharedPreferences.getUserName(this).toString());
            startActivity(intent);
            this.finish();
        }
    }
}

