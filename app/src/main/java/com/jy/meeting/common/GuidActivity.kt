package com.jy.meeting.common

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.jy.meeting.R
import com.jy.meeting.common.adapter.XFragmentAdapter
import com.jy.meeting.common.fragment.*
import com.jy.meeting.databinding.ActivityGuidBinding
import com.ximalife.library.base.BaseActivity
import com.ximalife.library.util.event.EventBusCode
import com.ximalife.library.util.event.EventMessage
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class GuidActivity : BaseActivity<ActivityGuidBinding>(ActivityGuidBinding::inflate) {

    var fragmentList = ArrayList<Fragment>()

    lateinit var imageViews: Array<ImageView?>

    lateinit var myViewPageAdapter: XFragmentAdapter

    var currentPosition = 0//当前fragment的索引

    override fun initView() {
        fragmentList.add(GuideFragmentOne())
        fragmentList.add(GuideFragmentTwo())
        fragmentList.add(GuideFragmentThree())
        fragmentList.add(GuideFragmentFour())
        fragmentList.add(GuideFragmentFive())
        fragmentList.add(GuideFragmentSix())
        fragmentList.add(GuideFragmentSeven())
        fragmentList.add(GuideFragmentEight())
        fragmentList.add(GuideFragmentNine())
        fragmentList.add(GuideFragmentTen())
        fragmentList.add(GuideFragmentEleven())
        fragmentList.add(GuideFragmentTwelve())

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
        binding.viewPager.adapter = myViewPageAdapter
    }


    override fun initListener() {
        //ViewPager滑动Pager监听
        binding.viewPager.setOnPageChangeListener(object : OnPageChangeListener {
            //滑动过程中的回调
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            /**
             * 设置按钮最后一页显示，其他页面隐藏
             *
             * @param position
             */
            override fun onPageSelected(position: Int) {
                //判断当前是在那个page，就把对应下标的ImageView原点设置为选中状态的图片
                for (i in imageViews.indices) {
                    imageViews[position]!!.setBackgroundResource(R.drawable.drawable_guide_point_selected)
                    if (position != i) {
                        imageViews[i]!!.setBackgroundResource(R.drawable.drawable_guide_point_unselected)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessageDel(message: EventMessage<String>?) {
        if (message == null) return
        when (message.getAction()) {
            EventBusCode.FRAGMENT_GUIDE_NEXT -> {
                if (currentPosition >= fragmentList.size - 1) {
                    currentPosition = fragmentList.size - 1;
                    return
                }
                currentPosition++
                binding.viewPager.currentItem = currentPosition
            }
            EventBusCode.FRAGMENT_GUIDE_TOP -> {
                if (currentPosition <= 0) {
                    currentPosition = 0;
                    return
                }
                currentPosition--
                binding.viewPager.currentItem = currentPosition
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


}