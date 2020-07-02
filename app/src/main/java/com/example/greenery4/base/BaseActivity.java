package com.example.greenery4.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;


public abstract class BaseActivity<B extends ViewBinding> extends FragmentActivity {
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    public B mBinding;



    public abstract B initViewBinding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        mBinding = initViewBinding();
        setContentView(mBinding.getRoot());
        initView(mBinding);
        initData();

    }





    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
//        boolean finish = getIntent().getBooleanExtra("finish", false);
//        if (finish){
//            ARouter.getInstance().build(ARouterAPI.SPLASH_ACTIVITY)
//                    .withString("package",this.getClass().getPackage().getName()+"."+this.getClass().getSimpleName()).navigation();
//            Log.i(TAG, "onRestart: "+this.getClass().getSimpleName().toString());
//            Log.i(TAG, "onRestart: "+this.getClass().getPackage().getName());
//        }

    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private onMenuClickListener onMenuClickListener;

    public void setOnMenuClickListener(BaseActivity.onMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface onMenuClickListener {
        void onMenuClickListener();
    }


    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }


    /**
     * 初始化布局
     */
    public abstract void initView(B view);

    /**
     * 设置数据
     */
    public abstract void initData();


}