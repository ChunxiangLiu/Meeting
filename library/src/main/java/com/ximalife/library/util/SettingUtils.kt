package com.ximalife.library.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.TextView
import com.ximalife.library.util.CameraThreadPool.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object SettingUtils {

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    fun getAppMetaData(ctx: Context): String? {
        var resultData: String? = null
        try {
            val packageManager = ctx.packageManager
            if (packageManager != null) {
                val applicationInfo = packageManager.getApplicationInfo(
                    ctx.packageName,
                    PackageManager.GET_META_DATA
                )
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString("UMENG_CHANNEL")
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return resultData
    }

    open fun getAppName(context: Context): String {
        val lableInfo: Int = context.getApplicationInfo().labelRes
        val textTitle: String = context.getString(lableInfo)
        return textTitle
    }


    /**
     * 在文字的左方设置图片
     */
    open fun setLeftImg(
        context: Context,
        textView: TextView?,
        drawableId: Int
    ) {
        var drawable: Drawable? = null
        drawable = context.resources.getDrawable(drawableId) // 找到资源图片
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) // 设置图片宽高
        textView?.setCompoundDrawables(drawable, null, null, null) // 设置到控件中
    }

    /**
     * 在文字的左方设置图片
     */
    fun setRightImg(
        context: Context,
        textView: TextView?,
        drawableId: Int
    ) {
        var drawable: Drawable? = null
        drawable = context.resources.getDrawable(drawableId) // 找到资源图片
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) // 设置图片宽高
        textView?.setCompoundDrawables(null, null, drawable, null) // 设置到控件中
    }


    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    var MIN_CLICK_DELAY_TIME = 1000
    var lastClickTime = 0L
    fun isFastClick(): Boolean {
        var flag = false
        var curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true
        }
        lastClickTime = curClickTime
        return flag
    }


    ///获得独一无二的Psuedo ID
    fun getUniquePsuedo(): String? {
        return UUID.randomUUID().toString()
    }


    fun getVerName(context: Context): String? {
        var verName: String? = ""
        try {
            verName =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verName
    }



    fun setTextCopyValue(context: Context, text: String){
        val cm =  context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(
            ClipData.newPlainText(
                null,
                text
            )
        )
    }



}