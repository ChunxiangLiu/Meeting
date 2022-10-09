package com.ximalife.library.view.flow;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 流式标签布局
 * 原理：重写{@link ViewGroup#onMeasure(int, int)}
 * 和{@link ViewGroup#onLayout(boolean, int, int, int, int)}
 * 方法
 */
public class FlowTagLayout extends ViewGroup {

    private static final String TAG = FlowTagLayout.class.getSimpleName();

    /**
     * 流布局不支持被选中
     */
    public static final int FLOW_TAG_CHECKED_NONE = 0;
    /**
     * 流布局支持单选
     */
    public static final int FLOW_TAG_CHECKED_SINGLE = 1;
    /**
     * 流布局支持多选
     */
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    /**
     * 监听数据集变化
     */
    AdapterDataSetObserver mDataSetObserver;

    /**
     * 含有数据及显示视图的Adapter
     */
    ListAdapter mAdapter;

    /**
     * 标签点击事件回调
     */
    OnTagClickListener mOnTagClickListener;

    /**
     * 标签被选中事件回调
     */
    OnTagSelectListener mOnTagSelectListener;

    /**
     * 是否有行限制
     */
    boolean isLimitLine = true;

    /**
     * 限制显示的行数
     */
    int limitLineCount = 1;

    /**
     * 是否溢出，即超过要求显示行数
     */
    boolean isOverFlow;

    /**
     * 标签流式布局选中模式，默认是不支持选中的
     */
    private int mTagCheckMode = FLOW_TAG_CHECKED_NONE;

    /**
     * 存储选中的tag
     */
    private SparseBooleanArray mCheckedTagArray = new SparseBooleanArray();

    /**
     * 超过限制函数回调
     */
    private IsMoreListener isMoreListener;

    public FlowTagLayout(Context context) {
        super(context);
    }

    public FlowTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsLimitLine(boolean isLimitLine){
        this.isLimitLine = isLimitLine;
        this.requestLayout();
        this.invalidate();
    }

    public boolean isLimitLine(){
        return isLimitLine;
    }

    public void setLimitLineCount(int limitLineCount){
        this.limitLineCount = limitLineCount;
    }

    public void setOverFlow(boolean overFlow) {
        isOverFlow = overFlow;
    }

    public void setIsMoreListener(IsMoreListener isMoreListener){
        this.isMoreListener = isMoreListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量时每一行的宽度，width不断取最大宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;
        //流布局的行数
        int currLines = 0;
        //流布局子视图的数量
        int childCount = getChildCount();
        //遍历每个子元素
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;

            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth) {
                //确认是否溢出，即超过要显示的行数
//                if(currLines == this.limitLineCount){
//                    setOverFlow(true);
//                    break;
//                }
                //限制显示行数
                if(isLimitLine){
                    //达到要求显示的行数
                    if(currLines == this.limitLineCount){
                        setOverFlow(true);
                        break;
                    }
//                    if(isOverFlow){
//                        break;
//                    }
                }
                //换行，计算所有行中最大的宽度
                resultWidth = Math.max(lineWidth, realChildWidth);
                //叠加当前高度
                resultHeight += realChildHeight;
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
//                if(!isShowAllItem) break;
                currLines++;
            } else{
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childCount - 1) {
//                if(currLines == this.limitLineCount){
//                    setOverFlow(true);
//                }
                //确认是否溢出，即超过要显示的行数
                if(isLimitLine){
                    if(currLines == this.limitLineCount){
                        setOverFlow(true);
                        break;
                    }
//                    if(isOverFlow){
//                        break;
//                    }
                }
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
                currLines++;
//                maxVisibleLines = currLines;
            }
            //判断流布局行数是否超过要显示的行数
            if(currLines>this.limitLineCount){
                //超过默认显示行数的
                setOverFlow(true);
            }
            Log.d("FlowTagLayout", "limitLineCount="+limitLineCount+ " i="+i +"currLines = "+currLines+" realChildWidth="+realChildWidth+
                    "sizeWidth="+sizeWidth);
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
//            MethodUtils.d("FlowLayout", "resultWidth"+resultWidth+"resultHeight"+resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int flowWidth = getWidth();

        int childLeft = 0;
        int childTop = 0;

        int lineCount = 1;

        //遍历子控件，记录每个子view的位置
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth) {
//                if(!isShowAllItem) break;
                if(isLimitLine) {
                    if(lineCount == this.limitLineCount) {
                        break;
                    }
                }
                //换行处理
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
                lineCount ++;
            }
            //布局
            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);

            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }


    /**
     * 重新加载刷新数据
     */
    private void reloadData() {
//        maxVisibleLines = 0;
        removeAllViews();

        boolean isSetted = false;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckedTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
//            addView(childView,
//              new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));//这个构造方法所然能使用但是编译器会报错
            addView(childView, new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));

            if (mAdapter instanceof OnInitSelectedPosition) {
                boolean isSelected = ((OnInitSelectedPosition) mAdapter).isSelectedPosition(i);
                //判断一下模式
                if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                    //单选只有第一个起作用
                    if (isSelected && !isSetted) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                        isSetted = true;
                    }
                } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    if (isSelected) {
                        mCheckedTagArray.put(i, true);
                        childView.setSelected(true);
                    }
                }
            }

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    setIsLimitLine(!isLimitLine);
                    if (mTagCheckMode == FLOW_TAG_CHECKED_NONE) {
                        if (mOnTagClickListener != null) {
                            mOnTagClickListener.onItemClick(FlowTagLayout.this, childView, j);
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_SINGLE) {
                        //判断状态
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                            if (mOnTagSelectListener != null) {
                                mOnTagSelectListener.onItemSelect(FlowTagLayout.this, new ArrayList<Integer>());
                            }
                            return;
                        }

                        for (int k = 0; k < mAdapter.getCount(); k++) {
                            mCheckedTagArray.put(k, false);
                            getChildAt(k).setSelected(false);
                        }
                        mCheckedTagArray.put(j, true);
                        childView.setSelected(true);

                        if (mOnTagSelectListener != null) {
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.this, Arrays.asList(j));
                        }
                    } else if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckedTagArray.put(j, true);
                            childView.setSelected(true);
                        }
                        //回调
                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<Integer>();
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                if (mCheckedTagArray.get(k)) {
                                    list.add(k);
                                }
                            }
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.this, list);
                        }
                    }
                }
            });
        }
    }

    /**
     * 清除所有被选择的选项
     */
    public void clearAllOption(){
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mCheckedTagArray.get(i)) {
                getChildAt(i).setSelected(false);
            }
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        this.mOnTagSelectListener = onTagSelectListener;
    }

    /**
     * 像ListView、GridView一样使用FlowLayout
     *
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        //清除现有的数据
        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    /**
     * 获取标签模式
     *
     * @return
     */
    public int getmTagCheckMode() {
        return mTagCheckMode;
    }

    /**
     * 设置标签选中模式
     *
     * @param tagMode
     */
    public void setTagCheckedMode(int tagMode) {
        this.mTagCheckMode = tagMode;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(isMoreListener != null){
            isMoreListener.isMoreListener(isOverFlow);
        }
    }

    public interface IsMoreListener{
        public void isMoreListener(boolean isOverFlow);
    }
}
