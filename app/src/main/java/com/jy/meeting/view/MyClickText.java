package com.jy.meeting.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.ColorInt;



public class MyClickText extends ClickableSpan {
    private Context context;
    private int type;
    int color;
    public MyClickText(Context context, int type) {
        this.context = context;
        this.type = type;
        this.color= Color.rgb(255, 255, 255);
    }
    public MyClickText(Context context, int type, @ColorInt int color) {
        this.context = context;
        this.type = type;
        this.color=color;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);         //设置文本的颜色
        ds.setColor(color);
        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
//          ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
//        Intent intent = new Intent();
//        intent.setClass(context, AppSerectActivity.class);
//        intent.putExtra("type", type);
//        context.startActivity(intent);
    }






}
