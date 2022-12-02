package com.ximalife.library.view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meet.library.R;


public class ToastUtil {
    private static Toast mToast;
    private static TextView mTextView;
    private static String message;
    private boolean canceled = true;

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context context, int layoutId, String msg) {
        message = msg;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(layoutId, null);
        //自定义toast文本
        TextView textView = (TextView)view.findViewById(R.id.toast_msg);

//        if (mToast == null) {
//            mToast = new Toast(context);
//        }
//        //设置toast居中显示
//        mToast.setGravity(Gravity.TOP, 0, 200);
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setView(view);

        if (mToast == null) {
            //设置toast居中显示
            mTextView = textView;
            mToast  = new Toast(context);
            mToast.setGravity(Gravity.CENTER, 0, 200);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(view);
            mTextView.setText(msg);
        }else{
            mToast.cancel();
            mTextView = textView;
            mToast= new Toast(context);
            mToast.setGravity(Gravity.CENTER, 0, 200);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(view);
            mTextView.setText(msg);
        }
        mToast.show();
    }

    /**
     * 自定义居中显示toast
     */
    public static void show() {
        mHandler.removeCallbacks(r);
        mHandler.postDelayed(r, 5000);
        mToast.show();
    }
}
