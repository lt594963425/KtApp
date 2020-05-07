package com.example.ktapp.ui.start;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ktapp.R;
import com.example.ktapp.ui.start.StartFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StartFragment.newInstance())
                    .commitNow();
        }
    }
}
