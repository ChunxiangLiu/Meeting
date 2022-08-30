package com.jy.selectphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jy.selectphoto.R;
import com.jy.selectphoto.bean.TopTitleBean;
import com.jy.selectphoto.util.event.EvenBusUtil;
import com.jy.selectphoto.util.event.EventBusCode;
import com.jy.selectphoto.util.event.EventMessage;

import java.util.ArrayList;
import java.util.List;


//扫描图片adapter-  by chenzy 2018/5/30
public class ScanTopTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TopTitleBean> imageBeans = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext = null;

    public ScanTopTitleAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<TopTitleBean> list) {
        this.imageBeans = list;
        notifyDataSetChanged();
    }


    public void clear() {
        imageBeans.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderScanView(mInflater.inflate(R.layout.item_top_title, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final TopTitleBean bean = imageBeans.get(position);
        if (holder instanceof HolderScanView) {
            initScanView((HolderScanView) holder, bean, position);
        }
    }

    //扫描图片加载
    private void initScanView(final HolderScanView holder, final TopTitleBean bean, final int position) {
        if (bean == null) return;
        RequestOptions optionssss = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        optionssss.dontAnimate();
        optionssss.error(R.mipmap.ic_all_document_empty);
        Glide.with(mContext).load(bean.firstPath).apply(optionssss).into(holder.vedio);
        holder.name.setText(bean.name);
        holder.num.setText("" + bean.num);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventMessage eventMessage = new EventMessage();
                eventMessage.setAction(EventBusCode.SCANIMAGE_SELECTED_PHOTO_ALBUM);
                eventMessage.setData(bean);
                EvenBusUtil.instance().postEventMesage(eventMessage);//通知文件列表刷新
//                EventBusUtils.post(new EventMessage(EventBusCode.SCANIMAGE_SELECTED_PHOTO_ALBUM, bean));
            }
        });
    }


    public List<TopTitleBean> getList() {
        return this.imageBeans;
    }

    @Override
    public int getItemCount() {
        return imageBeans.size();
    }

    class HolderScanView extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView vedio;
        TextView name;
        TextView num;

        public HolderScanView(View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.layout);
            vedio=itemView.findViewById(R.id.vedio_iv);
            name=itemView.findViewById(R.id.name);
            num=itemView.findViewById(R.id.num);
            itemView.setTag(this);
        }
    }
}

