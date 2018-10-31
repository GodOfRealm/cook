package com.miguan.youmi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.miguan.youmi.R;

import java.lang.reflect.Field;


/**
 * Desc: 搜索控件
 * <p>
 * Author: SonnyJack
 * Date: 2018-06-29
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class SearchLayout extends LinearLayout implements View.OnClickListener {

    /*  用法
    xmlns:app="http://schemas.android.com/apk/res-auto"
    <com.miguan.youmi.widget.SearchLayout
        android:id="@+id/main_sl_search"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_marginTop="70dp"
        android:background="@android:color/darker_gray"
        app:input_background="@drawable/common_search_input_bg"
        app:input_hint_text="@string/common_search_hint_txt"
        app:input_hint_text_color="@android:color/white"
        app:right_text="@string/app_name"/>
    */

    private SearchListener mSearchListener;
    private SearchEditText mEtSearch;
    private TextView mTvRightSearch;//右边搜索按钮
    private String mTxtSearch, mTxtCancel;//搜索、取消文字

    //先不提供直接实例化
    private SearchLayout(Context context) {
        super(context);
        init(null);
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @param context
     * @param attrs
     */
    public SearchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Desc: 初始化搜索布局
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @return string
     */
    private void init(AttributeSet attrs) {
        mTxtSearch = getResources().getString(R.string.search);
        mTxtCancel = getResources().getString(R.string.cancel);
        initView();
        initAttrs(attrs);
        setRightBtnText(mTxtCancel);//默认显示取消文字
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = null == s ? 0 : s.length();
                setRightBtnText(length > 0 ? mTxtSearch : mTxtCancel);
                if (null != mSearchListener) {
                    mSearchListener.onTextChangedListener(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvRightSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setRightBtnText(mTxtSearch);
            } else {
                setRightBtnText(mTxtCancel);
            }
        });
        mEtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (null != mSearchListener) {
                    mSearchListener.onClickRightButton(getText());
                }
                hideSoftInput(mEtSearch);
                return true;
            }
            return false;
        });
        mTvRightSearch.setOnClickListener(this::onClick);
    }

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_layout_search, null);
        addView(view);

        //输入框
        mEtSearch = findViewById(R.id.common_et_search_key);
        //右边取消、搜索按钮
        mTvRightSearch = findViewById(R.id.common_tv_search_txt);
    }

    /**
     * Desc: 填充XML设置的相关属性
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @return string
     */
    private void initAttrs(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.search_layout);
        //输入框背景
        int background = typedArray.getResourceId(R.styleable.search_layout_input_background, R.drawable.common_search_input_bg);
        mEtSearch.setBackgroundResource(background);
        //左边搜索icon
        int searchIcon = typedArray.getResourceId(R.styleable.search_layout_left_search_icon, R.mipmap.common_ic_search);
        Drawable drawable = getResources().getDrawable(searchIcon);
        mEtSearch.setLeftIcon(drawable);
        //输入文本相关
        int textColor = typedArray.getColor(R.styleable.search_layout_input_text_color, Color.GRAY);
        mEtSearch.setTextColor(textColor);
        int textSize = typedArray.getDimensionPixelOffset(R.styleable.search_layout_input_text_size, 13);
        mEtSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int textHintColor = typedArray.getColor(R.styleable.search_layout_input_hint_text_color, Color.GRAY);
        mEtSearch.setHintTextColor(textHintColor);
        String textHint = typedArray.getString(R.styleable.search_layout_input_hint_text);
        mEtSearch.setHint(textHint);
        //右边删除icon
        int deleteIcon = typedArray.getResourceId(R.styleable.search_layout_right_delete_icon, R.mipmap.common_ic_delete);
        drawable = getResources().getDrawable(deleteIcon);
        mEtSearch.setRightIcon(drawable);
        mEtSearch.showRightIcon(false);
        //右边搜索文字相关
        String rightTxt = typedArray.getString(R.styleable.search_layout_right_text);
        if (!TextUtils.isEmpty(rightTxt)) {
            mTxtSearch = rightTxt;

        }
        textColor = typedArray.getColor(R.styleable.search_layout_right_text_color, Color.WHITE);
        mTvRightSearch.setTextColor(textColor);
        textSize = typedArray.getDimensionPixelSize(R.styleable.search_layout_right_text_size, 13);
        mTvRightSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        typedArray.recycle();
    }

    /**
     * Desc: 获取输入文本
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @return string
     */
    public String getText() {
        if (null != mEtSearch) {
            return mEtSearch.getText().toString().trim();
        }
        return null;
    }

    /**
     * Desc:  设置右边文本
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     */
    public void resetRightBtnText() {
        setRightBtnText(mTxtCancel);
    }

    /**
     * Desc:  设置右边文本
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @param value
     */
    public void setRightBtnText(String value) {
        if (null == mTvRightSearch) {
            return;
        }
        mTvRightSearch.setText(value);
    }

    public void setMaxLength(int maxLength) {
        if (null == mEtSearch) {
            return;
        }
        mEtSearch.setFilters(new InputFilter[]{new MaxTextLengthFilter(maxLength)});
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_tv_search_txt) {
            //右边按钮点击
            if (null == mSearchListener) return;
            if (TextUtils.equals(mTxtCancel, mTvRightSearch.getText())) {
                mSearchListener.onCancel();
            } else {//取消
                mSearchListener.onClickRightButton(getText());
            }
        }
    }

    /**
     * Hide the soft input.
     * 隐藏输入键盘
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Desc: 设置监听
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     *
     * @param searchListener
     */
    public void setSearchListener(SearchListener searchListener) {
        mSearchListener = searchListener;
    }

    public SearchEditText getEtSearch() {
        return mEtSearch;
    }

    public boolean setInputCursorDrawable(int res) {
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtSearch, res);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-29
     * Copyright: Copyright (c) 2013-2018
     * Company: @米冠网络
     * Update Comments:
     */
    public static class SearchListener {
        /**
         * Desc:  右边按钮点击监听方法
         * <p>
         * Author: SonnyJack
         * Date: 2018-06-29
         *
         * @param value
         */
        public void onClickRightButton(String value) {

        }

        /**
         * Desc: 输入框文本变化监听方法
         * <p>
         * Author: SonnyJack
         * Date: 2018-06-29
         *
         * @param value
         */
        public void onTextChangedListener(String value) {

        }

        /**
         * Desc: 取消
         * <p>
         * Author: SonnyJack
         * Date: 2018-06-29
         */
        public void onCancel() {

        }
    }
}
