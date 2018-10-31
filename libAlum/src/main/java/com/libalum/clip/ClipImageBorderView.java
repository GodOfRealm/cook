package com.libalum.clip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author qiurong
 */
public class ClipImageBorderView extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#aa000000");
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 2;

    private Paint mPaint;
    private Bitmap mDes;
    private int mHeight;
    private boolean inited;
    private Canvas canvas;
    private RectF rectF;
    private PorterDuffXfermode xfermode;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas = new Canvas();
        rectF = new RectF();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
    }

    private void init() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        if (mDes == null) {
            mDes = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            rectF.set(0, 0, mWidth, mHeight);
        }
        canvas.setBitmap(mDes);
        mPaint.setStyle(Paint.Style.FILL);
        drawBorder();

        mPaint.setXfermode(xfermode);
        mPaint.setColor(0Xbb000000);
        canvas.drawRect(rectF, mPaint);
        mPaint.reset();
    }

    private void drawBorder() {
        int top = (mHeight - mWidth) / 2;
        canvas.drawRect(mBorderWidth, top, mWidth, top + mWidth, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        init();

        canvas.drawBitmap(mDes, 0, 0, null);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(0Xaaffffff);
        mPaint.setStyle(Paint.Style.STROKE);
        drawBorder();
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}