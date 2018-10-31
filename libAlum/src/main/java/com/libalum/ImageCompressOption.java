package com.libalum;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ImageCompressOption implements Serializable {
    int width = 360;   //压缩宽的最大值
    int height = 360;  //压缩高的最大值
    int quility = 95;  //压缩质量

    public static ImageCompressOption createOption(int maxWidth, int maxHeight, int quility) {
        ImageCompressOption option = new ImageCompressOption();
        option.width = maxWidth;
        option.height = maxHeight;
        option.quility = quility;
        return option;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
