package com.ximalife.library.util.glide;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.ximalife.library.R;
import com.ximalife.library.base.BaseAppApplication;

import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {
    public static void displayImagCache(ImageView imageView, Object obj) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(obj)
                .apply(options)
                .into(imageView);
    }
    public static void display(ImageView imageView, Object obj) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(obj)
                .apply(options)
                .into(imageView);
    }

    public static void display(ImageView imageView, Uri uri, int override) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(BaseAppApplication.Companion.getInstance())
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .apply(new RequestOptions()
                        .override(override, override)
                        .placeholder(R.drawable.load_item_nomarl)
                        .centerCrop())
                .into(imageView);
    }


    public static void displayHead(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void display(ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void displaNoCahceDefalutResouce(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void displaNoDefalutResouce(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void display(ImageView imageView, String url, int placeholder) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void display(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void displayNoCenterCrop(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void displayDontAnimate(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(imageView.getDrawable());
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    public static void display(ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    /**
     * 控制四个角的任意弧度
     *
     * @param radius
     * @param cornerType
     * @param url
     * @param imageView
     */
    public static void loadAllCornerImage(Context context, int radius,
                                          GlideRoundedCornersTransform.CornerType cornerType,
                                          String url, ImageView imageView) {



        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideRoundedCornersTransform(context, radius, cornerType))
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(myOptions)
                .into(imageView);
    }

    public static void displayImgage(String imageUrl, ImageView view, int radius) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new GlideRoundedCornersTransform(BaseAppApplication.Companion.getInstance(), radius, GlideRoundedCornersTransform.CornerType.ALL))
                .placeholder(view.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(options)
                .into(view);
    }





    public static void loadCustomFilletImage(String imageUrl, ImageView view, int radius) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .optionalTransform(new GlideRoundedCornersTransform(BaseAppApplication.Companion.getInstance(), radius, GlideRoundedCornersTransform.CornerType.ALL))
                .placeholder(view.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(options)
                .into(view);
    }

    /**
     * 控制四个角的任意弧度
     *
     * @param radius
     * @param cornerType
     * @param url
     * @param imageView
     */
    public static void loadAllCornerImage(int radius,
                                          GlideRoundedCornersTransform.CornerType cornerType,
                                          String url, ImageView imageView) {

        RequestOptions myOptions = new RequestOptions()
                //.override(DisplayUtil.dip2px(23),DisplayUtil.dip2px(23))
                .centerCrop()
                .optionalTransform(new GlideRoundedCornersTransform(BaseAppApplication.Companion.getInstance(), radius, cornerType))
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        if (TextUtils.isEmpty(url)) {
            Glide.with(BaseAppApplication.Companion.getInstance())
                    .load(R.drawable.load_item_nomarl)
                    .apply(myOptions)
                    .into(imageView);
        } else {
            Glide.with(BaseAppApplication.Companion.getInstance())
                    .load(url)
                    .apply(myOptions)
                    .into(imageView);
        }
    }


    public static void loadAllCornerImage(int radius,
                                          GlideRoundedCornersTransform.CornerType cornerType,
                                          Object url, ImageView imageView) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideRoundedCornersTransform(BaseAppApplication.Companion.getInstance(), radius, cornerType))
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(url)
                .apply(myOptions)
                .into(imageView);
    }


    public static void loadAllCornerImage(int radius,
                                          GlideRoundedCornersTransform.CornerType cornerType,
                                          int resId, ImageView imageView) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideRoundedCornersTransform(BaseAppApplication.Companion.getInstance(), radius, cornerType))
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(resId)
                .apply(myOptions)
                .into(imageView);
    }


    /**
     * @param imageUrl  图片网络地址
     * @param imageView 要载入图片的ImageView对象
     */
    public static void displatAutoWH(final String imageUrl, final ImageView imageView) {
        RequestOptions myOptions = new RequestOptions();
        myOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        myOptions.placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(myOptions)
                .into(imageView);
    }


    /**
     * 淡入淡出动画
     */
    public static void displayHeadCrossFade(final ImageView imageView, final String imageUrl) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions myOptions = new RequestOptions();
        myOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        myOptions.placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(myOptions)
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(imageView);
    }


    /**
     * 淡入淡出动画
     */
    public static void displayCrossFade(final ImageView imageView, final String imageUrl) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        RequestOptions myOptions = new RequestOptions();
        myOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        myOptions.placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(myOptions)
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(imageView);
    }

    public static void loadCircleCropImg(final ImageView imageView, final String imageUrl) {

        RequestOptions options = RequestOptions
                .bitmapTransform(new CircleCrop())
                .placeholder(R.drawable.load_item_nomarl)
                .error(R.drawable.load_item_nomarl)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(BaseAppApplication.Companion.getInstance())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }



}
