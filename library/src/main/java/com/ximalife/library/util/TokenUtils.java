package com.ximalife.library.util;

import android.content.Context;
import android.text.TextUtils;

import com.ximalife.library.http.HttpConfig;

import java.util.UUID;

public class TokenUtils {

    private static final String KEY_AUTH_INFO = "Auth_Info";
    private static final String KEY_REFRESH_AUTH_INFO = "Refresh_Auth_Info";
    private static final String TOKEN_INFO = "BaseToken";
    private static final String KEY_USERTOKEN = "userToken";


    //保存客户端授权
    public static void saveAuth(Context context, String auth) {
        SPUtils.put(context, TOKEN_INFO, KEY_AUTH_INFO, auth);
    }
    public static String getAuth(Context context) {
        return (String) SPUtils.get(context, TOKEN_INFO, KEY_AUTH_INFO, "");
    }

    public static void saveRefreshAuth(Context context, String auth) {
        SPUtils.put(context, TOKEN_INFO, KEY_REFRESH_AUTH_INFO, auth);
    }
    public static String getRefreshAuth(Context context) {
        return (String) SPUtils.get(context, TOKEN_INFO, KEY_REFRESH_AUTH_INFO, "");
    }
    public static void saveUserToken(Context context, String token) {
        SPUtils.put(context, TOKEN_INFO, KEY_USERTOKEN,token);
    }
    public static String getUserToken(Context context) {
        return (String) SPUtils.get(context, TOKEN_INFO, KEY_USERTOKEN, "");
    }

    public static void clearToken(Context context) {
        SPUtils.clear(context, TOKEN_INFO);
    }
}
