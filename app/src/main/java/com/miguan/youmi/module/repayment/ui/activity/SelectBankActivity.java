package com.miguan.youmi.module.repayment.ui.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;

import com.miguan.youmi.R;
import com.miguan.youmi.app.base.BaseListActivity;
import com.miguan.youmi.app.base.EmptyView;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.bean.repayment.BankItemBean;
import com.miguan.youmi.core.base.BaseAdapter;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.module.repayment.ui.adapter.SelectBankAdapter;

import com.miguan.youmi.module.repayment.di.component.DaggerSelectBankComponent;
import com.miguan.youmi.module.repayment.di.module.SelectBankModule;
import com.miguan.youmi.module.repayment.contract.SelectBankContract;
import com.miguan.youmi.module.repayment.presenter.SelectBankPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPaths.REPAYMENT_SELECT_BANK)
public class SelectBankActivity extends BaseListActivity<BankItemBean, SelectBankPresenter> implements SelectBankContract.View<BankItemBean> {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectBankComponent
                .builder()
                .appComponent(appComponent)
                .selectBankModule(new SelectBankModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void begin() {
        //九宫格间距
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.top = SizeUtils.dp2px(12);
                if (position % 3 == 1) {//中间列
                    outRect.left = SizeUtils.dp2px(11);
                    outRect.right = SizeUtils.dp2px(11);
                } else if (position % 3 == 2) {//右侧
                    outRect.left = SizeUtils.dp2px(0);
                    outRect.right = SizeUtils.dp2px(16);
                } else {
                    outRect.left = SizeUtils.dp2px(16);
                    outRect.right = SizeUtils.dp2px(0);
                }
            }
        });
        //测试数据
        List<BankItemBean> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new BankItemBean());
        }
        mAdapter.setNewData(list);
        //关闭刷新与加载
        setLoadMoreEnable(false);
        setRefreshEnable(false);
        //头部
        View inflate = LayoutInflater.from(this).inflate(R.layout.common_title_head_layout, getRootView(), false);
        getRootView().addView(inflate, 1);
        //item 点击监听事件
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PickToast.show(position +"");
        });

    }

    @Override
    public void setupEmptyView(EmptyView emptyView) {
        emptyView.setEmptyText("还没有相关数据哦~")
                .setEmptyImageResource(R.mipmap.ic_launcher)
                .setRetryText("重试");
    }

    @Override
    public RecyclerView.LayoutManager generateLayoutManager() {
        return new GridLayoutManager(this, 3);
    }

    @Override
    public BaseAdapter<BankItemBean> generateAdapter() {
        return new SelectBankAdapter();
    }
}
