package com.jy.meeting.common.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.jy.meeting.R;
import com.ximalife.library.http.model.TxtWithPhotoModel;
import com.ximalife.library.view.CustomRoundAngleImageView;
import com.ximalife.library.view.CustomTextView;

import java.util.ArrayList;

public class FootPrintAdapter extends PagerAdapter {

    private ArrayList<TxtWithPhotoModel> mData;

    private Context mContext;

    @Override
    public int getCount() {
        return mData.size();// 返回数据的个数
    }

    public FootPrintAdapter(Context ctx, ArrayList<TxtWithPhotoModel> data) {
        this.mContext = ctx;
        this.mData = data;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;// 过滤和缓存的作用

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.item_dailog_foot_print, null);

        CustomRoundAngleImageView imageView = view.findViewById(R.id.item_img);
//        CustomTextView itemText = view.findViewById(R.id.ct_item_text);


        if (!TextUtils.isEmpty(mData.get(position).getImgPath())) {
            Glide.with(mContext).load(mData.get(position).getImgPath()).into(imageView);
        } else {
            Glide.with(mContext).load(mData.get(position).getDrawableId()).into(imageView);
        }


//        itemText.setText(mData.get(position).getIntro());

        container.addView(view);//添加到父控件
        return view;
    }
}
