package com.jy.meeting.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jy.meeting.bean.PrintBean;
import com.ximalife.library.base.BaseAppApplication;

import java.util.ArrayList;
import java.util.List;

import me.panpf.sketch.SketchImageView;

public class DrawCircle extends SketchImageView {


    List<PrintBean> printBeanList = new ArrayList<>();

    private float currentX = 40;
    private float currentY = 50;
    private int radius = 80;

    Context mContext;

    boolean isInside = false;

    //定义并创建画笔
    Paint p = new Paint();

    public DrawCircle(Context context) {
        super(context);
        this.mContext = context;
    }

    public DrawCircle(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //绘制一个小圆（作为小球） 四个参数代表坐标   半径  画笔
        if (printBeanList == null && printBeanList.size() == 0) {
            //设置画笔的颜色
            p.setColor(Color.RED);
            canvas.drawCircle(currentX, currentY, radius, p);
        } else {
            for (int i = 0; i < printBeanList.size(); i++) {
                p.setColor(printBeanList.get(i).isInside() ? Color.BLACK : Color.RED);
                canvas.drawCircle(printBeanList.get(i).getPrintX(), printBeanList.get(i).getPrintY(), printBeanList.get(i).getRadius(), p);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        //修改currentX currentY两个属性


        float xTouch = event.getX();
        float yTouch = event.getY();


//如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (getInsideView(xTouch, yTouch) == null) {
                PrintBean printBean = new PrintBean();
                printBean.setPrintX(xTouch);
                printBean.setPrintY(yTouch);
                printBean.setRadius(radius);
                printBean.setInside(false);
                printBeanList.add(printBean);
            }
//            if (printBeanList != null && printBeanList.size() > 0) {
//                if (getInsideView(xTouch, yTouch) == null) {
//                    PrintBean printBean = new PrintBean();
//                    printBean.setPrintX(xTouch);
//                    printBean.setPrintY(yTouch);
//                    printBean.setRadius(radius);
//                    printBean.setInside(false);
//                    printBeanList.add(printBean);
//                }
//            } else {
//                PrintBean printBean = new PrintBean();
//                printBean.setPrintX(xTouch);
//                printBean.setPrintY(yTouch);
//                printBean.setRadius(radius);
//                printBean.setInside(false);
//                printBeanList.add(printBean);
//            }
            //通知当前组件重绘自己
            invalidate();
        }


        if (event.getPointerCount() >= 2) {
            //返回true表明该处理方法已经处理该事件
            return super.onTouchEvent(event);
        } else {
            return true;
        }

    }


    public int getCanvasCircleListSize() {
        if (printBeanList != null) {
            return printBeanList.size();
        }
        return 0;
    }


    public PrintBean getInsideView(float xTouch, float yTouch) {

        PrintBean printBean = null;

        for (int i = 0; i < printBeanList.size(); i++) {
            float itemX = printBeanList.get(i).getPrintX();
            float itemY = printBeanList.get(i).getPrintY();
            int radius1 = printBeanList.get(i).getRadius();

//            float centerX = itemX + radius1;
//            float centerY = itemY + radius1;
            float distanceX = Math.abs(xTouch - itemX);
            float distanceY = Math.abs(yTouch - itemY);
            int distance = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//            if (distance>radius1) {
//                printBean = printBeanList.get(i);
//                printBeanList.remove(i);
//            }
            if ((distanceX * distanceX) + (distanceY * distanceY) <= radius1 * radius1) {//点到相同位置的圆
                printBean = printBeanList.get(i);
                printBeanList.remove(i);
            }

        }

        return printBean;

    }


}
