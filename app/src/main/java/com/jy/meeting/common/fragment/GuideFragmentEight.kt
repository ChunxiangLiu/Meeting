package com.jy.meeting.common.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.jy.meeting.R
import com.jy.meeting.common.SelectPictrueActivity
import com.jy.meeting.common.adapter.CommonBaseRVAdapter
import com.jy.meeting.databinding.FragmentGuideEightBinding
import com.jy.meeting.view.dialog.FootPrintTipsDialog
import com.ximalife.library.Constant
import com.ximalife.library.base.BaseFragment
import com.ximalife.library.http.model.TxtWithPhotoModel
import com.ximalife.library.util.SettingUtils
import com.ximalife.library.view.CustomRoundAngleImageView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class GuideFragmentEight :
    BaseFragment<FragmentGuideEightBinding>(FragmentGuideEightBinding::inflate),
    FootPrintTipsDialog.onDialogItemClick {

    lateinit var textArray: TypedArray
    lateinit var imaArray: TypedArray

    var itemClick = false

    var txtWithPhotoModelList = mutableListOf<TxtWithPhotoModel>()//选择照片之前的集合

    lateinit var footPrintTipsDialog: FootPrintTipsDialog

    lateinit var pathList: java.util.ArrayList<String>//保存选择后图片的集合

    var isAddPhotoFlag = false
    var isAddPhotoPosition = 0//添加照片索引

    var slectPhotoModelList = ArrayList<String>()//用来做处理的集合

    var isRemorePhotoPosition = 0// 移除照片索引


    override fun initView(savedInstanceState: Bundle?) {

        footPrintTipsDialog = FootPrintTipsDialog(mActivity!!)
        footPrintTipsDialog.setClickListener(this)
    }

    override fun initData() {
        txtWithPhotoModelList = ArrayList()
        textArray = resources.obtainTypedArray(R.array.item_footprint_text)
        imaArray = resources.obtainTypedArray(R.array.item_footprint_img)
        for (i in 0 until textArray.length()) {
            var txtWithPhotoModel = TxtWithPhotoModel()
            txtWithPhotoModel.Intro = textArray.getString(i).toString()
            txtWithPhotoModel.DrawableId = imaArray.getResourceId(i, 0)
            txtWithPhotoModelList.add(txtWithPhotoModel)
        }

        var adapter = object : CommonBaseRVAdapter<TxtWithPhotoModel>(
            R.layout.item_foot_print,
            txtWithPhotoModelList
        ) {
            override fun convertData(helper: BaseViewHolder, item: TxtWithPhotoModel) {
                var itemText = helper.getView<TextView>(R.id.tv_title)

                SettingUtils.setTopImg(mActivity!!, itemText, item.DrawableId)
                if (helper.adapterPosition == 3 || helper.adapterPosition == 4) {
                    itemText.setText("")
                } else {
                    itemText.setText(item.Intro)
                }

                helper.itemView.setOnClickListener {
                    if (!itemClick) {
                        footPrintTipsDialog.show()
                    } else {
                        val intent = Intent(context, SelectPictrueActivity::class.java)

                        intent.putExtra(Constant.MAXSLECTEDNUM,
                            Constant.SELECT_PICTRUE_DEFAULT_MAX_NUM)
                        startActivityForResult(intent, 100)
                    }
                }
            }

        }
        binding.mRecyclerView.adapter = adapter

    }

    override fun initListener() {

    }

    override fun onClickOK() {
        itemClick = true
    }

    override fun onClickClose() {
        itemClick = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            //在这里获取到选择的图片

            pathList = data!!.getStringArrayListExtra(Constant.PICTRUELIST)





            if (pathList != null && pathList.size > 0) {

                for (i in 0 until pathList.size) {

                    if (isAddPhotoFlag && isAddPhotoPosition <= txtWithPhotoModelList.size) {

                        var txtWithPhotoModel = TxtWithPhotoModel()
                        txtWithPhotoModel.imgPath = pathList.get(i)
                        txtWithPhotoModel.time = System.currentTimeMillis()

                        txtWithPhotoModelList.set(isAddPhotoPosition, txtWithPhotoModel)

                        isAddPhotoPosition++
                    } else {
                        txtWithPhotoModelList.get(i).imgPath = pathList.get(i)
                        txtWithPhotoModelList.get(i).time = System.currentTimeMillis()
                    }

                    //累计选择的个数
                    slectPhotoModelList.add(pathList.get(i))
                }
                binding.mRecyclerView.visibility = View.GONE
                binding.photoRecyclerView.visibility = View.VISIBLE
                setSelectPictrueView(txtWithPhotoModelList)
            }


        }
    }

    private fun setSelectPictrueView(txtWithPhotoModelList: MutableList<TxtWithPhotoModel>) {
        var adapter = object : CommonBaseRVAdapter<TxtWithPhotoModel>(R.layout.item_select_pictrue,
            txtWithPhotoModelList) {
            override fun convertData(helper: BaseViewHolder, item: TxtWithPhotoModel) {
                var itemPrctrueLayout = helper.getView<FrameLayout>(R.id.item_prctrue_layout)
                var flNodataLayout = helper.getView<FrameLayout>(R.id.fl_nodata_layout)
                var ivItemImg = helper.getView<CustomRoundAngleImageView>(R.id.iv_item_img)
                var ivItemRemove = helper.getView<ImageView>(R.id.iv_item_remove)
                if (!TextUtils.isEmpty(item.imgPath)) {
                    itemPrctrueLayout.visibility = View.VISIBLE
                    flNodataLayout.visibility = View.GONE

                    Glide.with(mContext).load(item.imgPath).into(ivItemImg)
                } else {
                    itemPrctrueLayout.visibility = View.GONE
                    flNodataLayout.visibility = View.VISIBLE
                }

                helper.itemView.setOnClickListener {
                    if (!TextUtils.isEmpty(item.imgPath)) {


                    } else {
                        addSelectPictrue(helper.position)
                    }
                }

                ivItemRemove.setOnClickListener {
                    slectPhotoModelList.remove(item.imgPath)
                    var txtWithPhotoModel = TxtWithPhotoModel()
                    txtWithPhotoModel.imgPath = ""
                    txtWithPhotoModelList.set(helper.position, txtWithPhotoModel)
                    notifyDataSetChanged()
                }
            }
        }
        binding.photoRecyclerView.adapter = adapter
    }


    private fun addSelectPictrue(position: Int) {
        isAddPhotoFlag = true
        isAddPhotoPosition = position

        val intent = Intent(context, SelectPictrueActivity::class.java)

        intent.putExtra(Constant.MAXSLECTEDNUM,
            Constant.SELECT_PICTRUE_DEFAULT_MAX_NUM - slectPhotoModelList.size)
        startActivityForResult(intent, 100)

    }


}