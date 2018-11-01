package com.miguan.youmi.module.home.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.base.BaseListFragment;
import com.miguan.youmi.app.base.EmptyView;
import com.miguan.youmi.bean.home.Banner;
import com.miguan.youmi.bean.home.HomeCreditRecommendBean;
import com.miguan.youmi.bean.home.HomeHotRecommendBean;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.core.base.BaseMixAdapter;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.module.home.di.component.DaggerHomeComponent;
import com.miguan.youmi.module.home.di.module.HomeModule;
import com.miguan.youmi.module.home.contract.HomeContract;
import com.miguan.youmi.module.home.presenter.HomePresenter;
import com.miguan.youmi.module.home.ui.adapter.HomeCreditRecommend;
import com.miguan.youmi.module.home.ui.adapter.HomeHeaderBannerAdapter;
import com.miguan.youmi.module.home.ui.adapter.HomeHotRecommend;
import com.miguan.youmi.widget.VerticalTextview;
import com.miguan.youmi.widget.tablayout.SlidingTabLayout;
import com.tmall.ultraviewpager.UltraViewPager;


import java.util.ArrayList;
import java.util.List;

/**
 * Desc:首页home
 * <p>
 * Author: 张文顺
 * Date: 2018-10-31
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class HomeFragment extends BaseListFragment<Object, HomePresenter> implements HomeContract.View<Object> {
    private String[] mFragmentsItemTitle = new String[]{
            "余额代偿", "信用代还"
    };
    ViewPager mHeadViewPager;
    SlidingTabLayout mHeadSlidingTabLayout;
    private VerticalTextview mVtvSwitch;//滚动广告
    private ArrayList<String> titleList = new ArrayList<String>();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void begin() {
        //头部
        View top = LayoutInflater.from(getContext()).inflate(R.layout.home_head_layput, getRootView(), false);
        mHeadViewPager = top.findViewById(R.id.home_head_viewPager);
        mHeadSlidingTabLayout = top.findViewById(R.id.home_head_menu);
        mVtvSwitch = top.findViewById(R.id.home_head_vtv_switch);
        mAdapter.addHeaderView(top);
        //关闭下拉刷新
        setRefreshEnable(false);
        //关闭上拉加载
        setLoadMoreEnable(false);
        // 余额代偿、信用代还fragment
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(BalanceFragment.newInstance());
        fragmentList.add(CreditFragment.newInstance());
        mHeadSlidingTabLayout.setViewPager(mHeadViewPager, mFragmentsItemTitle, getActivity(), fragmentList);
        //滚动广告
        titleList.add("熬过最难熬的那段时间");
        titleList.add("你擦干眼泪终于明白");
        titleList.add("有些人");
        titleList.add("注定是让你成长的");
        titleList.add("正如");
        titleList.add("至尊宝在离开紫霞仙子后");
        titleList.add("才真正成长为孙悟空一样");
        titleList.add(getString(R.string.new_password));
        mVtvSwitch.setTextList(titleList);
        mVtvSwitch.setText(13, 50, getActivity().getResources().getColor(R.color.color_text_666666));//设置属性
        mVtvSwitch.setTextStillTime(3000);//设置停留时长间隔
        mVtvSwitch.setAnimTime(1000);//设置进入和退出的时间间隔
        mVtvSwitch.setOnItemClickListener(position ->
                PickToast.show(position + "")
        );
        mVtvSwitch.startAutoScroll();
        //head banner相关
        UltraViewPager ultraViewPager = top.findViewById(R.id.home_head_vp_banner);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        List<Banner> mBannerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mBannerList.add(new Banner());
        }
        HomeHeaderBannerAdapter adapter = new HomeHeaderBannerAdapter(mBannerList);
        ultraViewPager.setAdapter(adapter);
        //initialize built-in indicator
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setIndicatorPadding(SizeUtils.dp2px(9))
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getContext().getResources().getColor(R.color.colorTextTertiary))
                .setNormalColor(getContext().getResources().getColor(R.color.color_f0f0f0))
                .setRadius(SizeUtils.dp2px(3))
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        ultraViewPager.getIndicator().build();
        ultraViewPager.setAutoScroll(3000);
        ultraViewPager.setInfiniteLoop(true);
        mAdapter.addData(new HomeHotRecommendBean());
        mAdapter.addData(new HomeCreditRecommendBean());
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void setupEmptyView(EmptyView emptyView) {
        emptyView.setEmptyText("没有相关数据哦~")
                .setEmptyImageResource(R.mipmap.ic_launcher)
                .setRetryText("重试");
    }

    @Override
    public BaseAdapter<Object> generateAdapter() {
        BaseMixAdapter baseMixAdapter = new BaseMixAdapter();
        baseMixAdapter.addItemPresenter(new HomeHotRecommend());
        baseMixAdapter.addItemPresenter(new HomeCreditRecommend());
        return baseMixAdapter;
    }
}
