package com.common.cook.module.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.common.cook.R;
import com.common.cook.app.BaseActivity;
import com.common.cook.app.BaseFragment;
import com.common.cook.module.commodity.ui.fragment.CommodityHomeFragment;
import com.common.cook.module.main.contract.MainContract;
import com.common.cook.module.main.di.component.DaggerMainComponent;
import com.common.cook.module.main.di.module.MainModule;
import com.common.cook.module.main.presenter.MainPresenter;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener {
    @BindView(R.id.main_vp_content)
    ViewPager mViewPager;
    @BindView(R.id.main_stl_menu)
    SlidingTabLayout mSlidingTabLayout;
    private String[] mTitles = new String[]{"商品", "点赞"};
    private ArrayList mFragments = new ArrayList<BaseFragment>() {{
        add(CommodityHomeFragment.newInstance());
        add(CommodityHomeFragment.newInstance());
    }};

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mViewPager.addOnPageChangeListener(this);
        mSlidingTabLayout.setViewPager(mViewPager, mTitles, this, mFragments);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
