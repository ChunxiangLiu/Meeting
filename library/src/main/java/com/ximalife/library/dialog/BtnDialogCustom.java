package com.ximalife.library.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;
import com.ximalife.library.R;

/**
 * Created by zsxx on 2018/1/30.
 */

public class BtnDialogCustom {

    private AlertDialog dialog;
    LottieAnimationView v;

    public BtnDialogCustom(Context mContext,String text,onBtnClick click) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.lottie_dialog_siglet_btn, null);
        v = view.findViewById(R.id.animation_view);
        v.setImageAssetsFolder("images");
        v.setAnimation("internetemptystate.json");


        View ok = view.findViewById(R.id.btn_ok);
        TextView content = view.findViewById(R.id.content);
        content.setText(text);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                click.onClickOk();
            }
        });
        normalDialog.setView(view);
        // 显示
        dialog = normalDialog.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
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

    public interface onBtnClick{
        void onClickOk();
    }
}
