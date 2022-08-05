package com.ximalife.library.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

class ScreenUtil {

    companion object {


        /**
         * 修改状态栏文字颜色，这里小米，魅族区别对待。
         */
        fun setLightStatusBar(activity: Activity, dark: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                when (RomUtils.getLightStatusBarAvailableRomType()) {
                    RomUtils.AvailableRomType.MIUI -> MIUISetStatusBarLightMode(activity, dark)

                    RomUtils.AvailableRomType.FLYME -> setFlymeLightStatusBar(activity, dark)

                    RomUtils.AvailableRomType.ANDROID_NATIVE -> setAndroidNativeLightStatusBar(activity, dark)
                }
            }
        }


        fun setAndroidNativeStatusBar(activity: Activity, dark: Boolean) {
            val decor = activity.window.decorView
            if (dark) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }


        /**
         * 充满屏幕
         */
        fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
            val decor = activity.window.decorView
            if (dark) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }


        fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
            var result = false
            val window = activity.window
            if (window != null) {
                val clazz = window.javaClass
                try {
                    var darkModeFlag = 0
                    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
                        //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                        if (dark) {
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        } else {
                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        }
                    }
                } catch (e: Exception) {

                }

            }
            return result
        }

        private fun setFlymeLightStatusBar(activity: Activity?, dark: Boolean): Boolean {
            var result = false
            if (activity != null) {
                try {
                    val lp = activity.window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java
                            .getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    activity.window.attributes = lp
                    result = true
                } catch (e: Exception) {
                }

            }
            return result
        }




    }

}