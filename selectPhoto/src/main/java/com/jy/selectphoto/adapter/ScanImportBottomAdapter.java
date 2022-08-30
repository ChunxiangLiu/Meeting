package com.jy.selectphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jy.selectphoto.R;
import com.jy.selectphoto.bean.ImagineBean;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


//扫描图片adapter-  by chenzy 2018/5/30
public class ScanImportBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ImagineBean> imageBeans = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext = null;
    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");

    public ScanImportBottomAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    public void setList(ArrayList<ImagineBean> list) {
        this.imageBeans = list;
        notifyDataSetChanged();
    }


    public void clear() {
        imageBeans.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderScanView(mInflater.inflate(R.layout.item_import_bottom, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ImagineBean bean = imageBeans.get(position);
        if (holder instanceof HolderScanView) {
            initScanView((HolderScanView) holder, bean, position);
        }
    }

    //扫描图片加载
    private void initScanView(final HolderScanView holder, final ImagineBean bean, final int position) {
        if (bean == null) return;
        RequestOptions optionssss = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        optionssss.dontAnimate();
        optionssss.error(R.mipmap.ic_all_document_empty);
        Glide.with(mContext).load(bean.filePath).apply(optionssss).into(holder.vedio);
        holder.time.setText(format.format(new Date(bean.playtime)));
        holder.time.setText("");
    }


    public ArrayList<ImagineBean> getList() {
        return this.imageBeans;
    }

    @Override
    public int getItemCount() {
        return imageBeans.size();
    }

    class HolderScanView extends RecyclerView.ViewHolder {
        ImageView vedio;
        TextView time;

        public HolderScanView(View itemView) {
            super(itemView);
            vedio=itemView.findViewById(R.id.vedio_iv);
            time=itemView.findViewById(R.id.time);
            itemView.setTag(this);
        }
    }

}

