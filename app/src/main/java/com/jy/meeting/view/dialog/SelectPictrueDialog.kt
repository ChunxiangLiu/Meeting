package com.jy.meeting.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import com.jy.meeting.R
import com.jy.meeting.databinding.DialogSelectPictrueBinding
import com.ximalife.library.http.model.ImagineBean
import com.ximalife.library.http.model.TopTitleBean
import com.ximalife.library.util.DisplayUtil
import java.io.File
import java.util.*

class SelectPictrueDialog : Dialog {
    lateinit var binding: DialogSelectPictrueBinding


    private lateinit var clickListener: onDialogItemClick

    var mediaMap: MutableMap<String, ArrayList<ImagineBean>> =
        HashMap<String, ArrayList<ImagineBean>>()

    var topList: ArrayList<TopTitleBean> = ArrayList<TopTitleBean>()


    constructor(context: Context) : this(context, 0)

    constructor(mContext: Context, themeResId: Int) : super(mContext, R.style.dialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSelectPictrueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setLayout(
            DisplayUtil.getScreenWidth(context),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window!!.setGravity(Gravity.BOTTOM)
        setCanceledOnTouchOutside(false)

        initData()

        initListener()


    }

    private fun initData() {
        getMediaList()

        if (mediaMap != null && mediaMap.size > 0) {

        }

    }

    //获取相册所有图片
    private fun getMediaList() {
        //获取视频文件
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val videoInfo = ImagineBean()
            videoInfo.mediaType = 1
            videoInfo.filePath =
                cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
            videoInfo.name =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
            videoInfo.playtime =
                cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
            videoInfo.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
            val file = File(videoInfo.filePath)
            videoInfo.time = file.lastModified()
            try {
                videoInfo.rootName = file.getParentFile().getName()
            } catch (e: Exception) {
                videoInfo.rootName = "未知名称"
            }
            if (mediaMap.containsKey("相机胶卷")) {
                mediaMap["相机胶卷"]!!.add(videoInfo)
            } else {
                val list = ArrayList<ImagineBean>()
                list.add(videoInfo)
                mediaMap["相机胶卷"] = list
            }
            if (mediaMap.containsKey(videoInfo.rootName)) {
                mediaMap[videoInfo.rootName]!!.add(videoInfo)
            } else {
                val list = ArrayList<ImagineBean>()
                list.add(videoInfo)
                mediaMap[videoInfo.rootName] = list
            }
            // LogUtil.show("videoInfo==" + videoInfo.toString());
        }
        cursor.close()
        //获取图片文件
        val cursor2 = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (cursor2!!.moveToNext()) {
            val videoInfo = ImagineBean()
            videoInfo.mediaType = 2
            videoInfo.filePath =
                cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Media.DATA))
            videoInfo.name =
                cursor2.getString(cursor2.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE))
            videoInfo.size = cursor2.getLong(cursor2.getColumnIndex(MediaStore.Images.Media.SIZE))
            val file = File(videoInfo.filePath)
            videoInfo.time = file.lastModified()
            if (file.getParentFile() != null) {
                videoInfo.rootName = file.getParentFile().getName()
            } else {
                videoInfo.rootName = ""
            }
            if (mediaMap.containsKey("相机胶卷")) {
                mediaMap["相机胶卷"]!!.add(videoInfo)
            } else {
                val list = ArrayList<ImagineBean>()
                list.add(videoInfo)
                mediaMap["相机胶卷"] = list
            }
            if (mediaMap.containsKey(videoInfo.rootName)) {
                mediaMap[videoInfo.rootName]!!.add(videoInfo)
            } else {
                val list = ArrayList<ImagineBean>()
                list.add(videoInfo)
                mediaMap[videoInfo.rootName] = list
            }
        }
        cursor2.close()
        for ((key, veList) in mediaMap) {
            if (TextUtils.equals("相机胶卷", key)) {
                topList.add(0, TopTitleBean(veList[0].filePath, key, veList.size))
            } else {
                topList.add(TopTitleBean(veList[0].filePath, key, veList.size))
            }
        }

    }

    private fun initListener() {
        binding.ivDialogClaose.setOnClickListener {
            dismiss()
        }

    }

    @JvmName("setClickListener1")
    fun setClickListener(listener: onDialogItemClick) {
        this.clickListener = listener
    }


    interface onDialogItemClick {
        fun onClickItem(item: String)
    }


}

private operator fun <K, V> MutableMap<K, V>.set(rootName: K?, value: V) {

}
