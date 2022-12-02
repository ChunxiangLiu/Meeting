package com.ximalife.library.util;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MyClickSpan extends ClickableSpan {

    private Context context;
    private int colorId;
    private boolean isUnderline;

    public MyClickSpan(Context context, int colorId, boolean isUnderline) {
        this.context = context;
        this.colorId = colorId;
        this.isUnderline = isUnderline;
    }

    public MyClickSpan(Context context, boolean isUnderline) {
        this.context = context;
        this.isUnderline = isUnderline;
    }


    @Override
    public void onClick(View widget) {
//        Log.e("click","点击了");
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (colorId != 0) {
            ds.setColor(colorId);//颜色
        }
        ds.setUnderlineText(isUnderline); //去掉下划线

    }

}
