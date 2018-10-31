package com.libalum;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.libalum.shortvideo.ShortVideoPreviewActivity;
import com.libalum.utils.ShortVideoUtils;
import com.miguan.youmi.core.util.PickToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzy on 2016/4/13.
 */
public class AlbumSelectorAdapter extends BaseAdapter {

    private ImageFloder mCurrentImageFolder;
    private AlbumSelectorActivity mContext;
    private int mMaxVideoLength; // 视频最长时间
    /**
     * 已选择的图片
     */
    private ArrayList<String> mSelectedPicture;
    private AlbumBigPictureFragment mFragment;
    private FragmentTransaction mTransaction;
    private CameraListener mCameraListener;
    private BigPictureCompleteListener mBigPictureCompleteListener;

    private int mMaxNumber;
    private TextView mTvOk;
    private boolean isShowTake = true;
    /**
     * 是否已选择图片
     */
    private boolean hasSelectedImage = false;

    public AlbumSelectorAdapter(AlbumSelectorActivity mContext, ImageFloder currentImageFolder,
                                ArrayList<String> selectedPicture) {
        this.mContext = mContext;
        this.mCurrentImageFolder = currentImageFolder;
        this.mSelectedPicture = selectedPicture;
    }

    public void setOkTextView(TextView textView) {
        mTvOk = textView;
    }

    @Override
    public int getCount() {
        int addCount = isShowTake ? 1 : 0;
        return mCurrentImageFolder.images.size() + addCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_picture, null);
            holder = new ViewHolder();
            holder.ivIcon = convertView.findViewById(R.id.iv);
            holder.checkBox = convertView.findViewById(R.id.check);
            holder.ivChangeCheckBox = convertView.findViewById(R.id.iv_change_check);
            holder.ivVideoBg = convertView.findViewById(R.id.iv_bg);
            holder.ivVideo = convertView.findViewById(R.id.iv_video);
            holder.tvDuration = convertView.findViewById(R.id.tv_duration);
            holder.ivNotToSelect = convertView.findViewById(R.id.iv_not_to_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isShowTake && position == 0) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCameraListener != null) {
                        mCameraListener.onOpenCamera();
                    }
                }
            });
//            holder.ivIcon.setTag(R.string.tag_key, null);
            holder.ivIcon.setImageResource(R.mipmap.pickphotos_to_camera_normal);
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.ivNotToSelect.setVisibility(View.GONE);
            holder.ivChangeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCameraListener != null) {
                        mCameraListener.onOpenCamera();
                    }
                }
            });
        } else {
            final int correctPosition = position - (isShowTake ? 1 : 0);
            holder.checkBox.setVisibility(View.VISIBLE);
            final ImageItem item = mCurrentImageFolder.images.get(correctPosition);

            Glide.with(mContext)
                    .load("file://" + item.getPath())
                    .into(holder.ivIcon);

            final Button checkBox = holder.checkBox;
            holder.ivChangeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageSelectChanged(checkBox, item);
                }
            });
            setCheckBackground(holder.checkBox);
            holder.checkBox.setSelected(mSelectedPicture.contains(item.getPath()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick(item, correctPosition);
                }
            });
            //显示视频标识
            holder.ivVideoBg.setVisibility(item.isVideoFile() ? View.VISIBLE : View.GONE);
            holder.ivVideo.setVisibility(item.isVideoFile() ? View.VISIBLE : View.GONE);
            holder.tvDuration.setVisibility(item.isVideoFile() ? View.VISIBLE : View.GONE);
            holder.tvDuration.setText(item.getDuration());

            //视频不可打钩选择
            holder.ivChangeCheckBox.setVisibility(item.isVideoFile() ? View.GONE : View.VISIBLE);
            holder.checkBox.setVisibility(item.isVideoFile() ? View.GONE : View.VISIBLE);
            holder.ivNotToSelect.setVisibility(item.isVideoFile() && hasSelectedImage ? View.VISIBLE : View.GONE);

        }
