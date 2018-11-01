package com.miguan.youmi.module.home.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.miguan.youmi.R;
import com.miguan.youmi.bean.home.Banner;

import java.util.List;

/**
 * Created by SonnyJack on 2018/7/3 17:00.
 */
public class HomeHeaderBannerAdapter extends PagerAdapter {

    private List<Banner> mBannerList;


    public HomeHeaderBannerAdapter(List<Banner> banners) {
        mBannerList = banners;
    }

    @Override
    public int getCount() {
        return mBannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.base_viewpager_item_image, null);
        ImageView imageView = view.findViewById(R.id.base_view_pager_iv_image);
        imageView.setImageResource(R.mipmap.home_banner_test);
//        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//        layoutParams.width = mImageWidth;
//        layoutParams.height = mImageHeight;
//        imageView.setLayoutParams(layoutParams);

//        Banner banner = mBannerList.get(position);
//        IImageLoader.loadImage(imageView, banner.getImage());
//        view.setOnClickListener(v -> {
//            Navigator.getInstance().getCommonNavigator().openWebActivity(banner.getContent());
//            AnalysisManager.getInstance().clickBanner(banner, position);
//        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
