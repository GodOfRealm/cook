package com.miguan.youmi.module.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.miguan.youmi.app.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.bean.TabEntity;
import com.miguan.youmi.module.home.ui.fragment.HomeFragment;
import com.miguan.youmi.module.main.di.component.DaggerMainComponent;
import com.miguan.youmi.module.main.di.module.MainModule;
import com.miguan.youmi.module.main.contract.MainContract;
import com.miguan.youmi.module.main.presenter.MainPresenter;

import com.miguan.youmi.R;
import com.miguan.youmi.util.EventBusUtil;

import java.util.ArrayList;

import butterknife.BindView;

@Route(path = ARouterPaths.MAIN_HOME)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.main_ctl_tab)
    CommonTabLayout mCtlTab;
    private String[] mTitles = {"主页","还款","贷款超市","我的"};
    private String[] mFragmentName = {HomeFragment.class.getName(),HomeFragment.class.getName(),HomeFragment.class.getName(),HomeFragment.class.getName()};
    private int[] mIconUnselectIds = {R.mipmap.main_ic_unselect_home,R.mipmap.main_ic_unselect_repayment,R.mipmap.main_ic_unselect_loan,R.mipmap.main_ic_unselect_mine};
    private int[] mIconSelectIds = {R.mipmap.main_ic_select_home,R.mipmap.main_ic_select_repayment,R.mipmap.main_ic_select_loan,R.mipmap.main_ic_select_mine};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

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
        ARouter.getInstance().inject(this);
        initFragments(savedInstanceState);
        initTab();
    }

    /**
     * Desc: 创建Fragment前判断是否是恢复的，比如内存不够回收，或者崩溃后恢复导致的，
     * 避免重复添加导致的重叠问题
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-17
     */
    private void initFragments(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        for (Fragment fra : manager.getFragments()) {
            manager.beginTransaction().remove(fra).commit();
        }

        for (int i = 0; i < mTitles.length; i++) {
            /*if (savedInstanceState != null) {
                 /*正常情况是使用之前保存的而不是重新创建，但这样使用没有页面，估计是随着MainActivity的销毁导致
                 与Fragment绑定的变量都销毁所以直接使用之前的会有问题*//*
                Fragment fragment = manager.getFragment(savedInstanceState, "Fragment:" + i);
                mFragments.add(fragment);
            } else { */
            mFragments.add(Fragment.instantiate(this, mFragmentName[i]));
//            }
        }
    }

    /**
     * Desc: 初始化底部Tab
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-03
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mCtlTab.setTabData(mTabEntities, this, R.id.main_rl_content, mFragments);
        mCtlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                if (position != TAB_MSG) {
//                    mPresenter.getUnreadMsg();
//                }
//                if (position == TAB_MINE) {
//                    EventBusUtil.postEvent(EventBusTags.UPDATE_USER_INFO);
//                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

}
