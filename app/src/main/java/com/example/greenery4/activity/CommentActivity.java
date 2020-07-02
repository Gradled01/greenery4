package com.example.greenery4.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenery4.R;
import com.example.greenery4.adapter.GetResultListAdapter;
import com.example.greenery4.bean.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private GetResultListAdapter adapter;
    private Button say;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        say = findViewById(R.id.btn_saysomting);
        say.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(CommentActivity.this, PostCommentActivity.class);
                startActivity(intent);
            }
        });

        loadJson();
        initView();



    }

    private void initView() {
        RecyclerView recyclerView = this.findViewById(R.id.comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;

            }
        });
        adapter=new GetResultListAdapter();
        recyclerView.setAdapter(adapter);


    }


    public void loadJson(){
        //网络请求不能在主线程，所以新开一个线程，但以下操作不适合在实际开发中使
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.216.214.86:8080/CartDemo/CommentJsonServlet");//

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    //结果码
                    int responseCode = connection.getResponseCode();
                    if(responseCode == 200){

                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String json = bufferedReader.readLine();

                        //Log.i(TAG, "run: =============================="+json);

                        Gson gson = new Gson();
                        List<Comment> comments = gson.fromJson(json, new TypeToken<List<Comment>>(){}.getType());
                        updateUi(comments);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateUi(final List<Comment> comments) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(comments);
            }
        });
    }
}
