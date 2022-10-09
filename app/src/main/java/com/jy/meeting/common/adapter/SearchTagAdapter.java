package com.jy.meeting.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jy.meeting.R;
import com.ximalife.library.http.model.TextModel;
import com.ximalife.library.view.CustomTextView;
import com.ximalife.library.view.flow.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

public class SearchTagAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private List<TextModel> condListBeans;

    public SearchTagAdapter(Context context) {
        this.mContext = context;
        this.condListBeans = new ArrayList<TextModel>();
    }

    public void setCondListBeans(List<TextModel> condListBeans) {
        this.condListBeans = condListBeans;
        if (this.condListBeans == null) {
            this.condListBeans = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return condListBeans == null ? 0 : condListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return condListBeans == null ? null : condListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_flow_text, null);
        CustomTextView tvZszCategoryItem = view.findViewById(R.id.text);
        tvZszCategoryItem.setText(condListBeans.get(position).getText());
        if (condListBeans.get(position).isCheck()) {
            tvZszCategoryItem.setTextColor(mContext.getResources().getColor(R.color.color_FC556B));
        } else {
            tvZszCategoryItem.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
        return view;
    }

    public void onlyAddAll(List<TextModel> sub_filter_category_list) {
        condListBeans.addAll(sub_filter_category_list);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<TextModel> sub_filter_category_list) {
        condListBeans.clear();
        onlyAddAll(sub_filter_category_list);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return condListBeans.get(position).isCheck();
    }
}
