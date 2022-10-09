package com.jy.meeting.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ximalife.library.view.FlowLayout;

public class TestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowLayout.ItemView<String> itemView = new FlowLayout.ItemView<String>() {
            @Override
            public void getCover(String item, FlowLayout.ViewHolder holder, View inflate, int position) {

            }
        };
    }
}
