package com.common.cook.module.common.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.common.cook.bean.Image;
import com.common.cook.core.util.IImageLoader;

import java.util.List;

/**
 * Created by SonnyJack on 2018/7/18 18:43.
 */
public class PreviewImagePagerAdapter extends PagerAdapter implements OnOutsidePhotoTapListener, OnPhotoTapListener {

    private Context mContext;
    private List<Image> mDataList;

    public PreviewImagePagerAdapter(Context context, List<Image> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        View view = (View) object;
        container.removeView(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setMinimumScale(0.5f);
        Image image = mDataList.get(position);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (null != image) {
            if (image.getHeight() > ScreenUtils.getScreenHeight() && image.getHeight() * 1.0f / image.getWidth() > 3.0f) {
                //长截图的话[宽高比大于3.0的时候，当做长截图]
                int width = (int) (ScreenUtils.getScreenWidth() * 3.0f / 5);//宽度为屏幕宽度的3/5，防止低端机内存溢出
                width = image.getWidth() > width ? width : image.getWidth();
                int height = (int) (width * image.getHeight() * 1.0f / image.getWidth());
                IImageLoader.loadImage(photoView, image.getPath(), width, height, ((imageView, drawable) -> {
                    photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    photoView.setImageDrawable(drawable);
                    Matrix matrix = new Matrix();
                    photoView.getSuppMatrix(matrix);
                    //fuck：dy不能太大，精度可能会截取，无法已到最顶部【目前dy先写死，后期可以根据imageView和drawable大小计算平移】
                    matrix.postTranslate(0, 999999);
                    photoView.post(() -> photoView.setSuppMatrix(matrix));
                }));
            } else {
                IImageLoader.loadImage(photoView, image.getPath());
            }
        }
        photoView.setOnOutsidePhotoTapListener(this);
        photoView.setOnPhotoTapListener(this);
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void onOutsidePhotoTap(ImageView imageView) {
        finishActivity();
    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        finishActivity();
    }

    private void finishActivity() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }

}
