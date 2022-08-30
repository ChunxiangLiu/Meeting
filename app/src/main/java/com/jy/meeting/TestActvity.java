package com.jy.meeting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jy.meeting.view.DrawCircle;

public class TestActvity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DrawCircle circle = findViewById(R.id.circle);
        Log.e("lcx",circle.getAlpha()+"");
        circle.setZoomEnabled(true);
        circle.getZoomer().setReadMode(false);
        circle.getZoomer().zoom(3f, true);
        circle.displayResourceImage(R.mipmap.ic_guid_bg);

    }
}
