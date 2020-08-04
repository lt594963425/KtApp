package com.example.ktapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.example.ktapp.R;
import com.example.ktapp.base.BaseDataBindActivity;
import com.example.ktapp.databinding.ActivityMainDataBinding;
import com.example.ktapp.ui.adapter.GridImageAdapter;
import com.example.ktapp.ui.recyclercallback.OnRecyclerItemClickListener;
import com.example.ktapp.ui.recyclercallback.WXTouchHelper;
import com.example.ktapp.ui.login.LoginActivity;
import com.example.ktapp.ui.roomdata.RoomDataActivity;
import com.example.ktapp.ui.start.StartActivity;
import com.example.ktapp.ui.view.FullyGridLayoutManager;
import com.example.ktapp.ui.welcome.WelcomeActivity;
import com.example.ktapp.utils.SDFileSelecteUtil;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends BaseDataBindActivity<ActivityMainDataBinding> {
    private static final String TAG = "Main2Activity";
    private String runtimeKey = "runtimelite,1000,rud5244175697,none,D7MFA0PL4S8PC2EN0171";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArcGISRuntimeEnvironment.setLicense(runtimeKey);
        dataBind.deleteImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main2Activity.this, RecyclerImageActivity.class));
            }
        });
        dataBind.startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, StartActivity.class));

            }
        });
        dataBind.loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, LoginActivity.class));
            }
        });
        dataBind.roomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, RoomDataActivity.class));
            }
        });
        dataBind.welcomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, WelcomeActivity.class));
            }
        });
        dataBind.gismapTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, GisMapActivity.class));
            }
        });

    }


}
