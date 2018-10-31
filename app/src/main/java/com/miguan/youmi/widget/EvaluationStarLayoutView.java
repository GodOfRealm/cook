package com.miguan.youmi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miguan.youmi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 星星评价
 * 作者: zws 2018/7/4 0004 15:14
 * 功能描述:
 * 备注:
 */
public class EvaluationStarLayoutView extends LinearLayout {
    private List<ImageView> mItems = new ArrayList<>();
    private ArrayList<String> mItemPaths = new ArrayList<>();
    private Context mContext;
    private int mCurrentIndex;
    private boolean mCanChange;//是否可以点击更改评价
    private int mSelected;//已评价icn
    private int mUnSelected;//未已评价icn
    private float mStarsPace; //星星间距
    private int mSelectedCount;//评价数
    private int mStarWidth; // 星星宽度
    private int mStarHeight; //星星高度

    private OnStarSelectedListener mStarSelectedListener;

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     *
     * @param context
     */
    public EvaluationStarLayoutView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
        initListener();
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     *
     * @param context
     * @param attrs
     */
    public EvaluationStarLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
        initView();
        initData();
        initListener();
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public EvaluationStarLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
        initView();
        initData();
        initListener();
    }

    private void init(Context context, AttributeSet attrs) {
        float defaultSpace = context.getResources().getDimension(R.dimen.star_default_space);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EvaluationStarLayoutView);
        mCanChange = ta.getBoolean(R.styleable.EvaluationStarLayoutView_star_change, true);
        mSelected = ta.getResourceId(R.styleable.EvaluationStarLayoutView_star_selected, R.mipmap.order_ic_yellow_star);
        mUnSelected = ta.getResourceId(R.styleable.EvaluationStarLayoutView_star_unselected, R.mipmap.order_ic_gray_star);
        mSelectedCount = ta.getInteger(R.styleable.EvaluationStarLayoutView_star_selected_count, 0);
        mStarsPace = ta.getDimension(R.styleable.EvaluationStarLayoutView_star_space, defaultSpace);
        mStarWidth = (int) ta.getDimension(R.styleable.EvaluationStarLayoutView_star_width, 0);
        mStarHeight = (int) ta.getDimension(R.styleable.EvaluationStarLayoutView_star_height, 0);
        if (mSelectedCount > 5) {
            mSelectedCount = 5;
        }
    }


    private void initView() {
        ButterKnife.bind(this);
        this.setHorizontalGravity(HORIZONTAL);
        for (int i = 0; i < 5; i++) {
            ImageView ivStar = new ImageView(mContext);
            ivStar.setBackgroundResource(mUnSelected);
            ivStar.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mItems.add(ivStar);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (mStarWidth > 0) {
                lp.width = mStarWidth;
            }
            if (mStarHeight > 0) {
                lp.height = mStarHeight;
            }
            if (i != 0) {
                lp.setMargins((int) mStarsPace, 0, 0, 0);
            }
            ivStar.setLayoutParams(lp);
            this.addView(ivStar);
        }

    }

    private void initData() {
        setSelected(mSelectedCount);
    }

    private void initListener() {
        ButterKnife.apply(mItems, (view, index) -> view.setOnClickListener(view12 -> {
            if (mCanChange) {
                updateSelectedView(index + 1);
            }
        }));
    }

    public void setSelected(float count) {
        if (count > 5) {
            count = 5;
        }
        updateSelectedView((int) count);
    }

    /**
     * Desc: 更新界面
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param count
     */
    private void updateSelectedView(int count) {
        mSelectedCount = count;
        for (int i = 0; i < mItems.size(); i++) {
            if (i < count) {
                mItems.get(i).setBackgroundResource(mSelected);
            } else {
                mItems.get(i).setBackgroundResource(mUnSelected);
            }
        }
        if (mStarSelectedListener != null) {
            mStarSelectedListener.onStarSelected(mSelectedCount);
        }
    }

    public boolean isCanChange() {
        return mCanChange;
    }

    public void setCanChange(boolean canChange) {
        mCanChange = canChange;
    }

    public int getSelectedCount() {
        return mSelectedCount;
    }

    /**
     * Desc: 设置选中监听
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param listener
     */
    public void setOnStarSelectedListener(OnStarSelectedListener listener) {
        mStarSelectedListener = listener;
    }

    public interface OnStarSelectedListener {
        void onStarSelected(int star);
    }

}
