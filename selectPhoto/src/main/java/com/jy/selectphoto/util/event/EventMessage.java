package com.jy.selectphoto.util.event;

import java.io.Serializable;

/**
 * Created by joe.xiang on 2017/7/11.
 */
public class EventMessage<T> implements Serializable {

    /**
     * 处理事件的标识
     */
    public String action;

    /**
     * 发送数据
     */
    public T data;

    public Object[] objs;

    public EventMessage() {
    }

    public EventMessage(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public EventMessage(String action, Object...objs){
        this.action = action;
        this.objs = objs;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "action='" + action + '\'' +
                ", data=" + data +
                '}';
    }
}
