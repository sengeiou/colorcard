package com.example.yqy.myapplication.util;

import android.util.Log;

/**
 * Created by yqy on 2018/1/8.
 */

public class HSVUtil {
    private float H;
    private float S;
    private float V;

    //self-defined
    private static final double R = 100;
    private static final double angle = 30;
    private static final double h = R * Math.cos(angle / 180 * Math.PI);
    private static final double r = R * Math.sin(angle / 180 * Math.PI);

    public HSVUtil(float h,float s,float v){
        this.H=h;
        this.S=s;
        this.V=v;
    }

    public HSVUtil(){}

    public static double distanceOf(HSVUtil hsv1, HSVUtil hsv2) {
        Log.w("kim","---->"+hsv1.getH()+"====>"+hsv1.getS()+"===>"+hsv1.getV());
        Log.w("kim","---->"+hsv2.getH()+"====>"+hsv2.getS()+"===>"+hsv2.getV());
        double x1 = r * hsv1.V * hsv1.S * Math.cos(hsv1.H / 180 * Math.PI);
        double y1 = r * hsv1.V * hsv1.S * Math.sin(hsv1.H / 180 * Math.PI);
        double z1 = h * (1 - hsv1.V);
        double x2 = r * hsv2.V * hsv2.S * Math.cos(hsv2.H / 180 * Math.PI);
        double y2 = r * hsv2.V * hsv2.S * Math.sin(hsv2.H / 180 * Math.PI);
        double z2 = h * (1 - hsv2.V);
        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    public HSVUtil RGBtoHSV(float r, float g, float b) {
        float max1 = Math.max(r, Math.max(g, b));
        float min1 = Math.min(r, Math.min(g, b));
        if (max1 == min1) {
            setH(0);
        } else {
            if (r == max1) {
                if (g >= b) {
                    setH(60 * (g - b) / (max1 - min1));
                } else {
                    setH(60 * (g - b) / (max1 - min1) + 360);
                }
            }

            if (g == max1) {
                setH(60 * (b - r) / (max1 - min1) + 120);
            }

            if (b == max1) {
                setH(60 * (r - g) / (max1 - min1) + 240);
            }
        }
        if (max1 == 0) {
            setS(0);
        } else {
            setS((1 - min1 / max1) * 255);
        }
        setV(max1);
        return this;
    }

    public float getH() {
        return H;
    }

    public void setH(float h) {
        H = h;
    }

    public float getS() {
        return S;
    }

    public void setS(float s) {
        S = s;
    }

    public float getV() {
        return V;
    }

    public void setV(float v) {
        V = v;
    }
}


