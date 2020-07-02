package com.example.greenery4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_account ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_account = findViewById(R.id.et_tel);
        TextView tv_login = findViewById(R.id.tv_login);
        String account =  edt_account.getText().toString();
        System.out.println(account);
        tv_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                //System.out.println(account);
                if(edt_account.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入账号密码",
                            Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(intent);
                }
            }
        });
    }

}
