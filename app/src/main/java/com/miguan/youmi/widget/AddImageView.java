package com.miguan.youmi.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.miguan.youmi.R;
import com.miguan.youmi.core.util.IImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 添加图片
 * 作者: zws 2018/7/4 0004 15:14
 * 功能描述:
 * 备注:
 */
public class AddImageView extends LinearLayout {
    private List<View> mItems = new ArrayList<>();
    private ArrayList<String> mItemPaths = new ArrayList<>();
    private Context mContext;
    private int mCurrentIndex;

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     *
     * @param context
     */
    public AddImageView(Context context) {
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
    public AddImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
     * @param defStyleAttr
     */
    public AddImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
        initListener();
    }


    private void initView() {
        ButterKnife.bind(this);
        this.setHorizontalGravity(HORIZONTAL);
        int width = (ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(55)) / 4;
        for (int i = 0; i < 4; i++) {
            View inflate = View.inflate(getContext(), R.layout.view_add_imge, null);
            mItemPaths.add("");
            mItems.add(inflate);
            ImageView ivPicture = inflate.findViewById(R.id.add_image_view_iv_picture);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ivPicture.getLayoutParams();
            params.width = width;
            params.height = width;
            ivPicture.setLayoutParams(params);
            if (i == 0) {
                this.addView(inflate);//第一个view不用设置间隔
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(ConvertUtils.dp2px(7), 0, 0, 0);
                inflate.setLayoutParams(lp);
                this.addView(inflate);
            }
        }

    }

    private void initData() {

    }

    private void initListener() {
        ButterKnife.apply(mItems, (view, index) -> view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView ivDelete = mItems.get(index).findViewById(R.id.add_image_view_iv_delete);
                ImageView ivPicture = mItems.get(index).findViewById(R.id.add_image_view_iv_picture);
                ivDelete.setOnClickListener(view1 ->
                {
                    mItemPaths.set(index, "");
                    ivDelete.setVisibility(GONE);
                    GlideArms.with(mContext)
                            .load("")
                            .into(ivPicture);
                });

                mCurrentIndex = index;
                if (mContext instanceof FragmentActivity) {
//                    Navigator.getInstance().getCommonNavigator().openAlbumActivity((FragmentActivity) mContext, 1, false, false);
                }

            }
        }));
    }

    /**
     * Desc:单个item设置图片数据
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     *
     * @param path
     */
    public void setItemDataPath(String path) {
        if (mCurrentIndex > mItems.size() || mCurrentIndex < 0) {
            return;
        }
        ImageView ivPicture = mItems.get(mCurrentIndex).findViewById(R.id.add_image_view_iv_picture);
        ImageView ivDelete = mItems.get(mCurrentIndex).findViewById(R.id.add_image_view_iv_delete);
        ivDelete.setVisibility(VISIBLE);
        IImageLoader.loadImage(ivPicture, path);
        mItemPaths.set(mCurrentIndex, path);

    }

    /**
     * Desc:获取路径
     * <p>
     * Author: 张文顺
     * Date: 2018-07-17
     */
    public ArrayList<String> getDataPath() {
        boolean isEmpty = true;
        for (String path : mItemPaths) {
            if (!TextUtils.isEmpty(path)) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            return new ArrayList<String>();
        }
        return mItemPaths;
    }

}
