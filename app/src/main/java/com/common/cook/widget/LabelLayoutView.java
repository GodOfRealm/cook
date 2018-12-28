package com.common.cook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.common.cook.R;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 添加label
 * 作者: zws 2018/7/4 0004 15:14
 * 功能描述:
 * 备注:
 *
 * @deprecated 扩展性太差，请使用{@link TagListLinearLayout}
 *
 * @param <T> the type parameter
 */
public class LabelLayoutView<T> extends LinearLayout {

    public LabelItemCallback mLabelItemCallback;

    public ArrayList<T> dataList = new ArrayList<>();

    public ArrayList<T> mDataList = new ArrayList<>();

    private int mbg; // 背景
    private int mTextColor; // 文字颜色
    private float mMarginLeft; //
    private float mTextSize; // 字体大小

    public LabelLayoutView(Context context) {
        super(context);
        initView();
        initData();
        initListener();
    }

    public LabelLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initView();
        initData();
        initListener();
    }

    public LabelLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initView();
        initData();
        initListener();
    }

    private void init(Context context, AttributeSet attrs) {

        int defaultTextColor = context.getResources().getColor(R.color.color_0faef8);
        float defaultTextSize = context.getResources().getDimension(R.dimen.text_size_small);
        float defaultMarginLeft = context.getResources().getDimension(R.dimen.label_default_margin_left);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LabelLayoutView);
        mbg = ta.getResourceId(R.styleable.LabelLayoutView_llv_layoutbg, R.drawable.order_bg_order_label);
        mTextColor = ta.getColor(R.styleable.LabelLayoutView_llv_text_color, defaultTextColor);
        mTextSize = ta.getDimension(R.styleable.LabelLayoutView_llv_text_size, defaultTextSize);
        mMarginLeft = ta.getDimension(R.styleable.LabelLayoutView_llv_margin_left, defaultMarginLeft);
        ta.recycle();
    }


    private void initView() {
        this.setHorizontalGravity(HORIZONTAL);
    }

    private void initData() {
    }

    private void initListener() {

    }

    /**
     * Desc: 设置字体颜色
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-30
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    /**
     * Desc:设置数据
     * <p>
     * Author: 张文顺
     * Date: 2018-07-11
     *
     * @param list
     * @param spaceDp 子控件之间的间距
     */
    public void setData(ArrayList<T> list, int spaceDp) {
        mMarginLeft = spaceDp == 0 ? mMarginLeft : SizeUtils.dp2px(spaceDp);
        dataList.clear();
        dataList.addAll(list);
        this.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            View view = View.inflate(getContext(), R.layout.view_item_label, null);
            TextView tvLabel = view.findViewById(R.id.labellayout_tv_label);
            tvLabel.setTextSize(COMPLEX_UNIT_PX, mTextSize);
            tvLabel.setTextColor(mTextColor);
            tvLabel.setBackgroundResource(mbg);
            if (mLabelItemCallback != null) {
                tvLabel.setText(mLabelItemCallback.getLabel(list.get(i)));
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            if (i != 0) {
                lp.setMargins((int) mMarginLeft, 0, 0, 0);
            }
            this.addView(view);//第一个view不用设置间隔
        }
    }

    public void setData(ArrayList<T> list) {
        setData(list, 0);
    }

    public void clearData() {
        dataList.clear();
        this.removeAllViews();
    }

    /**
     * Desc:设置回调
     * <p>
     * Author: 张文顺
     * Date: 2018-07-11
     *
     * @param callback
     */
    public void setLabelItemCallback(LabelItemCallback callback) {
        mLabelItemCallback = callback;
    }

    public interface LabelItemCallback<T> {
        String getLabel(T item);
    }

    /**
     * Desc:获取数据
     * <p>
     * Author: 张文顺
     * Date: 2018-07-11
     *
     * @return array list
     */
    public ArrayList<T> getDataList() {
        return mDataList;
    }

}
