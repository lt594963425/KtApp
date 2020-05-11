package com.example.ktapp.ui.roomdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ktapp.R;

public class RoomDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_data_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RoomDataFragment.newInstance())
                    .commitNow();
        }
    }
}
