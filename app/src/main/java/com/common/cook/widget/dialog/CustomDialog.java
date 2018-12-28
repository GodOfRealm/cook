package com.common.cook.widget.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.common.cook.R;
import com.common.cook.app.constant.ExtraConstant;

import butterknife.BindView;


public class CustomDialog extends BaseDialog {

    @BindView(R.id.common_tv_common_dialog_content)
    TextView mTvContent;
    @BindView(R.id.common_tv_common_dialog_left)
    TextView mTvLeft;
    @BindView(R.id.common_tv_common_dialog_right)
    TextView mTvRight;
    @BindView(R.id.common_v_common_dialog_line_vertical)
    TextView mTvVerticalLine;
    private View mDialogView;

    private int mRightStringID = -1;
    private int mRightColorID = -1;
    private View.OnClickListener mRightClickLIstener;
    private int mLeftStringID = -1;
    private int mLeftColorID = -1;
    private View.OnClickListener mLeftClickLIstener;
    private CharSequence mContent;
    private int mContentRes;
    private boolean mCanTouchOutside = true;

    public static CustomDialog newInstance(@CustomDialogType.Val int type) {
        CustomDialog fragment = new CustomDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraConstant.CUSTOMDIALOG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.common_layout_dialog;
    }

    @Override
    protected void initView(View view, AlertDialog dialog) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);
        mDialogView = view;
        int type = CustomDialogType.TYPE_DOUBLE;
        if (getArguments() != null) {
            type = getArguments().getInt(ExtraConstant.CUSTOMDIALOG_TYPE);
        }

        View.OnClickListener clickListener = v -> {
            if (mCanTouchOutside && isShowing()) {
                dismiss();
            }
        };
        dialog.setCancelable(mCanTouchOutside);
        dialog.setCanceledOnTouchOutside(mCanTouchOutside);
        mTvRight.setOnClickListener(clickListener);
        mDialogView.setOnClickListener(clickListener);
        if (type == CustomDialogType.TYPE_SINGLE) {
            mTvLeft.setVisibility(View.GONE);
            mTvVerticalLine.setVisibility(View.GONE);
        }
        initData();
    }

    public void setCanTouchOutside(boolean canTouchOutside) {
        mCanTouchOutside = canTouchOutside;
    }

    private void initData() {
        if (!TextUtils.isEmpty(mContent)) {
            mTvContent.setText(mContent);
        } else if (mContentRes > 0) {
            mTvContent.setText(mContentRes);
        }
        mTvLeft.setOnClickListener(mLeftClickLIstener != null ? mLeftClickLIstener : v -> dismiss());
        if (mLeftStringID != -1) {
            mTvLeft.setText(mLeftStringID);
        }
        if (mLeftColorID != -1) {
            mTvLeft.setTextColor(getContext().getResources().getColor(mLeftColorID));
        }

        mTvRight.setOnClickListener(mRightClickLIstener != null ? mRightClickLIstener : v -> dismiss());
        if (mRightStringID != -1) {
            mTvRight.setText(mRightStringID);
        }
        if (mRightColorID != -1) {
            mTvRight.setTextColor(getContext().getResources().getColor(mRightColorID));
        }

    }

    public void setContentText(CharSequence content) {
        mContent = content;
    }

    public void setContentText(@StringRes int stringRes) {
        mContentRes = stringRes;
    }

    public void setLeftButtonOnClick(View.OnClickListener onClickListener) {
        mLeftClickLIstener = onClickListener;
    }

    public void setLeftButton(int resourceId, final View.OnClickListener onClickListener) {
        mLeftStringID = resourceId;
        mLeftClickLIstener = onClickListener;
    }

    public void setLeftButton(int resourceId, int colorId, View.OnClickListener onClickListener) {
        mLeftColorID = colorId;
        setLeftButton(resourceId, onClickListener);
    }

    public void setRightButtonOnClick(View.OnClickListener onClickListener) {
        mRightClickLIstener = onClickListener;
    }

    public void setRightButton(int stringId, final View.OnClickListener onClickListener) {
        mRightStringID = stringId;
        mRightClickLIstener = onClickListener;
    }

    public void setRightButton(int resourceId, int colorId, View.OnClickListener onClickListener) {
        mRightColorID = colorId;
        setRightButton(resourceId, onClickListener);
    }

}
