package com.jy.meeting.common

import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.jy.meeting.R
import com.jy.meeting.common.adapter.XFragmentAdapter
import com.jy.meeting.common.fragment.GuideFragmentFour
import com.jy.meeting.common.fragment.GuideFragmntOne
import com.jy.meeting.common.fragment.GuideFragmntThree
import com.jy.meeting.common.fragment.GuideFragmntTwo
import com.jy.meeting.databinding.ActivityGuidBinding
import com.ximalife.library.base.BaseActivity
import com.ximalife.library.http.model.GuideMessageModel
import com.ximalife.library.util.event.EventBusCode
import com.ximalife.library.util.event.EventMessage
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class GuidActivity : BaseActivity<ActivityGuidBinding>(ActivityGuidBinding::inflate) {

    var fragmentList = ArrayList<Fragment>()

    lateinit var imageViews: Array<ImageView?>

    lateinit var myViewPageAdapter: XFragmentAdapter

    var userNikeName = ""

    var index = 0

    var currentItem = 0;

    override fun initView() {
        fragmentList.add(GuideFragmntOne())
        fragmentList.add(GuideFragmntTwo())
        fragmentList.add(GuideFragmntThree())
        fragmentList.add(GuideFragmentFour())

        setupWithPager(fragmentList, null)
        binding.viewPager.offscreenPageLimit = fragmentList.size
        binding.viewPager.setNoScroll(false)
        initPointer()
    }

    //初始化下面的小圆点的方法
    private fun initPointer() {
        //有多少个界面就new多长的数组
        imageViews = arrayOfNulls<ImageView>(fragmentList.size)
        for (i in imageViews.indices) {
            val imageView = ImageView(this)
            val params = LinearLayout.LayoutParams(20, 20)
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = 20
            }
            //设置控件的宽高
            imageView.layoutParams = params
            imageViews[i] = imageView
            //初始化第一个page页面的图片的原点为选中状态
            if (i == 0) {
                //表示当前图片
                imageViews[i]!!.setBackgroundResource(R.drawable.drawable_guide_point_selected)
                /**
                 * 在java代码中动态生成ImageView的时候
                 * 要设置其BackgroundResource属性才有效
                 * 设置ImageResource属性无效
                 */
            } else {
                imageViews[i]!!.setBackgroundResource(R.drawable.drawable_guide_point_unselected)
            }
            binding.guideLlContainer.addView(imageViews[i])
        }
    }


    /**
     * 绑定pager 和 Fragment
     */
    private fun setupWithPager(list: List<Fragment>, title: Array<String>?) {
        myViewPageAdapter = XFragmentAdapter(supportFragmentManager, list, title)
        binding.viewPager.setAdapter(myViewPageAdapter)
    }


    override fun initListener() {
        //ViewPager滑动Pager监听
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i("viewpage", "onPageScrolled : " + position);

            }

            override fun onPageSelected(position: Int) {
                Log.i("viewpage", "onPageSelected : " + position);

                for (i in imageViews.indices) {
                    imageViews.get(position)!!
                        .setBackgroundResource(R.drawable.drawable_guide_point_selected)
                    if (position != i) {
                        imageViews.get(i)!!
                            .setBackgroundResource(R.drawable.drawable_guide_point_unselected)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i("viewpage", "onPageScrollStateChanged : " + state);
            }

        })


        binding.tvNext.setOnClickListener {
            if (myViewPageAdapter != null) {
                if (currentItem > fragmentList.size) return@setOnClickListener
                if (currentItem == 0) {
                    if (!TextUtils.isEmpty(userNikeName)) {
                        binding.tvGenderAgeTips.visibility = View.VISIBLE
                    }
                } else if (currentItem == 1) {
                    binding.tvGenderAgeTips.visibility = View.GONE
                }
                binding.viewPager.setCurrentItem(currentItem + 1)
                myViewPageAdapter.notifyDataSetChanged()
                currentItem++
            }
        }

        binding.ivTop.setOnClickListener {
            if (myViewPageAdapter != null) {
                if (currentItem == 0 || currentItem > fragmentList.size) return@setOnClickListener
                if (currentItem == 1) {
                    binding.tvGenderAgeTips.visibility = View.GONE
                } else if (currentItem == 2) {
                    binding.tvGenderAgeTips.visibility = View.VISIBLE
                }
                binding.viewPager.setCurrentItem(currentItem - 1)
                myViewPageAdapter.notifyDataSetChanged()
                currentItem--
            }
        }

    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            //do something.
            true
        } else {
            super.dispatchKeyEvent(event)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessageDel(message: EventMessage<String>?) {
        if (message == null) return
        when (message.getAction()) {
            EventBusCode.FRAGMNET_GUIDE_ONE -> {
                var date = message.getData() as GuideMessageModel
                userNikeName = date.text
                currentItem = date.position
//                when (date.position) {
//                    0 -> {
//                        if (!TextUtils.isEmpty(date.text)) {
//                            binding.tvNext.setBackgroundResource(R.mipmap.ic_dialog_register_or_login_bt_bg)
//                        } else {
//                            binding.tvNext.setBackgroundResource(R.drawable.drawable_guid_bt_bg)
//                        }
//                    }
//                }
            }
        }
    }


}