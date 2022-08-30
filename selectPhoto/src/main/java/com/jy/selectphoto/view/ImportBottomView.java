package com.jy.selectphoto.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jy.selectphoto.R;
import com.jy.selectphoto.bean.ImagineBean;

import java.util.ArrayList;

public class ImportBottomView extends SimpleLinearLayout {
    int maxSelectedNum = 8;
    TextView daoruBtn;
    ImportBottomRycView bottomRcyview;
    TextView num;
    private BottomListener listener;
    private ArrayList<ImagineBean> list = new ArrayList<>();

    public ImportBottomView(Context context) {
        super(context);
    }

    public ImportBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initViews() {
        super.initViews();
        contentView = inflate(mContext, R.layout.layout_import_bottom, this);
        daoruBtn = contentView.findViewById(R.id.daoru_btn);
        bottomRcyview = contentView.findViewById(R.id.bottom_rcyview);
        num = contentView.findViewById(R.id.num);
        daoruBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && list.size() > 0) {
                    listener.onOK();
                }
            }
        });
    }

    public void initView(ArrayList<ImagineBean> imageBeans) {
        list.clear();
        list.addAll(imageBeans);
        ImportBottomView.this.post(new Runnable() {
            @Override
            public void run() {
                bottomRcyview.setRecycleList(list);
                daoruBtn.setBackgroundResource(list.size() > 0 ? R.drawable.daoru_sty : R.drawable.daoru_sty2);
                daoruBtn.setTextColor(list.size() > 0 ? Color.rgb(255, 255, 255) : Color.argb(80, 255, 255, 255));
                num.setText(list.size() + "/" + maxSelectedNum + "（最多选择" + maxSelectedNum + "个素材）");

            }
        });
    }


    public void setListerner(BottomListener listener) {
        this.listener = listener;

    }

    public interface BottomListener {
        void onOK();
    }

    public int getMaxSelectedNum() {
        return maxSelectedNum;
    }

    public void setMaxSelectedNum(int maxSelectedNum) {
        this.maxSelectedNum = maxSelectedNum;
        num.setText(list.size() + "/" + maxSelectedNum + "（最多选择" + maxSelectedNum + "个素材）");
    }
}
