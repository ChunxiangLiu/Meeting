package com.ximalife.library.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ximalife.library.BuildConfig;
import com.ximalife.library.base.BaseAppApplication;
import com.ximalife.library.util.TokenUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class VersionInterceptor implements Interceptor {

    private String appVersion = BuildConfig.versionname;
    private String deviceType = "android";
    private String userAgent = "JM";

    private String auth = "";
    public VersionInterceptor(String auth) {
        this.auth = auth;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", userAgent)
                .header("X-Response-Behavior", "V3")
                .header("Device-Type",deviceType)
                .header("Authorization", TextUtils.isEmpty(auth)?TokenUtils.getAuth(BaseAppApplication.Companion.getInstance()):auth)
                .header("X-Access-Token",TokenUtils.getUserToken(BaseAppApplication.Companion.getInstance()))
                //目前版本号写死，后面要从gradle文件获取
                .header("App-Version",appVersion)
                .build();
        return chain.proceed(request);
    }
}
