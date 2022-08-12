package com.jy.meeting.common.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 通用Adapter
 * @param <T>
 */
public abstract class CommonBaseRVAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public CommonBaseRVAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        convertData(helper,item);
    }


    protected abstract void convertData(BaseViewHolder helper, T item);
}
