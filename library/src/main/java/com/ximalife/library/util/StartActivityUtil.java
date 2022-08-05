package com.ximalife.library.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ximalife.library.Constant;


public class StartActivityUtil {

    public static void startActivity(Context context, Class mClass, Bundle bundle) {
        if (context == null || mClass == null) {
            return;
        }
        Intent intent = new Intent(context, mClass);
        if (bundle != null) {
            intent.putExtra(Constant.BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class mClass) {
        if (context == null || mClass == null) {
            return;
        }
        Intent intent = new Intent(context, mClass);
        context.startActivity(intent);
    }
}
