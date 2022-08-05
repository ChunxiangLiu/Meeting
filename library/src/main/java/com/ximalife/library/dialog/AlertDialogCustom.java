package com.ximalife.library.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.ximalife.library.R;

/**
 * Created by zsxx on 2018/1/30.
 */

public class AlertDialogCustom {

    private AlertDialog dialog;
    LottieAnimationView v;

    public AlertDialogCustom(Context mContext) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.lottie_dialog, null);
        v = view.findViewById(R.id.animation_view);
        v.setImageAssetsFolder("images");
        v.setAnimation("loading3.json");
        normalDialog.setView(view);
        // 显示
        dialog = normalDialog.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    public AlertDialogCustom(Context mContext, boolean defa) {
        if(defa){
            AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
            View view = LayoutInflater.from(mContext).inflate(R.layout.lottie_dialog, null);
            v = view.findViewById(R.id.animation_view);
            v.setImageAssetsFolder("images");
            v.setAnimation("googleloading.json");
            normalDialog.setView(view);
            // 显示
            dialog = normalDialog.create();
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        }else{
            AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
            ImageView imageView = view.findViewById(R.id.loading_dialog_img);

            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();

            normalDialog.setView(view);
            // 显示
            dialog = normalDialog.create();
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        }

    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void show() {
        if (dialog != null) {
            if (v != null) {
                v.playAnimation();
            }
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            if (v != null) {
                v.cancelAnimation();
            }
            dialog.dismiss();
        }
    }

    //获取dialogview
    public AlertDialog getDialog() {
        return dialog;
    }
}
