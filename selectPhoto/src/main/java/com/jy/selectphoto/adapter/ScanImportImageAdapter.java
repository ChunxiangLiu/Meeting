package com.jy.selectphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jy.selectphoto.R;
import com.jy.selectphoto.bean.ImagineBean;
import com.jy.selectphoto.dailog.PlayImageDialog;
import com.jy.selectphoto.util.DisplayUtil;
import com.jy.selectphoto.util.event.EvenBusUtil;
import com.jy.selectphoto.util.event.EventBusCode;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


//扫描图片adapter-  by chenzy 2018/5/30
public class ScanImportImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int MaxSelectedNum = 8;
    private ArrayList<ImagineBean> imageBeans = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext = null;
    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private static final int TYPE_1 = 1;//标题栏
    private static final int TYPE_2 = 2;//图片
    private int with;
    private SimpleDateFormat forma2 = new SimpleDateFormat("yyyy.MM.dd");
    private ArrayList<ImagineBean> haveSelectedBean = new ArrayList<>();

    public ScanImportImageAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        with = DisplayUtil.getScreenWidth(context) / 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (imageBeans.get(position).mediaType == 3)
            return TYPE_1;
        return TYPE_2;
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
//        if (viewType == TYPE_1) {
//            return new HolderTitleView(mInflater.inflate(R.layout.item_scan_img_title, parent, false));
//        } else {
            return new HolderScanView(mInflater.inflate(R.layout.item_scan_import_img, parent, false));
//        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ImagineBean bean = imageBeans.get(position);
        initScanView((HolderScanView) holder, bean, position);
//        if (holder instanceof HolderScanView) {
//            initScanView((HolderScanView) holder, bean, position);
//        }
//        else if (holder instanceof HolderTitleView) {
//            initTitleView((HolderTitleView) holder, bean, position);
//        }
    }

    //扫描图片加载
    private void initScanView(final HolderScanView holder, final ImagineBean bean, final int position) {
        if (bean == null) return;
        RequestOptions optionssss = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        optionssss.dontAnimate();
        optionssss.error(R.mipmap.ic_all_document_empty);
        Glide.with(mContext).load(bean.filePath).apply(optionssss).into(holder.vedio);
        holder.selectTv.setVisibility(bean.isSelect ? View.VISIBLE : View.GONE);
        initSdelectView(holder, bean, position);
        if (position == getItemCount() - 1) {
            holder.layout.setPadding(0, 0, 0, DisplayUtil.dip2px(130));
        } else {
            holder.layout.setPadding(0, 0, 0, 0);
        }
        holder.vedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  int haveSlectedCount = 0;
                for (int i = 0; i < imageBeans.size(); i++) {
                    if (imageBeans.get(position).mediaType != 3&&imageBeans.get(i).isSelect) {
                        haveSlectedCount++;
                    }
                }*/
                if (haveSelectedBean.size() < MaxSelectedNum || bean.isSelect) {
                    bean.isSelect = !bean.isSelect;
                    if (haveSelectedBean.contains(bean)) {
                        haveSelectedBean.remove(bean);
                    } else {
                        haveSelectedBean.add(bean);
                    }
                    EvenBusUtil.instance().postEventMesage(EventBusCode.SCANIMAGE_ONCLICK);
//                    EventBusUtils.post(new EventMessage(EventBusCode.SCANIMAGE_ONCLICK));
                }
            }
        });
        holder.playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayImageDialog dialog = new PlayImageDialog(mContext, bean.filePath);
                dialog.setListerner(new PlayImageDialog.playListener() {
                    @Override
                    public void onOK() {
                     /*   int haveSlectedCount = 0;
                        for (int i = 0; i < imageBeans.size(); i++) {
                            if (imageBeans.get(position).mediaType != 3&&imageBeans.get(i).isSelect) {
                                haveSlectedCount++;
                            }
                        }*/
                        if (haveSelectedBean.size() < MaxSelectedNum || bean.isSelect) {
                            bean.isSelect = !bean.isSelect;
                            if (haveSelectedBean.contains(bean)) {
                                haveSelectedBean.remove(bean);
                            } else {
                                haveSelectedBean.add(bean);
                            }
                            EvenBusUtil.instance().postEventMesage(EventBusCode.SCANIMAGE_ONCLICK);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final ImagineBean bean = getList().get(position);
            if (holder instanceof HolderScanView) {
                initSdelectView((HolderScanView) holder, bean, position);
            }
        }
    }

    //更新单个view
    private void initSdelectView(final HolderScanView holder, final ImagineBean bean, final int position) {
        if (bean.isSelect) {
            int i = haveSelectedBean.indexOf(bean) + 1;
            holder.selectTv.setText("" + i);
            holder.selectTv.setVisibility(View.VISIBLE);
        } else {
            holder.selectTv.setVisibility(View.GONE);
        }
    }

    public void notifyDataSetEdit() {
        notifyItemRangeChanged(0, getItemCount(), "true");
    }

    //标题栏
    private void initTitleView(final HolderTitleView holder, final ImagineBean bean, final int position) {
        if (bean == null) return;
        holder.title.setText(forma2.format(new Date(bean.time)));

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
        ImageView playIv;
        TextView selectTv;
        RelativeLayout layout;
        RelativeLayout layout2;

        public HolderScanView(View itemView) {
            super(itemView);
            vedio = itemView.findViewById(R.id.vedio_iv);
            playIv = itemView.findViewById(R.id.play_iv);
            selectTv = itemView.findViewById(R.id.select_tv);
            layout = itemView.findViewById(R.id.layout);
            layout2 = itemView.findViewById(R.id.layout_2);
            itemView.setTag(this);
            ViewGroup.LayoutParams layoutParams = vedio.getLayoutParams();
            layoutParams.height = with;
            vedio.setLayoutParams(layoutParams);
            layout2.setLayoutParams(layoutParams);
        }
    }

    class HolderTitleView extends RecyclerView.ViewHolder {
        TextView title;

        public HolderTitleView(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemView.setTag(this);
        }
    }

//    private void PlayVedio(String filePath) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        File file = new File(filePath);
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
//            uri = FileProvider.getUriForFile(mContext, MyApplication.mContext.getPackageName() + ".fileprovider", file);  //包名.fileprovider
//        } else {
//            uri = Uri.fromFile(file);
//        }
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(uri, "video/*");
//        mContext.startActivity(intent);
//    }

    public int getMaxSelectedNum() {
        return MaxSelectedNum;
    }

    public void setMaxSelectedNum(int maxSelectedNum) {
        MaxSelectedNum = maxSelectedNum;
    }

    public ArrayList<ImagineBean> getHaveSelectedBean() {
        return haveSelectedBean;
    }


}

