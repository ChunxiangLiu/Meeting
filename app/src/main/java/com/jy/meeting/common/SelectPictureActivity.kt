package com.jy.meeting.common

import android.content.Intent
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jy.meeting.databinding.DialogSelectPictrueBinding
import com.jy.meeting.view.utils.HiddenAnimUtil
import com.jy.selectphoto.bean.ImagineBean
import com.jy.selectphoto.bean.TopTitleBean
import com.jy.selectphoto.util.event.EventBusCode
import com.jy.selectphoto.util.event.EventMessage
import com.jy.selectphoto.view.ImportBottomView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.ximalife.library.Constant
import com.ximalife.library.base.BaseActivity
import com.ximalife.library.util.DisplayUtil
import com.ximalife.library.util.ThreadManager
import kotlinx.android.synthetic.main.dialog_select_pictrue.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SelectPictureActivity :
    BaseActivity<DialogSelectPictrueBinding>(DialogSelectPictrueBinding::inflate) {

    var MaxSlectedNum = 0
    var NeedClosedThis = true


    var mediaMap: MutableMap<String, ArrayList<ImagineBean>> =
        HashMap<String, ArrayList<ImagineBean>>()
    var topList: ArrayList<TopTitleBean> = ArrayList<TopTitleBean>()

    private val format = SimpleDateFormat("yyyy.MM.dd")

    var titleTime: String = ""

    private var runnable: Runnable? = null


    var isTopShow = false


    override fun initView() {
        isImmersionBarEnabled = false
        var layoutParams = binding.rlRootview.getLayoutParams() as FrameLayout.LayoutParams

        layoutParams.width = DisplayUtil.getScreenWidth(this)
        layoutParams.setMargins(0, DisplayUtil.dip2px(60f), 0, 0)
        binding.rlRootview.layoutParams = layoutParams


        MaxSlectedNum = intent.getIntExtra(Constant.MAXSLECTEDNUM, 1)
        NeedClosedThis = intent.getBooleanExtra(Constant.NEEDCLOSETHIS, true)
        binding.importImageRycView.getAdapter()!!.setMaxSelectedNum(MaxSlectedNum)
        binding.bottomView.setMaxSelectedNum(MaxSlectedNum)





        binding.swipeLayout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                startScan()
            }

        })

        binding.swipeLayout.setEnableAutoLoadMore(false)
        binding.swipeLayout.autoRefresh()



        binding.bottomView.setVisibility(if (MaxSlectedNum > 1) View.VISIBLE else View.GONE)


        binding.bottomView.setListerner(object : ImportBottomView.BottomListener {
            override fun onOK() {
                setResultAvtivity()
            }

        })


    }

    override fun initListener() {
        binding.title.setOnClickListener {
            initTitleView(isTopShow)
        }

    }


    fun startScan() {
        mediaMap.clear()
        topList.clear()
        ThreadManager.getInstance().execute(ScanMediaRunnable())
    }

    internal inner class ScanMediaRunnable : Runnable {

        override fun run() {
            getMediaList()
            runOnUiThread {
                binding.topTitleRecView.setRecycleList(topList)
                initFileView(mediaMap["相机胶卷"])
                binding.title.setText("相机胶卷")
                binding.swipeLayout.finishRefresh()
            }
        }
    }

    /**
     * 获取图片集合
     */
    private fun getMediaList() {
        val cursor2 = this.contentResolver.query(
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
            if (file.parentFile != null) {
                try {
                    videoInfo.rootName = file.parentFile.name
                } catch (e: Exception) {
                    videoInfo.rootName = ""
                }
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
        for ((key, value) in mediaMap) {
            if (TextUtils.equals("相机胶卷", key)) {
                topList.add(0, TopTitleBean(value.size, value[0].getFilePath(), key))
            } else {
                topList.add(TopTitleBean(value.size, value[0].getFilePath(), key))
            }
        }
    }

    //对文件进行排序
    fun initFileView(ALLList: ArrayList<ImagineBean>?) {
        var ALLList = ALLList
        val list = ArrayList<ImagineBean>()
        if (ALLList == null) {
            ALLList = ArrayList<ImagineBean>()
        }
        for (bean in ALLList) {
            if (bean.mediaType === 2) {
                if (binding.importImageRycView.adapter.haveSelectedBean.contains(bean)) {
                    bean.isSelect = true
                }
                list.add(bean)
            }
        }

        Collections.sort<ImagineBean>(list,
            Comparator<ImagineBean> { o1: ImagineBean, o2: ImagineBean ->
                if (o1.getTime() < o2.getTime()) return@Comparator 1
                if (o1.getTime() == o2.getTime()) 0 else -1
            })
//        val list2 = ArrayList<ImagineBean>()
//        for (bean in list) {
//            val date = format.format(Date(bean.time))
//            if (!TextUtils.equals(titleTime, date)) {
//                titleTime = date
//                val bea = ImagineBean()
//                bea.time = bean.time
//                bea.mediaType = 3
//                list2.add(bea)
//            }
//            list2.add(bean)
//        }
        binding.importImageRycView.setRecycleList(list)
    }

    private fun setResultAvtivity() {
        if (NeedClosedThis) { //单张模式
            //选择好图片进行压缩
            val imagineBeanArrayList: ArrayList<ImagineBean> =
                binding.importImageRycView.getAdapter()!!.getHaveSelectedBean()
            val fileList = ArrayList<String>()
            for (imagineBean in imagineBeanArrayList) {
                fileList.add(imagineBean.getFilePath())
            }
            if (fileList != null && fileList.size > 0) {
                showLoading("")
                setToactivtyData(fileList)
            }
        } else {
            val list: ArrayList<ImagineBean> =
                binding.importImageRycView.getAdapter()!!.getHaveSelectedBean()
            if (list != null && list.size > 0) {
                getImgArrayList(list[0].filePath)
                binding.importImageRycView.getAdapter()!!.getHaveSelectedBean().clear()
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMesg(message: EventMessage<*>) {
        if (message == null) return
        when (message.getAction()) {
            EventBusCode.SCANIMAGE_ONCLICK ->
                if (MaxSlectedNum <= 1) {
                    //单选
                    setResultAvtivity()
                } else {
                    //多选
                    importImageRycView.notifyDataSetEdit()
                    binding.bottomView.initView(importImageRycView.getAdapter()!!.haveSelectedBean)
                }
            EventBusCode.SCANIMAGE_SELECTED_PHOTO_ALBUM -> if (message.getData() != null) {
                val bean = message.getData() as TopTitleBean
                binding.title.setText(bean.name)
                initTitleView(isTopShow)
                initFileView(mediaMap[bean.name])
            }
        }
    }


    private fun setToactivtyData(data: ArrayList<String>) {
        val intent = Intent()
        intent.putStringArrayListExtra(Constant.PICTRUELIST, data)
        setResult(RESULT_OK, intent)
        dismissLoading()
        finish()
    }


    private fun getImgArrayList(filePath: String): ArrayList<ImagineBean> {
        val resultList = ArrayList<ImagineBean>()
        if (!TextUtils.isEmpty(filePath)) {
            val imagineBean = ImagineBean()
            imagineBean.setFilePath(filePath)
            resultList.add(imagineBean)
        }
        return resultList
    }

    private fun initTitleView(isTopShow: Boolean) {
        this.isTopShow = !isTopShow
        val height: Int = binding.swipeLayout.getHeight()
        HiddenAnimUtil.newInstance(this, binding.topTitleRecView, binding.titleIv, height).toggle()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (runnable != null) {
            ThreadManager.getInstance().cancel(runnable)
        }
    }


}