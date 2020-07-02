package com.example.greenery4.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.greenery4.LoginActivity;
import com.example.greenery4.R;
import com.example.greenery4.base.BaseFragment;
import com.example.greenery4.databinding.FragmentMeBinding;

public class UserFragment extends BaseFragment<FragmentMeBinding> {

    private TextView tv_logout;

    @Override
    public void initData() {

    }

    @Override
    public void initView(FragmentMeBinding view) {
        tv_logout = view.tvLogout;
        tv_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public FragmentMeBinding initViewBinding() {
        return FragmentMeBinding.inflate(LayoutInflater.from(getContext()));
    }

    protected void logout(BaseViewHolder baseViewHolder) {
        baseViewHolder.getView(R.id.tv_topic_name)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

    }
}
