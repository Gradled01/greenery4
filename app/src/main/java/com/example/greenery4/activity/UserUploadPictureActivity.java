package com.example.greenery4.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.greenery4.GlideEngine;
import com.example.greenery4.R;
import com.example.greenery4.base.BaseActivity;
import com.example.greenery4.databinding.ActivityUserUploadBinding;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.List;
import java.util.UUID;
import cn.leancloud.AVFile;
import cn.leancloud.AVObject;


public class UserUploadPictureActivity extends BaseActivity<ActivityUserUploadBinding> {
    private PictureAdapter pictureAdapter;
    @Override
    public ActivityUserUploadBinding initViewBinding() {
        return ActivityUserUploadBinding.inflate(LayoutInflater.from(this));
    }
    @Override
    public void initView(ActivityUserUploadBinding view) {
        pictureAdapter = new PictureAdapter(null);
        pictureAdapter.addData(new LocalMedia());
        mBinding.ryPost.setLayoutManager(new GridLayoutManager(this, 3));
        view.ryPost.setAdapter(pictureAdapter);
        view.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AVObject post = new AVObject("Post");
                    post.put("title", view.etContent.getText().toString());
                    for (int i = 0; i < pictureAdapter.getData().size(); i++) {
                        if (pictureAdapter.getData().get(i).getPath() != null) {
                            AVObject comment = new AVObject("PostPicture");
                            Log.i(TAG, "onClick: =======================");
                            AVFile file = AVFile.withFile(UUID.randomUUID().toString() + ".jpg", new File(pictureAdapter.getData().get(i).getPath()));
                            comment.put("pictureFile", file);
                            comment.put("post", post);
                            comment.save();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "发布成功",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.i(TAG, "onClick: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void initData() {
    }
    private class PictureAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {
        public PictureAdapter(@Nullable List<LocalMedia> data) {
            super(R.layout.ry_picture, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, @Nullable LocalMedia localMedia) {
            ImageView ivPicture = baseViewHolder.getView(R.id.iv_picture);
            if (localMedia.getPath() != null) {
                Glide.with(UserUploadPictureActivity.this).asBitmap().load(localMedia.getPath()).into(ivPicture);
            } else {
                Glide.with(UserUploadPictureActivity.this).asBitmap().load(R.drawable.add).into(ivPicture);
            }
            ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureSelector.create(UserUploadPictureActivity.this)
                            .openGallery(PictureMimeType.ofAll())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .previewImage(true)// 是否可预览图片 true or false
                            .enableCrop(true)// 是否裁剪 true or false
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                            .forResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    pictureAdapter.addData(0, result);
                                    pictureAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancel() {
                                }
                            });
                }
            });
        }
    }
}
