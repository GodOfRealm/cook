package com.libalum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lmy on 2018/3/27.
 * 大图预览activity
 */

public class ImageBrowserActivity extends AppCompatActivity {

    public static final String STR_POSITION = "str_position";

    public static final String STR_ALL_PICTURES = "str_all_pictures";
    private HashMap<Integer, View> mViewList = new HashMap();

    private ArrayList<String> mImageAll;
    private ImageView back;
    private TextView mTvNumber;
    private ViewPager mViewPager;
    private int mCurrentPosition;

    /**
     * 图片列表size
     */
    private int mImageSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);

        Intent bundle = getIntent();//从activity传过来的Bundle
        if (bundle != null) {
            mImageAll = bundle.getStringArrayListExtra(STR_ALL_PICTURES);
            mCurrentPosition = bundle.getIntExtra(STR_POSITION, 0);
        }

        mImageSize = mImageAll.size();

        initView();
        initData();
    }

    private void initView() {
        back = findViewById(R.id.back);
        mTvNumber = findViewById(R.id.tv_number);
        mViewPager = findViewById(R.id.vp_picture);

        mTvNumber.setText(1 + "/" + mImageSize);
    }

    private void initData() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager.setAdapter(new MyPagerAdapter(mImageAll));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvNumber.setText((position + 1) + "/" + mImageSize);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(mCurrentPosition);

    }

    private class MyPagerAdapter extends PagerAdapter {

        ArrayList imagesAll;

        public MyPagerAdapter(ArrayList imagesAll) {
            this.imagesAll = imagesAll;
        }

        @Override
        public int getCount() {
            return imagesAll.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.album_big_picture_item, null);
            mViewList.put(position, layout); //将View加入到容器和container里面
            ((ViewPager) container).addView(layout, 0);
            ImageView item = layout.findViewById(R.id.iv_picture_item);
            Glide.with(layout.getContext())
                    .load("file://" + mImageAll.get(position))
                    .into(item);

            return layout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mViewList.get(position));
            mViewList.remove(position);
        }
    }
}
