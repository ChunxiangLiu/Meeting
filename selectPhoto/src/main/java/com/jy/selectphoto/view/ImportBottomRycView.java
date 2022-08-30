package com.jy.selectphoto.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.selectphoto.adapter.ScanImportBottomAdapter;
import com.jy.selectphoto.bean.ImagineBean;

import java.util.ArrayList;


/**
 * Created by user on chenzy 2018/5/30 扫描视频
 */

public class ImportBottomRycView extends RecyclerView {
    private Context mContext;
    public ScanImportBottomAdapter adapter;

    public ImportBottomRycView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        adapter = new ScanImportBottomAdapter(mContext);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(layoutManager);
        setItemAnimator(new DefaultItemAnimator());
        //((DefaultItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);//解决动画的闪屏
        setHasFixedSize(true);
        setAdapter(adapter);
    }

    //加载推荐数据
    public void setRecycleList(ArrayList<ImagineBean> list) {
        adapter.setList(list);
    }

    //设置清除数据
    public void clearData() {
        adapter.clear();
    }

    public int getCount() {
        return adapter != null ? adapter.getItemCount() : 0;
    }
}