//        setPictureParams(holder.ivIcon, parent);
        return convertView;
    }

    /**
     * 图片选择
     *
     * @param checkBox
     * @param item
     */
    private void imageSelectChanged(Button checkBox, ImageItem item) {
        if (!checkBox.isSelected()) {
            if (1 == AlbumSelectorActivity.MAX_NUM) {
                //如果为一张时，以最后一张选择为准
                this.mSelectedPicture.clear();
            } else if (mSelectedPicture.size() + 1 > AlbumSelectorActivity.MAX_NUM) {
                Toast.makeText(mContext, "最多选择" + AlbumSelectorActivity.MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (mSelectedPicture.contains(item.getPath())) {
            mSelectedPicture.remove(item.getPath());
        } else {
            mSelectedPicture.add(item.getPath());
        }
        refreshView();
        checkBox.setSelected(mSelectedPicture.contains(item.getPath()));

        notifyDataSetChanged();
    }

    public void setCameraListener(CameraListener listener) {
        this.mCameraListener = listener;
    }

    public void setBigPictureCompleteListener(BigPictureCompleteListener listener) {
        this.mBigPictureCompleteListener = listener;
    }

    public void setCurrentImageFolder(ImageFloder imageFloder) {
        this.mCurrentImageFolder = imageFloder;
    }

    public void setMaxNumber(int maxNumber) {
        this.mMaxNumber = maxNumber;
    }

    public void setShowTake(boolean showTake) {
        isShowTake = showTake;
    }

    private void setCheckBackground(Button checkBox) {
        checkBox.setBackgroundResource(R.drawable.btn_dynamic_picture_select_bg);
    }

    private void itemClick(ImageItem item, int position) {
        if (hasSelectedImage && item.isVideoFile()) {
            //已选择图片，视频不可点击
            return;
        }
        if (item.isVideoFile()) {

            int videoDuration = ShortVideoUtils.getVideoDuration(item.getPath());
            if (videoDuration < mMaxVideoLength) {
                Intent intent = new Intent(mContext, ShortVideoPreviewActivity.class);
                intent.putExtra(ShortVideoPreviewActivity.EXTRA_PATH, item.getPath());
                if (mMaxVideoLength > 0) {
                    intent.putExtra(ShortVideoPreviewActivity.EXTRA_MAX_LENGTH, mMaxVideoLength);
                }
                mContext.startActivityForResult(intent, ImageSDK.REQUEST_CODE_TAKE_VIDEO);
            } else {
                PickToast.show("请选择小于" + (mMaxVideoLength / 1000) + "的视频");
            }

        } else {

            mFragment = new AlbumBigPictureFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AlbumBigPictureFragment.STR_PATH, "" + item.getPath());

            bundle.putStringArrayList(AlbumBigPictureFragment.STR_SELECT_PICTURES, mSelectedPicture);
            //存储去除视频后的索引
            int[] savePosition = new int[1];
            bundle.putStringArrayList(AlbumBigPictureFragment.STR_ALL_PICTURES, getAllImage(savePosition, position));
            bundle.putInt(AlbumBigPictureFragment.STR_POSITION, savePosition[0]);

            bundle.putInt(ImageSDK.KEY_MAX_NUM, mMaxNumber);
            mFragment.setArguments(bundle);
            mFragment.setSelectImageListener(new AlbumBigPictureFragment.SelectImageListener() {

                @Override
                public void changeSelect(List<String> imageSelected) {
                    mSelectedPicture = (ArrayList) imageSelected;
                    refreshView();

                    notifyDataSetChanged();
                }
            });
            mFragment.setCompleteListener(new AlbumBigPictureFragment.CompleteListener() {
                @Override
                public void complete(List<String> imageSelected) {
                    if (mBigPictureCompleteListener != null) {
                        mBigPictureCompleteListener.onComplete(imageSelected);
                    }
                }
            });
            //如果transaction  commit（）过  那么我们要重新得到transaction
            mTransaction = mContext.getFragmentManager().beginTransaction();
            mTransaction.replace(android.R.id.content, mFragment);
            mTransaction.commitAllowingStateLoss();
        }

    }

    public void refreshView() {
        if (mSelectedPicture.size() > 0) {
            //图片个数大于0
            hasSelectedImage = true;

            mTvOk.setOnClickListener(mContext);
            mTvOk.setText(String.format("确定(%d)", mSelectedPicture.size()));
        } else {
            //图片个数<=0
            hasSelectedImage = false;

            mTvOk.setOnClickListener(null);
            mTvOk.setText("确定");
        }

        if (mContext != null) {
            mContext.resetOriginalText();
        }
    }

    /**
     * 获取相册中的所以图片
     *
     * @param originalPosition 所有图片时原位置
     * @return
     */
    private ArrayList getAllImage(int[] position, int originalPosition) {
        ArrayList<String> imageAll = new ArrayList<>();
        int newIndex = 0;
        for (int i = 0; i < mCurrentImageFolder.images.size(); i++) {
            ImageItem item = mCurrentImageFolder.images.get(i);
            if (originalPosition == i) {
                position[0] = newIndex;
            }
            if (!item.isVideoFile()) {//仅显示图片
                //新索引位置
                newIndex++;
                imageAll.add(item.getPath());
            }
        }
        return imageAll;
    }

    public void setHasSelectedImage(boolean hasSelectedImage) {
        this.hasSelectedImage = hasSelectedImage;
    }

    public void setMaxVideoLength(int maxVideoLength) {
        mMaxVideoLength = maxVideoLength;
    }

    public interface CameraListener {
        void onOpenCamera();
    }

    public interface BigPictureCompleteListener {
        void onComplete(List<String> imageSelected);
    }

}

class ViewHolder {
    ImageView ivIcon;
    Button checkBox;
    ImageView ivChangeCheckBox;
    ImageView ivVideoBg;
    ImageView ivVideo;
    TextView tvDuration;
    ImageView ivNotToSelect;
}