package com.ximalife.library.bottombar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Class description
 *
 * @author ashokvarma
 * @version 1.0
 * @see FrameLayout
 * @since 19 Mar 2016
 */
abstract class BottomNavigationLottieTab extends FrameLayout {

//    protected boolean isNoTitleMode;

//    protected int paddingTopActive;
//    protected int paddingTopInActive;

    protected int mPosition;
    protected int mActiveColor;
    protected int mInActiveColor;
    protected int mBackgroundColor;
    protected int mActiveWidth;
    protected int mInActiveWidth;

    protected Drawable mCompactIcon;
    protected Drawable mCompactInActiveIcon;
    protected boolean isInActiveIconSet = false;
    protected String mLabel;

    protected String lottiesrc;

    protected BadgeItem badgeItem;

    boolean isActive = false;

    View containerView;
    TextView labelView;
    LottieAnimationView iconView;
    FrameLayout iconContainerView;
    BadgeTextView badgeView;

    ValueAnimator animator;

    public BottomNavigationLottieTab(Context context) {
        this(context, null);
    }

    public BottomNavigationLottieTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationLottieTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationLottieTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

//    public void setIsNoTitleMode(boolean isNoTitleMode) {
//        this.isNoTitleMode = isNoTitleMode;
//    }
//
//    public boolean getIsNoTitleMode() {
//        return isNoTitleMode;
//    }

    public void setActiveWidth(int activeWidth) {
        mActiveWidth = activeWidth;
    }

    public void setInactiveWidth(int inactiveWidth) {
        mInActiveWidth = inactiveWidth;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = mInActiveWidth;
        setLayoutParams(params);
    }

    public void setLottieAimation(String json) {
        lottiesrc = json;
    }


    public void setLabel(String label) {
        mLabel = label;
        labelView.setText(label);
    }

    public void setActiveColor(int activeColor) {
        mActiveColor = activeColor;
    }

    public int getActiveColor() {
        return mActiveColor;
    }

    public void setInactiveColor(int inActiveColor) {
        mInActiveColor = inActiveColor;
        labelView.setTextColor(inActiveColor);
    }

    public void setItemBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public void setBadgeItem(BadgeItem badgeItem) {
        this.badgeItem = badgeItem;
    }

    public int getPosition() {
        return mPosition;
    }

    public void select(boolean setActiveColor, int animationDuration) {
        isActive = true;
//        animator.start();
        iconView.playAnimation();
        if (setActiveColor) {
            labelView.setTextColor(mActiveColor);
        } else {
            labelView.setTextColor(mBackgroundColor);
        }

        if (badgeItem != null) {
            badgeItem.select();
        }
    }

    public void unSelect(boolean setActiveColor, int animationDuration) {
        isActive = false;
        iconView.cancelAnimation();
        iconView.setProgress(0);

        labelView.setTextColor(mInActiveColor);

        if (badgeItem != null) {
            badgeItem.unSelect();
        }
    }

    @CallSuper
    public void initialise() {
        iconView.setAnimation(lottiesrc);
        iconView.setRepeatCount(0);
        iconView.setProgress(0);
//        animator = ValueAnimator.ofFloat(0f, 1f)
//                .setDuration(200);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                iconView.setProgress((float)valueAnimator.getAnimatedValue());
//            }
//        });

    }

}