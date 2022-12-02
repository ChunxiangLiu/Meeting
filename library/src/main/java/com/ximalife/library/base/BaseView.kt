package com.ximalife.library.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.meet.library.R
import com.umeng.analytics.MobclickAgent
import com.ximalife.library.dialog.AlertDialogCustom
import com.ximalife.library.http.IIBaseViewModelEventObserver
import com.ximalife.library.http.viewmodel.BaseViewModel
import com.ximalife.library.util.ScreenUtil
import com.ximalife.library.util.Util
import com.ximalife.library.util.event.EvenBusUtil
import com.ximalife.library.util.event.EventMessage
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    AppCompatActivity(), IIBaseViewModelEventObserver {


    private var loadDialog: AlertDialogCustom? = null
    var mToolbar: TitleBar? = null
    var mImmersionBar: ImmersionBar? = null

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this.isTaskRoot()) {
            var intent = getIntent();
            if (intent != null) {
                var action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish()
                    return
                }
            }
        }
        /**
         * 设置软键盘的弹起模式，防止顶起布局
         */
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        binding = inflate(layoutInflater)
        setContentView(binding.root)
        //ScreenUtil.setLightStatusBar(this, setStatusDark())
//        AndroidBug5497Workaround.assistActivity(this)
        //注册事件
        EvenBusUtil.instance().register(this)
        initStatusBar()
        initViewModelEvent()
        initView()
        initListener()
//        initSoftKeyboard()
    }

    open fun getToolBar() {}
    open fun initView() {}
    open fun initListener() {}

    /**
     * 状态栏文字 true是白色 false是黑色
     */
    open fun setStatusDark() = true
    open fun setToolBarKeyboardEnable() = true

    fun changeStatusDark(bool: Boolean = true) =
        ScreenUtil.setAndroidNativeStatusBar(this, bool)

    override fun getLContext(): Context = this

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun finishView() {
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(message: EventMessage<Objects>?) {
    }

    var isImmersionBarEnabled = true

    open fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        if (isImmersionBarEnabled) {
            mImmersionBar!!.statusBarDarkFont(true) ////状态栏字体是深色，不写默认为亮色
                .keyboardEnable(false) // //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .fitsLayoutOverlapEnable(true)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                //.navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .init()
        } else {
            mImmersionBar!!
                .keyboardEnable(false) // //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .statusBarDarkFont(true) ////状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true) //解决布局和状态栏重叠
                .fitsLayoutOverlapEnable(false)
                .navigationBarColor(R.color.white) //导航栏颜色，不写默认黑色
                .statusBarColor(R.color.white) //状态栏颜色，不写默认透明色
                .init()
        }
    }


    override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart("BaseActivity")
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd("BaseActivity")
        MobclickAgent.onPause(this)
//        KeyBoardManager.hideKeyboard(getContentView())
    }


    override fun onDestroy() {
        super.onDestroy()
        EvenBusUtil.instance().unRegister(this)
//        KeyBoardManager.hideKeyboard(getContentView())
    }


    var dlg: ProgressDialog? = null

    /**
     * 加载进度条
     */
    fun showLoadingDialog(message: String) {
        if (dlg == null) {
            dlg = ProgressDialog(this)
            dlg!!.setCancelable(true)
            dlg!!.setCanceledOnTouchOutside(false)
        }
        dlg!!.setMessage(message)
        if (!isFinishing && !isDestroyed()) {
            if (dlg != null) {
                dlg!!.show()
            }
        }
    }


    fun dismissDialog() {
        if (dlg != null && dlg!!.isShowing()) {
            dlg!!.dismiss()
        }
    }


    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = AlertDialogCustom(getLContext(), true)
//            loadDialog!!.setCancelable(false)
//            loadDialog!!.setCanceledOnTouchOutside(false)
        }

        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }


    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }


    override fun pop() {

    }

    override fun toLogin() {
    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) = ViewModelProvider(this).get(clazz)

    //获取状态栏高度
    protected fun getStatusBarHeight(): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Util.closeKeyboard(this)
    }

    open fun getContentView(): ViewGroup {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }


}

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    Fragment(), IIBaseViewModelEventObserver {
    private var loadDialog: AlertDialogCustom? = null
    protected var rootView: View? = null
    var mActivity: FragmentActivity? = null


    lateinit var v: View
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModelEvent()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(layoutInflater)
        v = _binding!!.root
        mActivity = activity
        return _binding!!.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(message: EventMessage<Objects>?) {
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState)
        initData()
        initListener()
        //注册事件
        EvenBusUtil.instance().register(this)
    }

    //获取布局文件
//    protected abstract fun getLayoutResource(): Int

    //初始化view
    protected abstract fun initView(savedInstanceState: Bundle?)
    open fun initView() {}
    open fun initData() {}
    open fun initListener() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity
    }


    override fun toLogin() {
    }

    override fun getLContext(): Context = this.context!!

    override fun getLifecycleOwner(): LifecycleOwner = this


    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = AlertDialogCustom(getLContext(), true)
//            loadDialog!!.setCancelable(false)
//            loadDialog!!.setCanceledOnTouchOutside(false)
        }
        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }


    override fun finishView() {
        activity?.finish()
    }

    override fun pop() {

    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) =
        ViewModelProvider(this).get(clazz)

    //获取状态栏高度
    protected fun getStatusBarHeight(): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    var dlg: ProgressDialog? = null

    /**
     * 加载进度条
     */
    fun showLoadingDialog(message: String) {
        if (dlg == null) {
            dlg = ProgressDialog(activity)
            dlg!!.setCancelable(false)
            dlg!!.setCanceledOnTouchOutside(false)
        }
        dlg!!.setMessage(message)
        if (dlg != null) {
            dlg!!.show()
        }
    }


    fun dismissDialog() {
        if (dlg != null && dlg!!.isShowing()) {
            dlg!!.dismiss()
        }
    }

}
