package com.jy.selectphoto.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 */

public class ImagineBean implements Parcelable {
    public long size;
    public String filePath;
    public int mediaType;//1代表视频 2代表图片  3代表时间标题
    public String name;
    public long time;
    public long playtime;
    public String rootName;//根目录名字
    public boolean isSelect = false;//是否选中

    public ImagineBean(long size, String filePath, long time, long playtime, String name) {
        this.size = size;
        this.filePath = filePath;
        this.time = time;
        this.playtime = playtime;
        this.name = name;
    }

    public ImagineBean() {
    }

    protected ImagineBean(Parcel in) {
        size = in.readLong();
        filePath = in.readString();
        mediaType = in.readInt();
        name = in.readString();
        time = in.readLong();
        playtime = in.readLong();
        rootName = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(size);
        dest.writeString(filePath);
        dest.writeInt(mediaType);
        dest.writeString(name);
        dest.writeLong(time);
        dest.writeLong(playtime);
        dest.writeString(rootName);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImagineBean> CREATOR = new Creator<ImagineBean>() {
        @Override
        public ImagineBean createFromParcel(Parcel in) {
            return new ImagineBean(in);
        }

        @Override
        public ImagineBean[] newArray(int size) {
            return new ImagineBean[size];
        }
    };

    @Override
    public String toString() {
        return "VedioBean{" +
                "size=" + size +
                ", filePath='" + filePath + '\'' +
                ", mediaType=" + mediaType +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", playtime=" + playtime +
                ", rootName='" + rootName + '\'' +
                '}';
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public static Creator<ImagineBean> getCREATOR() {
        return CREATOR;
    }
}
