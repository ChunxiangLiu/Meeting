package com.jy.meeting.bean;

public class PrintBean {
    private float printX;
    private float printY;
    private int radius;

    private boolean isInside;

    public float getPrintX() {
        return printX;
    }

    public void setPrintX(float printX) {
        this.printX = printX;
    }

    public float getPrintY() {
        return printY;
    }

    public void setPrintY(float printY) {
        this.printY = printY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isInside() {
        return isInside;
    }

    public void setInside(boolean inside) {
        isInside = inside;
    }
}
