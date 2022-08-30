package com.jy.selectphoto.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jy.selectphoto.adapter.ScanImportImageAdapter;
import com.jy.selectphoto.bean.ImagineBean;

import java.util.ArrayList;


/**
 * Created by user on chenzy 2018/5/30 扫描视频
 */

public class ImportImageRycView extends RecyclerView {
    private Context mContext;
    public ScanImportImageAdapter adapter;

    public ImportImageRycView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        adapter = new ScanImportImageAdapter(mContext);
        final GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == 1) {
                    return 3;
                }
                return 1;
            }
        });
        setLayoutManager(layoutManager);
        com.jy.selectphoto.view.ItemDecoration itemDecoration = new com.jy.selectphoto.view.ItemDecoration(mContext, 0x000000, 8f, 8f);
        itemDecoration.setDrawBorderTopAndBottom(false);
        addItemDecoration(itemDecoration);
//        addItemDecoration(new SpacesItemDecoration(10));
        setItemAnimator(new DefaultItemAnimator());
        //((DefaultItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);//解决动画的闪屏
        setHasFixedSize(true);
        setAdapter(adapter);
    }

    //加载推荐数据
    public void setRecycleList(ArrayList<ImagineBean> list) {
        adapter.setList(list);
    }

    public void notifyDataSetEdit() {
        adapter.notifyDataSetEdit();
    }

    //设置清除数据
    public void clearData() {
        adapter.clear();
    }

    public int getCount() {
        return adapter != null ? adapter.getItemCount() : 0;
    }

    @Override
    public ScanImportImageAdapter getAdapter() {
        return adapter;
    }

}
