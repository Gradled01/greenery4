package com.example.greenery4.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;


public abstract class BaseFragment<B extends ViewBinding> extends Fragment {
    protected B mBinding;
    protected Context mContext;//activity的上下文对象

    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public abstract void initData();

    public abstract void initView(B view) ;

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取bundle,并保存起来


    }


    private ViewGroup viewGroup;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        this.viewGroup = container;
        mBinding = initViewBinding();
        View root = mBinding.getRoot();


        initView(mBinding);
        initData();
        return root;
    }




    public ViewGroup getViewGroup() {
        return viewGroup;
    }


    public abstract B initViewBinding();

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

}
