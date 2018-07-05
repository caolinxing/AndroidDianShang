package com.baway.day01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baway.utils.PhoneFormatCheckedUtil;

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

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ev_phone;
    private EditText ev_pwd;
    private Button btn_reg;
    private ImageButton btn_back;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        findView();
        setListener();
    }
    private void findView() {
        ev_phone =  findViewById(R.id.reg_ev_phone);
        ev_pwd = findViewById(R.id.reg_ev_pwd);
        btn_reg = findViewById(R.id.reg_btn_reg);
        btn_back = findViewById(R.id.reg_btn_back);
    }

    private void setListener() {
        btn_reg.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn_reg:
                if (TextUtils.isEmpty(ev_pwd.getText())||TextUtils.isEmpty(ev_phone.getText())){
                    Toast.makeText(this,"手机号和密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    boolean flag = PhoneFormatCheckedUtil.isChinaPhoneLegal(ev_phone.getText().toString());
                    if (flag){
                        okHttpClient = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("mobile",ev_phone.getText().toString())
                                .add("password",ev_pwd.getText().toString())
                                .build();
                        Request request = new Request.Builder()
                                .url("http://120.27.23.105/user/reg")
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
                                    JSONObject object = new JSONObject(response.body().toString());
                                    String str = object.optString("msg");
                                    if (str.equals("注册成功")){
                                        Log.i("sss",str);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                tiaoZhuan();
                            }
                        });

                    }else{
                        Toast.makeText(this,"手机号格式错误",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.reg_btn_back:
                tiaoZhuan();
                break;
        }
    }

    private void tiaoZhuan() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
