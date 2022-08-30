package com.jy.meeting.common

import com.jy.meeting.R
import com.jy.meeting.databinding.ActivityTestBinding
import com.jy.meeting.view.DrawCircle
import com.ximalife.library.base.BaseActivity


class TestActivity : BaseActivity<ActivityTestBinding>(ActivityTestBinding::inflate) {

    override fun initView() {

        var circle = findViewById<DrawCircle>(R.id.circle)

//        circle.setZoomEnabled(true);
//        circle.getZoomer()!!.setReadMode(false);
//        circle.getZoomer()!!.zoom(3f, true)
//        circle.displayResourceImage(R.mipmap.ic_guid_bg)

    }

}
