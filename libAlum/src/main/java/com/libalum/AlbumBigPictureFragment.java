package com.libalum;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.miguan.youmi.core.util.PickToast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wzy on 2016/4/13.
 */
public class AlbumBigPictureFragment extends Fragment {

    public static final String STR_PATH = "str_path";
    public static final String STR_POSITION = "str_position";
    public static final String STR_SELECT_PICTURES = "str_select_pictures";
    public static final String STR_ALL_PICTURES = "str_all_pictures";
    private HashMap<Integer, View> mViewList = new HashMap();
    private String mPath;
    private FragmentTransaction mTransaction;
    private ArrayList<String> mSelectedPicture;
    private ArrayList<String> mImageAll;
    private ImageView back;
    private TextView mTvNumber;
    private ImageView mCheckBox;
    private TextView ok;
    private SelectImageListener mSelectImageListener;
    private CompleteListener mCompleteListener;
    private ViewPager mViewPager;
    private int mCurrentPosition;
    private TextView mTvDiy;
    private boolean mIsShowDiy;
    private int mMaxNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            mPath = bundle.getString(STR_PATH);
            mSelectedPicture = bundle.getStringArrayList(STR_SELECT_PICTURES);
            mImageAll = bundle.getStringArrayList(STR_ALL_PICTURES);
            mCurrentPosition = bundle.getInt(STR_POSITION);
            mIsShowDiy = bundle.getBoolean(ImageSDK.KEY_IS_SHOW_DIY);
            mMaxNumber = bundle.getInt(ImageSDK.KEY_MAX_NUM);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_album_big_picture, null);
        initView(view);
        initData();
        return view;
    }


    public void setSelectImageListener(SelectImageListener listener) {
        mSelectImageListener = listener;
    }

    public void setCompleteListener(CompleteListener listener) {
        mCompleteListener = listener;
    }

    private void initView(View view) {
        back = view.findViewById(R.id.back);
        mTvNumber = view.findViewById(R.id.tv_number);
        mCheckBox = view.findViewById(R.id.checkBox);
        ok = view.findViewById(R.id.ok);
        mViewPager = view.findViewById(R.id.vp_picture);
        mTvDiy = view.findViewById(R.id.tv_diy);
    }

    private void initData() {
        if (mSelectedPicture != null) {
            refreshNumberView();
        }
        final boolean isSelected = mSelectedPicture.contains(mPath);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
                if (mSelectImageListener != null) {
                    mSelectImageListener.changeSelect(mSelectedPicture);
                }
            }
        });
        mCheckBox.setSelected(isSelected);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPicture.contains(mPath)) {
                    mSelectedPicture.remove(mPath);
                    mCheckBox.setSelected(false);
                } else {
                    if (1 == mMaxNumber && mSelectedPicture != null && 1 == mSelectedPicture.size()) {
                        mSelectedPicture.clear();
                    }
                    if (mSelectedPicture != null && mSelectedPicture.size() < mMaxNumber) {
                        mSelectedPicture.add(mPath);
                        mCheckBox.setSelected(true);
                    } else {
                        PickToast.show("最多选择" + mMaxNumber + "张");
                    }
                }
                if (mSelectedPicture != null) {
                    refreshNumberView();
                }
            }
        });

        mViewPager.setAdapter(new MyPagerAdapter(mImageAll));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPath = mImageAll.get(position);
                mCheckBox.setSelected(mSelectedPicture.contains(mImageAll.get(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(mCurrentPosition);

        if (mIsShowDiy) {
            mTvDiy.setVisibility(View.VISIBLE);
        } else {
            mTvDiy.setVisibility(View.GONE);
        }

        if (mMaxNumber == 1) {
            ok.setText("确定");
        }

    }

    public void refreshNumberView() {
        if (mSelectedPicture.size() > 0) {
            mTvNumber.setText("" + mSelectedPicture.size());
            mTvNumber.setVisibility(View.VISIBLE);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtil.showToast(""+selectedPicture.size());//显示大图时，完成
                    if (mCompleteListener != null) {
                        mCompleteListener.complete(mSelectedPicture);
                    }
                }
            });
            mTvDiy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCompleteListener != null) {
                        mCompleteListener.complete(mSelectedPicture);
                    }
                }
            });
            ok.setTextColor(getResources().getColor(R.color.white));
            mTvDiy.setTextColor(getResources().getColor(R.color.white));
        } else {
            mTvNumber.setVisibility(View.GONE);
            ok.setOnClickListener(null);
            mTvDiy.setOnClickListener(null);
            ok.setTextColor(getResources().getColor(R.color.color_3e3e3e));
            mTvDiy.setTextColor(getResources().getColor(R.color.color_3e3e3e));
        }
    }

    private void hideFragment() {
        mTransaction = getFragmentManager().beginTransaction();
        mTransaction.hide(AlbumBigPictureFragment.this);
        mTransaction.commitAllowingStateLoss();
    }

    public interface SelectImageListener {
        void changeSelect(List<String> imageSelected);
    }

    public interface CompleteListener {
        void complete(List<String> imageSelected);
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
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
