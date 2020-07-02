package com.example.greenery4.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.greenery4.Post;
import com.example.greenery4.R;
import com.example.greenery4.activity.CommentActivity;
import com.example.greenery4.base.BaseFragment;
import com.example.greenery4.databinding.FragmentHomeBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVFile;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    public void initData() {

    }

    private SmartRefreshLayout smlPost;

    private PostAdapter postAdapter;



    @Override
    public void initView(FragmentHomeBinding view) {



        view.ryPost.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(null);
        view.ryPost.setAdapter(postAdapter);
        requestData(skip);
        smlPost = view.smlPost;
        smlPost.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData(postAdapter.getData().size());
            }
        });
        smlPost.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                postAdapter.getData().clear();
                postAdapter.notifyDataSetChanged();
                requestData(0);
            }
        });


    }

    private int skip = 0;

    private void requestData(int skip) {
        AVQuery<AVObject> query = new AVQuery<>("Post");
        query.skip(skip).limit(5);
        query.findInBackground().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AVObject>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(List<AVObject> avObjects) {
                        for (int i = 0; i < avObjects.size(); i++) {
                            Post postEntity = new Post();
                            postEntity.setTitle(avObjects.get(i).get("title").toString());
                            AVObject post = AVObject.createWithoutData("Post", avObjects.get(i).getObjectId());
                            AVQuery<AVObject> query = new AVQuery<>("PostPicture");
                            query.whereEqualTo("post", post);//
                            query.findInBackground().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<AVObject>>() {
                                @Override
                                public void onSubscribe(Disposable disposable) {

                                }

                                @Override
                                public void onNext(List<AVObject> avObjects) {
                                    List<String> list = new ArrayList<>();
                                    for (int j = 0; j < avObjects.size(); j++) {
                                        list.add(((AVFile) avObjects.get(j).get("pictureFile")).getUrl());
                                    }
                                    postEntity.setPicture(list);
                                    postAdapter.addData(postAdapter.getData().size(), postEntity);
                                    smlPost.finishLoadMore();
                                    smlPost.finishRefresh();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    smlPost.finishLoadMore();
                                    smlPost.finishRefresh();
                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        smlPost.finishLoadMore();
                        smlPost.finishRefresh();
                    }

                    @Override
                    public void onComplete() {
                        smlPost.finishLoadMore();
                        smlPost.finishRefresh();
                    }
                });

    }

    private static final String TAG = "HomeFragment";

    @Override
    public FragmentHomeBinding initViewBinding() {
        return FragmentHomeBinding.inflate(LayoutInflater.from(getContext()));
    }

    class PostAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {
        boolean isChanged = false;

        public PostAdapter(@Nullable List<Post> data) {
            super(R.layout.item, data);
        }
        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable Post post) {
            PictureAdapter pictureAdapter = new PictureAdapter(null);
            RecyclerView ryPicture = baseViewHolder.getView(R.id.picture);
            ImageView img_favor = baseViewHolder.getView(R.id.favor);
            TextView favor_counts = baseViewHolder.getView(R.id.favor_counts);
            ImageView img_comment = baseViewHolder.getView(R.id.comment);

            img_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), CommentActivity.class);
                    startActivity(intent);
                }
            });

            img_favor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(v == img_favor){
                        if(isChanged){
                            img_favor.setImageDrawable(getResources().getDrawable(R.mipmap.favor));
                            favor_counts.setText("3");
                        }else {
                            img_favor.setImageDrawable(getResources().getDrawable(R.mipmap.favor_full));
                            favor_counts.setText("4");
                        }
                        isChanged  = !isChanged;
                    }

                    //img_favor.setBackgroundColor(getResources().getColor(R.color.pink_fc4f74));

                }
            });



            ryPicture.setNestedScrollingEnabled(false);
            ryPicture.setLayoutManager(new GridLayoutManager(HomeFragment.this.getContext(), 2));
            List<String> picture = post.getPicture();
            Log.i(TAG, "convert:================ " + picture);
            if (picture != null) {
                pictureAdapter.addData(picture);
                ryPicture.setAdapter(pictureAdapter);
            }

            baseViewHolder.setText(R.id.tv_title, post.getTitle());
        }
    }

    private class PictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PictureAdapter(@Nullable List<String> data) {
            super(R.layout.picture_item, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable String s) {
            Log.i(TAG, "convert:=====================" + s);
            Glide.with(HomeFragment.this.getContext()).load(s).into((ImageView) baseViewHolder.getView(R.id.ry_picture));
        }
    }
}

