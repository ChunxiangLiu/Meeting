package com.ximalife.library.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.content.FileProvider
import java.io.File

object ShareUtil {

    /**
     * 分享单张图片/
     */
    fun shareSingleFile(context: Context, filePath: String?) {
        val shareFile = File(filePath)
        if (!shareFile.exists()) return
        val intent = Intent(Intent.ACTION_SEND)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentUri = FileProvider.getUriForFile(
                context,
                context.packageName + ".fileprovider",
                shareFile
            )
            intent.putExtra(Intent.EXTRA_STREAM, contentUri)
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(shareFile))
        }

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        intent.type = "image/*"
        context.startActivity(Intent.createChooser(intent, "分享到："))
    }

    /**
     * 系统分享文本
     *
     * @param context 上下文
     */
    open fun shareContext(context: Context, content: String) {
        if (TextUtils.isEmpty(content)) return
        var intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}