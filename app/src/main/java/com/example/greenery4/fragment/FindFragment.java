package com.example.greenery4.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.greenery4.GlideLoader;
import com.example.greenery4.R;
import com.example.greenery4.TopicGson;
import com.example.greenery4.base.BaseFragment;
import com.example.greenery4.databinding.FragmentSearchBinding;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FindFragment extends BaseFragment<FragmentSearchBinding> {
    @Override
    public void initData() {

    }

    private RecyclerView ryLeft;
    private RecyclerView ryRight;

    @Override
    public void initView(FragmentSearchBinding view) {
        ryLeft = view.ryLeft;
        ryRight = view.ryRight;
        ryLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        ryRight.setLayoutManager(new LinearLayoutManager(getContext()));
        InputStream open = null;
        try {
            open = getContext().getAssets().open("user_post_topic.json");
            int lenght = open.available();
            byte[] buffer = new byte[lenght];
            open.read(buffer);
            String result = new String(buffer, "utf8");
            Gson gson = new Gson();
            TopicGson topicGson = gson.fromJson(result, TopicGson.class);
            List<TopicGson.DataBean> data = topicGson.getData();
            TopicLeftMenuAdapter topicLeftMenuAdapter = new TopicLeftMenuAdapter(data);
            ryLeft.setAdapter(topicLeftMenuAdapter);

            Log.i(TAG, "initView: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rightMenuAdapter = new RightMenuAdapter(null);
        ryRight.setAdapter(rightMenuAdapter);
    }

    private RightMenuAdapter rightMenuAdapter;


    private static final String TAG = "FindFragment";

    @Override
    public FragmentSearchBinding initViewBinding() {
        return FragmentSearchBinding.inflate(LayoutInflater.from(getContext()));
    }

    private class TopicLeftMenuAdapter extends BaseQuickAdapter<TopicGson.DataBean, BaseViewHolder> {
        private int position = 0;

        public TopicLeftMenuAdapter(List<TopicGson.DataBean> data) {
            super(R.layout.ry_topic_left_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TopicGson.DataBean topicGson) {
            baseViewHolder.getView(R.id.tv_topic_name)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            position = baseViewHolder.getPosition();
                            notifyDataSetChanged();
                            rightMenuAdapter.getData().clear();
                            rightMenuAdapter.addData(topicGson.getItem());

                        }
                    });
            baseViewHolder.setText(R.id.tv_topic_name, topicGson.getTopic_name());
            if (position == baseViewHolder.getPosition()) {
                baseViewHolder.setVisible(R.id.view_divide, true)
                        .setTextColor(R.id.tv_topic_name, 0xff000000);
            } else {
                baseViewHolder.setVisible(R.id.view_divide, false)
                        .setTextColor(R.id.tv_topic_name, 0xffbfbfbf);
            }
        }
    }

    private class RightMenuAdapter extends BaseQuickAdapter<TopicGson.DataBean.ItemBean, BaseViewHolder> {

        public RightMenuAdapter(List<TopicGson.DataBean.ItemBean> data) {
            super(R.layout.ry_topic_right_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TopicGson.DataBean.ItemBean topicGson) {
            GlideLoader.loadCenterRoundImage(topicGson.getTopic_pic_url(), baseViewHolder.getView(R.id.iv_cover), 9);
            baseViewHolder.setText(R.id.tv_topic_name, topicGson.getTopic_name())
                    .setText(R.id.tv_join_count, String.format("已有%s位用户关注", topicGson.getJoin_count()));
        }
    }

}
