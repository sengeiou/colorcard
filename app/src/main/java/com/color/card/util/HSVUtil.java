package com.color.card.util;

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

    public HSVUtil(float h, float s, float v) {
        this.H = h;
        this.S = s;
        this.V = v;
    }

    public HSVUtil() {
    }

    public static double distanceOf(HSVUtil hsv1, HSVUtil hsv2) {
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


    public static String getValue(HSVUtil hsv1, HSVUtil hsv2) {
        if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(385000)) < 0) {
            return "0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(385000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(600000)) < 0) {
            return "0.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(600000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(655000)) < 0) {
            return "1.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(655000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(750000)) < 0) {
            return "1.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(750000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1210000)) < 0) {
            return "2.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1210000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1530000)) < 0) {
            return "2.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1530000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1550000)) < 0) {
            return "3.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1550000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1600000)) < 0) {
            return "3.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1600000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1640000)) < 0) {
            return "4.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1640000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1650000)) < 0) {
            return "4.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1650000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1660000)) < 0) {
            return "5.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1660000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1670000)) < 0) {
            return "5.5";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1670000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1680000)) < 0) {
            return "6.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1680000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1690000)) < 0) {
            return "6.5";
        }else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1690000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1700000)) < 0) {
            return "7.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1700000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1710000)) < 0) {
            return "8.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1710000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1720000)) < 0) {
            return "9.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1720000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1730000)) < 0) {
            return "10.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1730000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1740000)) < 0) {
            return "11.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1740000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1750000)) < 0) {
            return "12.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1750000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1760000)) < 0) {
            return "13.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1760000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1770000)) < 0) {
            return "14.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1770000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1780000)) < 0) {
            return "16.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1780000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1790000)) < 0) {
            return "18.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1790000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1800000)) < 0) {
            return "20.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1800000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1810000)) < 0) {
            return "22.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1810000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1815000)) < 0) {
            return "24.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1815000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1820000)) < 0) {
            return "26.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1820000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1825000)) < 0) {
            return "28.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1825000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1830000)) < 0) {
            return "30.0";
        } else if (new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1830000)) >= 0 && new Double(distanceOf(hsv1, hsv2)).compareTo(new Double(1835000)) < 0) {
            return "35.0";
        } else {
            return "40.0";
        }

    }
}


