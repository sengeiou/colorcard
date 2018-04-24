package com.color.card.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.color.card.R;
import com.color.card.util.DensityUtils;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yqy
 * @date on 2018/3/26
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class HealthPointView extends View {
    private int len;

    private RectF oval;
    // 圆弧的半径
    private int radius;

    private float startAngle = 120;

    private float sweepAngle = 270;

    // 刻度经过角度范围
    private float targetAngle = 0;

    private Paint paint;

    private int red;

    private String score = "0.0";

    private int green;
    private boolean useCenter = false;

    public static final double maxNumber = 40;

    //判断是否在动
    private boolean isRunning;
    //判断是回退的状态还是前进状态
    private int state = 2;

    private String stateText = "无检测";

    private int totalCount = 80;//设置刻度的个数


    private float trueAngle;


    /**
     * 用来初始化画笔等
     *
     * @param context
     * @param attrs
     */
    public HealthPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.WHITE);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //让画出的图形是空心的(不填充)
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 用来测量限制view为正方形
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //以最小值为正方形的长
        len = Math.min(width, height);
        //实例化矩形
        oval = new RectF(0, 0, len, len);

        radius = len / 2;
        //设置测量高度和宽度（必须要调用，不然无效果）
        setMeasuredDimension(len, len);
    }

    /**
     * 实现各种绘制功能
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧的方法
//        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
        //画刻度线的方法
        drawViewLine(canvas);
        // 画刻度线内的内容
        drawText(canvas);
    }


    private void drawViewLine(Canvas canvas) {
        //先保存之前canvas的内容
        canvas.save();
        //移动canvas(X轴移动距离，Y轴移动距离)
        canvas.translate(radius, radius);
        //旋转坐标系
        canvas.rotate(45);

        Paint linePatin = new Paint();
        //设置画笔颜色
        linePatin.setColor(Color.parseColor("#4cFFFFFF"));
        //线宽
        linePatin.setStrokeWidth(Integer.parseInt(getContext().getString(R.string.hl_stroke_hight)));
        //设置画笔抗锯齿
        linePatin.setAntiAlias(true);


        Paint linePatin2 = new Paint();
        //设置画笔颜色
        linePatin2.setColor(Color.parseColor("#4cFFFFFF"));
        //线宽
        linePatin2.setStrokeWidth(Integer.parseInt(getContext().getString(R.string.hl_stroke_hight)));
        //设置画笔抗锯齿
        linePatin.setAntiAlias(true);
        //确定每次旋转的角度
        float rotateAngle = sweepAngle / totalCount;
        //绘制有色部分的画笔
        Paint targetLinePatin = new Paint();
        targetLinePatin.setColor(Color.GREEN);
        targetLinePatin.setStrokeWidth(Integer.parseInt(getContext().getString(R.string.hl_stroke_gray)));
        targetLinePatin.setAntiAlias(true);
        float hasDraw = 0;
        for (int i = 0; i < totalCount + 1; i++) {

            if (hasDraw <= targetAngle && targetAngle != 0) {//需要绘制有色部分的时候
                //计算已经绘制的比例
                float percent = hasDraw / sweepAngle;
//                red = 255 - (int) (255 * percent);
//                green = (int) (255 * percent);
//                targetLinePatin.setARGB(255, red, green, 0);
                targetLinePatin.setColor(Color.WHITE);
                //画一条刻度线
                targetLinePatin.setStrokeWidth(Integer.parseInt(getContext().getString(R.string.hl_stroke_hight)));
                if (((hasDraw + rotateAngle - 0.1) >= trueAngle)) {
                    targetLinePatin.setStrokeWidth(Integer.parseInt(getContext().getString(R.string.hl_stroke_hight2)));
                    canvas.drawLine(0, radius, 0, radius - Float.parseFloat(getContext().getString(R.string.hl_left)), targetLinePatin);
                } else {
                    canvas.drawLine(0, radius - Float.parseFloat(getContext().getString(R.string.hl_padding_out)), 0, radius - Float.parseFloat(getContext().getString(R.string.hl_left)), targetLinePatin);
                }
            } else {//不需要绘制有色部分
                //画一条刻度线
                canvas.drawLine(0, radius - Float.parseFloat(getContext().getString(R.string.hl_padding_out)), 0, radius - Float.parseFloat(getContext().getString(R.string.hl_left)), linePatin);
            }
            if (i == 8 || i == 14 || i == 18) {
                canvas.drawLine(0, radius - Float.parseFloat(getContext().getString(R.string.hl_in_padding)), 0, radius - Float.parseFloat(getContext().getString(R.string.hl_in_padding_out)), linePatin2);
            } else {
                canvas.drawLine(0, radius - Float.parseFloat(getContext().getString(R.string.hl_in_padding)), 0, radius - Float.parseFloat(getContext().getString(R.string.hl_in_padding2)), linePatin2);
            }
            hasDraw += rotateAngle;
            canvas.rotate(rotateAngle);
        }
        //操作完成后恢复状态
        canvas.restore();
    }


    public void changeAngle(final float trueAngle, final String score1, final String scoreText1) {
        if (isRunning) {//如果在动直接返回
            return;
        }
        this.trueAngle = trueAngle;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                switch (state) {
                    case 1://后退状态
                        isRunning = true;
                        targetAngle = 0;
                        //改为前进状态
                        state = 2;
                        break;
                    case 2://前进状态
                        targetAngle += 3;
                        if (targetAngle >= trueAngle) {//如果增加到指定角度
                            targetAngle = trueAngle;
                            //改为后退状态
                            state = 1;
                            isRunning = false;
                            //结束本次运动
                            timer.cancel();
                        }
                        break;
                    default:
                        break;
                }
                // 计算当前比例应该的多少分
//                score = (targetAngle / 270 * 20);
                score = score1;
                stateText = scoreText1;
//                if (score < 10) {
//                    stateText = "偏低";
//                } else if (10 <= score && score < 20) {
//                    stateText = "正常";
//                } else {
//                    stateText = "偏高";
//                }
//                score = scoreText;

                //重新绘制（子线程中使用的方法）
                postInvalidate();
            }
        }, 500, 30);
    }


    /**
     * 绘制小圆和文本的方法，小圆颜色同样渐变
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //先绘制一个小圆
        Paint smallPaint = new Paint();
        smallPaint.setColor(Color.TRANSPARENT);
        // 画小圆指定圆心坐标，半径，画笔即可
        int smallRadius = radius - 200;
        canvas.drawCircle(radius, radius, smallRadius, smallPaint);
        //绘制文本
        Paint textPaint = new Paint();
        //设置文本居中对齐
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(DensityUtils.sp2px(60));
        //score需要通过计算得到
        canvas.drawText(score, radius, Float.parseFloat(getContext().getString(R.string.hl_value_padding)), textPaint);

        textPaint.setTextSize(DensityUtils.sp2px(18));
        //score需要通过计算得到
        canvas.drawText("mmol/L", radius, Float.parseFloat(getContext().getString(R.string.hl_unit_padding)), textPaint);
//        //绘制分，在分数的右上方
//        textPaint.setTextSize(smallRadius / 6);
//        canvas.drawText("分", radius + smallRadius / 2, radius - smallRadius / 4, textPaint);
        //绘制点击优化在分数的下方
        textPaint.setTextSize(DensityUtils.sp2px(18));
        canvas.drawText(stateText, radius, Float.parseFloat(getContext().getString(R.string.hl_state_padding)), textPaint);
    }

}
