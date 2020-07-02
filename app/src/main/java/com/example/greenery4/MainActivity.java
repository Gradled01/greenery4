package com.example.greenery4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenery4.activity.UserUploadPictureActivity;
import com.example.greenery4.base.BaseActivity;
import com.example.greenery4.databinding.ActivityMainBinding;
import com.example.greenery4.fragment.FindFragment;
import com.example.greenery4.fragment.HomeFragment;
import com.example.greenery4.fragment.MsgFragment;
import com.example.greenery4.fragment.UserFragment;

import java.io.FileNotFoundException;

import cn.leancloud.AVFile;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author Administrator
 */

@SuppressLint("Registered")
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Fragment mHomeFragment;
    private Fragment mGroupFragment;
    private Fragment mStudyFragment;
    private Fragment mUserFragment;
    public static int mFragmentId = 0;
    public static final int HOME = 0;
    public static final int GROUP = 1;
    public static final int STUDY = 2;
    public static final int USER = 3;




    @Override
    public ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //通过onSaveInstanceState方法保存当前显示的fragment
        outState.putInt("fragment_id", mFragmentId);
        super.onSaveInstanceState(outState);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ============");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mHomeFragment = mFragmentManager.findFragmentByTag("home_fragment");
        mGroupFragment = mFragmentManager.findFragmentByTag("find_fragment");
        mStudyFragment = mFragmentManager.findFragmentByTag("message_fragment");
        mUserFragment = mFragmentManager.findFragmentByTag("user_fragment");
        setFragment(savedInstanceState.getInt("fragment_id"));
    }


    @Override
    public void initView(ActivityMainBinding view) {
        setFragment(0);
        view.rbHome.setChecked(true);
        view.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserUploadPictureActivity.class);
            }
        });
        view.rbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(0);
            }
        });
        view.rbFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(1);
            }
        });
        view.rbMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(2);
            }
        });
        view.rbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(3);
            }
        });
        AVObject todo = new AVObject("Todo");

// 为属性赋值
        todo.put("title", "马拉松报名");
        todo.put("priority", "mmmmm");
        try {
            AVFile file = AVFile.withAbsoluteLocalPath("avatar.jpg", "/tmp/avatar.jpg");
            todo.saveInBackground().subscribe(new Observer<AVObject>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(AVObject avObject) {
                    Log.i(TAG, "onNext: "+avObject.getClassName());
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.i(TAG, "onError: " + throwable.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
// 将对象保存到云端

    }


    @Override
    public void initData() {

    }

    private void setFragment(int index) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        hideFragments(mTransaction);
        switch (index) {
            default:
                break;
            case HOME:
                mFragmentId = HOME;
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mTransaction.add(R.id.container, mHomeFragment, "home_fragment");
                } else {
                    mTransaction.show(mHomeFragment);
                }
                break;
            case GROUP:
                mFragmentId = GROUP;
                if (mGroupFragment == null) {
                    mGroupFragment = new FindFragment();
                    mTransaction.add(R.id.container, mGroupFragment, "find_fragment");
                } else {
                    mTransaction.show(mGroupFragment);
                }
                break;
            case STUDY:
                mFragmentId = STUDY;
                if (mStudyFragment == null) {
                    mStudyFragment = new MsgFragment();
                    mTransaction.add(R.id.container, mStudyFragment, "message_fragment");
                } else {
                    mTransaction.show(mStudyFragment);
                }
                break;
            case USER:
                mFragmentId = USER;
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
                    mTransaction.add(R.id.container, mUserFragment, "user_fragment");
                } else {
                    mTransaction.show(mUserFragment);
                }
                break;
        }
        //提交事务
        mTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mGroupFragment != null) {
            transaction.hide(mGroupFragment);
        }
        if (mStudyFragment != null) {
            transaction.hide(mStudyFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
    }

}
