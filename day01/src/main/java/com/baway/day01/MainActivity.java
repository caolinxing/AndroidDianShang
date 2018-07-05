package com.baway.day01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baway.utils.PhoneFormatCheckedUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ev_phone;
    private EditText ev_pwd;
    private Button btn_login;
    private Button btn_reg;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void findView() {
        ev_phone =  findViewById(R.id.ev_phone);
        ev_pwd = findViewById(R.id.ev_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
    }

    private void setListener() {
        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (TextUtils.isEmpty(ev_pwd.getText())||TextUtils.isEmpty(ev_phone.getText())){
                    Toast.makeText(this,"手机号和密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    okHttpClient = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("mobile",ev_phone.getText().toString())
                            .add("password",ev_pwd.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://120.27.23.105/user/login")
                            .post(requestBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String str = jsonObject.optString("msg");
                                Log.i("sss",str);
                                if (str.equals("登录成功")){
                                    Intent intent = new Intent(MainActivity.this,GoodsActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_reg:
                Intent intent = new Intent(this,RegActivity.class);
                startActivity(intent);
                break;
        }
    }
}
