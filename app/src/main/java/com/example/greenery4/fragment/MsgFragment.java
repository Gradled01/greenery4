package com.example.greenery4.fragment;

import android.view.LayoutInflater;

import com.example.greenery4.base.BaseFragment;
import com.example.greenery4.databinding.FragmentMessageBinding;

public class MsgFragment extends BaseFragment<FragmentMessageBinding> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(FragmentMessageBinding view) {

    }

    @Override
    public FragmentMessageBinding initViewBinding() {
        return FragmentMessageBinding.inflate(LayoutInflater.from(getContext()));
    }

}
