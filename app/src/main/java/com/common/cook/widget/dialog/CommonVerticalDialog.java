package com.common.cook.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.cook.R;
import com.common.cook.core.base.BaseAdapter;
import com.common.cook.core.base.ViewHolder;
import com.common.cook.widget.RecyclerViewLinearLayoutSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by SonnyJack on 2018/7/25 17:36.
 */
public class CommonVerticalDialog extends BaseDialog {

    private int mLayoutRes = R.layout.common_vertical_item;
    private CommonVerticalDialogCallback mCallback;

    public static CommonVerticalDialog newInstance(ArrayList<String> menus) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("menu", menus);
        CommonVerticalDialog commonVerticalDialog = new CommonVerticalDialog();
        commonVerticalDialog.setArguments(bundle);
        return commonVerticalDialog;
    }

    private ArrayList<String> mMenus;

    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    @BindView(R.id.common_rv_vertical)
    RecyclerView mRecyclerView;

    @Override
    public void onStart() {
        super.onStart();
        //设置宽度
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.common_dialog_vertical;
    }

    @Override
    protected void initView(View view, AlertDialog dialog) {
        Bundle arguments = getArguments();
        mMenus = arguments.getStringArrayList("menu");
        initRecyclerView();
        setData();
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        int itemSize = SizeUtils.dp2px(0.5f);
        RecyclerViewLinearLayoutSpace recyclerViewLinearLayoutSpace = new RecyclerViewLinearLayoutSpace(
                LinearLayoutManager.HORIZONTAL,
                itemSize,
                getResources().getColor(R.color.color_eeeeee)
        );
        mRecyclerView.addItemDecoration(recyclerViewLinearLayoutSpace);
    }

    private void setData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(mLayoutRes, mMenus);
        arrayAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(adapter, view, position);
            }
            if (mCallback != null) {
                mCallback.onItemClick(adapter, view, position);
            }
        });
        mRecyclerView.setAdapter(arrayAdapter);
    }

    public interface CommonVerticalDialogCallback {
        void onItemClick(BaseQuickAdapter adapter, View view, int position);

        void convertItem(ViewHolder helper, String item);
    }

    /**
     * Desc: 设置自定义item布局
     * <p>
     * Author: QiuRonaC
     * Date: 2018-07-27
     */
    public void setDialogItemLayoutRes(int layoutRes, CommonVerticalDialogCallback callback) {
        mLayoutRes = layoutRes;
        mCallback = callback;
    }

    private class ArrayAdapter extends BaseAdapter<String> {

        public ArrayAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, String item) {
            if (mCallback != null) {
                mCallback.convertItem(helper, item);
            } else {
                helper.setText(R.id.common_tv_vertical_item, item);
            }
        }
    }
}
