package com.example.greenery4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenery4.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class PostCommentActivity extends AppCompatActivity {

    private EditText et_comment;
    private Button bt_comm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        et_comment = findViewById(R.id.et_comment);

       // String comment =  et_comment.getText().toString();

        bt_comm = findViewById(R.id.btn_comment);
        bt_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("====================================="+et_comment.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.216.214.86:8080/CartDemo/comment/add.do?commentJson="+et_comment.getText().toString());//

                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(10000);
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);// 允许输出
                            connection.setRequestProperty("Connection", "Keep-Alive");
                            connection.setRequestProperty("Charset", "GBK");
                            connection.connect();
                            //结果码
                            int responseCode = connection.getResponseCode();
                            if(responseCode == 200){

                                System.out.println("请求成功");

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                Intent intent =new Intent(PostCommentActivity.this, CommentActivity.class);
                startActivity(intent);

            }
        });

    }

    public void loadJson(){
        //网络请求不能在主线程，所以新开一个线程，但以下操作不适合在实际开发中使
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.216.214.86:8080/CartDemo/comment/add.do?commentJson="+et_comment.getText().toString());//

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    //结果码
                    int responseCode = connection.getResponseCode();
                    if(responseCode == 200){

                        System.out.println("请求成功");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
