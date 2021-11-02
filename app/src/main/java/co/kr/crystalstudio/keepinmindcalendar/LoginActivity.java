package co.kr.crystalstudio.keepinmindcalendar;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register;
    private CheckBox cb_save;
    private ImageView btn_kakao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.i("keyhash",keyHash);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        cb_save=findViewById(R.id.cb_save);
        btn_kakao=findViewById(R.id.btn_kakao);

        btn_register.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                        if (oAuthToken != null){
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            //사용자 정보 가져오기
                            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                @Override
                                public Unit invoke(User user, Throwable throwable) {
                                    if(user!=null){
                                        Long userID = user.getId();
                                        String userNick = user.getKakaoAccount().getProfile().getNickname();
                                        String userPhoto = user.getKakaoAccount().getProfile().getProfileImageUrl();
                                        String userEmail = user.getKakaoAccount().getEmail();

                                        Global.userID = userNick + userID;
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    return null;
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                });

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() { //로그인 버튼
            @Override
            public void onClick(View v) {
                String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();


                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//회원등록 성공한 경우
                                String userID = jasonObject.getString("userID");
                                String userPass = jasonObject.getString("userPassword");
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                                Global.userID = userID;

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass",userPass);
                                intent.putExtra("log", "User");
                                startActivity(intent);
                                finish();
                            }


                            else{//회원등록 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LogInRequest loginRequest=new LogInRequest(userID,userPass,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

                if (cb_save.isChecked()) {
                    SaveSharedPreferences.setUserName(LoginActivity.this, et_id.getText().toString());
                    SaveSharedPreferences.setLoginSaved(LoginActivity.this, true);
                    SaveSharedPreferences.setUserPassword(LoginActivity.this,et_pass.getText().toString());

                }
                else{
                    SaveSharedPreferences.setUserName(LoginActivity.this, "");
                    SaveSharedPreferences.setLoginSaved(LoginActivity.this,false);
                    SaveSharedPreferences.setUserPassword(LoginActivity.this,"");
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        저장된 데이터 불러오기
        String userName = SaveSharedPreferences.getUserName(this);
        et_id.setText(userName);

        boolean LoginSaved = SaveSharedPreferences.getLoginSaved(this);
        cb_save.setChecked(LoginSaved);

        String userPassword = SaveSharedPreferences.getUserPassword(this);
        et_pass.setText((userPassword));

    }
}