package com.ximalife.library.http

import android.util.Log
import com.readystatesoftware.chuck.ChuckInterceptor
import com.ximalife.library.BuildConfig
import com.ximalife.library.base.BaseAppApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class RetrofitManagement private constructor() {

    companion object {
        private const val READ_TIMEOUT: Long = 40000
        private const val WRITE_TIMEOUT: Long = 40000
        private const val CONNECT_TIMEOUT: Long = 15000

        val instance: RetrofitManagement by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManagement()
        }
    }

    private val serviceMap = ConcurrentHashMap<String, Any>()
    private val serviceTokenMap = ConcurrentHashMap<String, Any>()

//    private fun createRetrofit(url: String): Retrofit {
//        val builder = OkHttpClient.Builder()
//            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
//            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
//            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
//            .retryOnConnectionFailure(true)
//
////        val httpLoggingInterceptor = HttpLoggingInterceptor()
////        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
////        builder.addInterceptor(httpLoggingInterceptor)
//
//        if (BuildConfig.DEBUG) {
//            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
//                Log.d(
//                    "RETROFIT_LOG",
//                    "OkHttp====Message:$message"
//                )
//            })
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            builder.addInterceptor(interceptor)
//            builder.addInterceptor(ChuckInterceptor(BaseAppApplication.context))
//        }
//
//
//        val client = builder.build()
//        return Retrofit.Builder()
//            .client(client)
//            .baseUrl(url)
////            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(
//                CustomConverterFactory.create(
//                    "errorCode",
//                    "message", 200, 10006
//                )
//            )
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//    }

    private fun createRetrofit(url: String ,clienAuthToken: String = ""): Retrofit {
        val builder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(VersionInterceptor(clienAuthToken))
//            .addInterceptor(SignInterceptor(token, deviceid))


        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    "RETROFIT_LOG",
                    "OkHttp====Message:$message"
                )
            })
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
            builder.addInterceptor(ChuckInterceptor(BaseAppApplication.getInstance()))
        }


        val client = builder.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    //多个URL的service管理器
    fun <T : Any> getService(clz: Class<T>, host: String): T {
        if (serviceMap.containsKey(host)) {
            val obj = serviceMap[host] as? T
            obj?.let {
                return obj
            }
        }

        val value = createRetrofit(host).create(clz)
        serviceMap[host] = value
        return value
    }

    fun <T : Any> getService(clz: Class<T>, host: String, authorization: String): T {
//        if (serviceTokenMap.containsKey(host)) {
//            val obj = serviceTokenMap[host] as? T
//            obj?.let {
//                return obj
//            }
//        }

        val value = createRetrofit(host,authorization).create(clz)
        return value
    }
}