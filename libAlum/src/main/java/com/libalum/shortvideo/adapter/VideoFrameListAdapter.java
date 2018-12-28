package com.libalum.shortvideo.adapter;


import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.util.LruCache;

import com.common.cook.core.base.ViewHolder;
import com.libalum.R;
import com.libalum.shortvideo.entity.VideoFrame;
import com.common.cook.core.base.BaseAdapter;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class VideoFrameListAdapter extends BaseAdapter<VideoFrame> {

    private int selectedPosition = -1;
    private MediaMetadataRetriever videoRetriever;
    private LruCache<Long, Bitmap> bitmapCache;
    private int frameWidth;
    private OnFrameLoadListener onFrameLoadListener;
    private boolean cacheFullBitmap;

    public VideoFrameListAdapter() {
        super(R.layout.short_video_item_frame);
        bitmapCache = new LruCache<>(50);
    }

    public void setCacheFullBitmap(boolean cacheFullBitmap) {
        this.cacheFullBitmap = cacheFullBitmap;
    }

    public void setOnFrameLoadListener(OnFrameLoadListener onFrameLoadListener) {
        this.onFrameLoadListener = onFrameLoadListener;
    }

    @Override
    protected void convert(ViewHolder holder, VideoFrame item) {
        if (frameWidth > 0) {
            holder.getConvertView().getLayoutParams().width = frameWidth;
        }
        holder.setImageBitmap(R.id.frame_image, null);
        if (videoRetriever != null) {
            fetchFrameBitmap(holder, holder.getAdapterPosition(), item);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    private void fetchFrameBitmap(final ViewHolder holder, final int position, final VideoFrame frame) {
        Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() {
                if (holder.getAdapterPosition() != position) return null;
                Bitmap bitmap = bitmapCache.get(frame.time);
                if (bitmap == null) {
                    bitmap = videoRetriever.getFrameAtTime(frame.time * 1000);
                    if (cacheFullBitmap && bitmap != null) {
                        frame.frameBitmap = bitmap;
                        bitmapCache.put(frame.time, bitmap);
                    }
                    if (bitmap != null) {
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 90, 160, !cacheFullBitmap ? ThumbnailUtils.OPTIONS_RECYCLE_INPUT : 0);
                        if (!cacheFullBitmap && bitmap != null) {
                            bitmapCache.put(frame.time, bitmap);
                        }
                    }
                }
                if (bitmap != null && position == holder.getAdapterPosition()) {
                    return bitmap;
                }
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {
                        if (bitmap != null && holder.getAdapterPosition() == position) {
                            if (onFrameLoadListener != null) {
                                onFrameLoadListener.onFrameLoad(frame, position);
                            }
                            holder.setImageBitmap(R.id.frame_image, bitmap);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                });
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public void setVideoRetriever(MediaMetadataRetriever videoRetriever) {
        this.videoRetriever = videoRetriever;
    }

    public interface OnFrameLoadListener {
        void onFrameLoad(VideoFrame frame, int position);
    }
}
