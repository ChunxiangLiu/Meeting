package com.jy.selectphoto.dailog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jy.selectphoto.R;
import com.jy.selectphoto.util.DisplayUtil;
import com.ximalife.library.view.CustomTextView;

import java.text.SimpleDateFormat;


/**
 */
public class PlayImageDialog extends Dialog {
    ImageView playImage;
    RelativeLayout layout;
    private Context activity = null;
    private LayoutInflater layoutInflater = null;
    private playListener listener;
    private SimpleDateFormat format = new SimpleDateFormat("MM-dd HH-mm-ss");

    public PlayImageDialog(Context activity, String path) {
        super(activity);
        this.activity = activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init(path);
    }

    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //dialogWindow.setWindowAnimations(R.style.an);
        lp.width = DisplayUtil.getScreenWidth(getContext());
        lp.height = DisplayUtil.getScreenHeight(getContext());
        dialogWindow.setAttributes(lp);

    }

    public void setListerner(playListener listener) {
        this.listener = listener;
    }

    private void init(String path) {
        initWindow();
        View view = layoutInflater.inflate(R.layout.dialog_play_image, null);
        setContentView(view);
        playImage=view.findViewById(R.id.play_image);
        layout=view.findViewById(R.id.layout);
        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onOK();
                dismiss();
            }
        });
        ((CustomTextView)view.findViewById(R.id.bottom_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onOK();
                dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        intExoPlayer(path);
        show();
    }

    private void intExoPlayer(String path) {
        RequestOptions optionssss = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        optionssss.dontAnimate();
        optionssss.error(R.mipmap.ic_all_document_empty);
        Glide.with(activity).load(path).apply(optionssss).into(playImage);

    }


    public interface playListener {
        void onOK();
    }
}