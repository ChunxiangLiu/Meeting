package com.jy.meeting.common.fragment

import android.Manifest
import android.os.Bundle
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.jy.meeting.R
import com.jy.meeting.databinding.FragmentGuideFiveBinding
import com.jy.meeting.databinding.FragmentGuideFourBinding
import com.jy.meeting.databinding.FragmentGuideSevenBinding
import com.jy.meeting.view.dialog.AvatarTipsDialog
import com.ximalife.library.base.BaseFragment

class GuideFragmentSeven :
    BaseFragment<FragmentGuideSevenBinding>(FragmentGuideSevenBinding::inflate) {

    private val mPermissionList = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {
        binding.flAvatarLayout.setOnClickListener {
            XXPermissions.with(this).permission(mPermissionList)
                .request(object : OnPermissionCallback {
                    override fun onGranted(
                        permissions: MutableList<String>?,
                        all: Boolean
                    ) {
                        if (all) {
                            var avatarTipsDialog = AvatarTipsDialog(mActivity!!)
                            avatarTipsDialog.show()

                        } else {
                            showToast(resources.getString(R.string.permission_fail_tips))
                        }

                    }

                    override fun onDenied(
                        permissions: MutableList<String>?,
                        never: Boolean
                    ) {
                        if (never) {
                            showToast(resources.getString(R.string.permission_fail_tips))
                        }

                    }

                })
        }
    }
}