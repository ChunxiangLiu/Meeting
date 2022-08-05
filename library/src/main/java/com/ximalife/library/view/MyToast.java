package com.ximalife.library.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ximalife.library.R;

public class MyToast {


        private static String oldMsg;
        private static TextView mTextView;
        protected static Toast toast   = null;
        private static long oneTime=0;
        private static long twoTime=0;

        public static void showToast(Context context, String s){
            if(toast==null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //自定义布局
                View view = inflater.inflate(R.layout.toast_dialog, null);
                //自定义toast文本
                TextView textView = (TextView)view.findViewById(R.id.toast_msg);
                mTextView = textView;
                toast  = new Toast(context);
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                mTextView.setText(s);

//                toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
                toast.show();
                oneTime=System.currentTimeMillis();
            }else{
                twoTime=System.currentTimeMillis();
                if(s.equals(oldMsg)){
                    if(twoTime-oneTime> Toast.LENGTH_SHORT){
                        toast.show();
                    }
                }else{
                    oldMsg = s;
                    mTextView.setText(s);
                    toast.show();
                }
            }
            oneTime=twoTime;
        }

    public static void showToast11(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }
    }
