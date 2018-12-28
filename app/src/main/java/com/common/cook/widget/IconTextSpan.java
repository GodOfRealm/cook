package com.common.cook.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.widget.TextView;

/**
 * Created by SonnyJack on 2018/7/13 09:19.
 */
public class IconTextSpan extends ReplacementSpan {

    private TextView mTextView;
    private int mBgColor;//背景颜色
    private float mRadius = 0f;//Icon圆角半径
    private int mTextColor; //文字颜色
    private float mTextSize; //文字大小,单位为px

    private float mBgWidth;  //Icon背景宽度

    //用于控制背景大小
    private float mPaddingRight = 0; //右边距
    private float mPaddingLeft = 0; //左边距
    private float mPaddingTop = 0; //上边距
    private float mPaddingBottom = 0; //下边距

    private Paint mBgPaint; //icon背景画笔
    private Paint mTextPaint; //icon文字画笔

    public IconTextSpan(TextView textView, int textColor, int textSize, int bgColor, float radius) {
        mTextView = textView;
        mTextColor = textColor;
        mTextSize = textSize;
        mBgColor = bgColor;
        mRadius = radius;
        init();
    }

    private void init() {
        //初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //初始化背景画笔
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        //初始化文字画笔
        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
    }

    /**
     * 设置内边距， 用于控制背景大小
     *
     * @param paddingLeft
     * @param paddingTop
     * @param paddingRight
     * @param paddingBottom
     */
    public void setPadding(float paddingLeft, float paddingTop, float paddingRight, float paddingBottom) {
        mPaddingLeft = paddingLeft;//px
        mPaddingRight = paddingRight;
        mPaddingTop = paddingTop;
        mPaddingBottom = paddingBottom;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        text = text.subSequence(start, end);
        float textWidth = mTextPaint.measureText(text.toString());
        mBgWidth = textWidth + mPaddingLeft + mPaddingRight;
        return (int) Math.ceil(mBgWidth);//大小为字体的宽度加左右边距
    }

    //top 当前span所在行的上方y
    //y其实就是metric里baseline的位置
    //bottom 当前span所在行的下方y(包含了行间距)，会和下一行的top重合
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        float newBottom = bottom;
        if (mTextView.getLineCount() > 1) {
            //要扣除行间距
            newBottom = bottom - mTextView.getLineSpacingExtra();
        }

        //绘制背景
        RectF bgRect = new RectF(x, top + mPaddingTop, x + mBgWidth, newBottom - mPaddingBottom);
        if (mRadius > 0) {
            canvas.drawRoundRect(bgRect, mRadius, mRadius, mBgPaint);
        } else {
            canvas.drawRect(bgRect, mBgPaint);
        }

        //绘制文字
        Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        text = text.subSequence(start, end);
        y = -fm.ascent + (((int) newBottom - top + fm.ascent) / 2) - 3;
        //canvas.drawText(text.toString(), x + mPaddingLeft, y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2), mTextPaint);//此处重新计算y坐标，使字体居中
        canvas.drawText(text.toString(), x + mPaddingLeft, y, mTextPaint);//此处重新计算y坐标，使字体居中
    }
}
