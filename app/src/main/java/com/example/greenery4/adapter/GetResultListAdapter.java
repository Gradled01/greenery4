package com.example.greenery4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenery4.R;
import com.example.greenery4.bean.Comment;

import java.util.ArrayList;
import java.util.List;

public class GetResultListAdapter extends RecyclerView.Adapter<GetResultListAdapter.InnerHolder> {

    private List<Comment> comments = new ArrayList<>();
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        TextView usernameTv = itemView.findViewById(R.id.username);
        TextView commentTv = itemView.findViewById(R.id.content);

        Comment comment = comments.get(position);

        usernameTv.setText(comment.getUsername());
        commentTv.setText(comment.getContent());


        //常规写法
        //ImageView cover = itemView.findViewById(R.id.item_iv);
        //Glide.with(itemView.getContext()).load("http://10.0.2.2:9102"+data.get(position).getCover()).into(cover);


       //简单封装
        //String string = "http://10.0.2.2:9102/" + data.get(position).getCover();
       // GlideLoader.loadCommonImage(string,cover);



    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setData(List<Comment> comment_list) {
        comments.clear();
        comments.addAll(comment_list);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
