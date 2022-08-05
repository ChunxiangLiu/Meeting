package com.ximalife.library.util.event;


import org.greenrobot.eventbus.EventBus;


/**
 * EventBus事件发送处理的工具类
 * Created by joe.xiang on 2017/7/10.
 */
public class EvenBusUtil {

    private String TAG = "EvenBusUtil";
    private static EvenBusUtil instance;

    public static EvenBusUtil instance() {
        if (instance == null) {
            synchronized (EvenBusUtil.class) {
                if (instance == null) {
                    instance = new EvenBusUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 判断当前事件是否注册过
     */
    public boolean isRegistered(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }


    /**
     * 注册事件
     *
     * @param subscriber
     */
    public void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }

    }

    /**
     * 释放事件
     *
     * @param subscriber
     */
    public void unRegister(Object subscriber) {
        //LogUtil.i(TAG,"释放事件");
        if(EventBus.getDefault().isRegistered(subscriber)){
            EventBus.getDefault().unregister(subscriber);
        }
    }


    /**
     * 发送普通事件
     */
    public void postEventMesage(String mesgAction) {
        EventMessage message = new EventMessage(mesgAction);
        EventBus.getDefault().post(message);
    }


    /**
     * 发送普通事件
     */
    public void postEventMesage(EventMessage eventMessage) {
        EventBus.getDefault().post(eventMessage);
    }


    /**
     * 发送粘性事件
     */
    public void postStickyEventMessage(EventMessage eventMessage) {
        EventBus.getDefault().postSticky(eventMessage);
    }


    /**
     * 移除指定的粘性订阅事件
     */
    public <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }


    /**
     * 移除所有的粘性订阅事件
     */
    public void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }


    /**
     * 取消事件传送
     * param event 事件对象
     */
    public void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }
}
