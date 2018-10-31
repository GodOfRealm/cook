package com.libalum.imageloader;


import com.libalum.R;

/**
 * 获取不同默认图片工厂
 */
public class ImageOptionFactory {

    public final static int OPTION_DEF = 1;
    public final static int OPTION_USER = 3;
    public final static int OPTION_NO_LOAD_IMAGE = 4;
    public final static int OPTION_NUll_IMAGE = 5;

    public static int createImageOption(int type) {
        switch (type) {
            case OPTION_DEF:
                return R.mipmap.common_ic_def_avatar;
            case OPTION_USER:
                return R.mipmap.common_ic_def_avatar;
            case OPTION_NO_LOAD_IMAGE:
                return -1;
            case OPTION_NUll_IMAGE:
                return R.mipmap.pic_null;

        }
        return -1;
    }

}
