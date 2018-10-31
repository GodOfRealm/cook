package com.libalum.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtil {
    private static final String TAG = "Util";

    public BitmapUtil() {
    }

    public static String getBase64FromBitmap(Bitmap bitmap) {
        String base64Str = null;
        ByteArrayOutputStream baos = null;

        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 40, baos);
                byte[] bitmapBytes = baos.toByteArray();
                base64Str = Base64.encodeToString(bitmapBytes, 2);
                Log.d("base64Str", "" + base64Str.length());
                baos.flush();
                baos.close();
            }
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

        return base64Str;
    }

    public static Bitmap getBitmapFromBase64(String base64Str) {
        if (TextUtils.isEmpty(base64Str)) {
            return null;
        } else {
            byte[] bytes = Base64.decode(base64Str, 2);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }

    public static Bitmap getResizedBitmap(Context context, Uri uri, int widthLimit, int heightLimit) throws IOException {
        String path = null;
        Bitmap result = null;
        if (uri.getScheme().equals("file")) {
            path = uri.toString().substring(5);
        } else {
            if (!uri.getScheme().equals("content")) {
                return null;
            }

            Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(0);
            cursor.close();
        }

        ExifInterface exifInterface = new ExifInterface(path);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int orientation = exifInterface.getAttributeInt("Orientation", 0);
        int width;
        if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
            width = widthLimit;
            widthLimit = heightLimit;
            heightLimit = width;
        }

        width = options.outWidth;
        int height = options.outHeight;
        int sampleW = 1;

        int sampleH;
        for (sampleH = 1; width / 2 > widthLimit; sampleW <<= 1) {
            width /= 2;
        }

        while (height / 2 > heightLimit) {
            height /= 2;
            sampleH <<= 1;
        }

        options = new Options();
        int sampleSize;
        if (widthLimit != 2147483647 && heightLimit != 2147483647) {
            sampleSize = Math.max(sampleW, sampleH);
        } else {
            sampleSize = Math.max(sampleW, sampleH);
        }

        options.inSampleSize = sampleSize;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError var22) {
            var22.printStackTrace();
            options.inSampleSize <<= 1;
            bitmap = BitmapFactory.decodeFile(path, options);
        }

        Matrix matrix = new Matrix();
        if (bitmap == null) {
            return bitmap;
        } else {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
                int tmp = w;
                w = h;
                h = tmp;
            }

            switch (orientation) {
                case 2:
                    matrix.preScale(-1.0F, 1.0F);
                    break;
                case 3:
                    matrix.setRotate(180.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 4:
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 5:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 6:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 7:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 8:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
            }

            float xS = (float) widthLimit / (float) bitmap.getWidth();
            float yS = (float) heightLimit / (float) bitmap.getHeight();
            matrix.postScale(Math.min(xS, yS), Math.min(xS, yS));

            try {
                result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                return result;
            } catch (OutOfMemoryError var21) {
                var21.printStackTrace();
                Log.d("ResourceCompressHandler", "OOMHeight:" + bitmap.getHeight() + "Width:" + bitmap.getHeight() + "matrix:" + xS + " " + yS);
                return null;
            }
        }
    }

    public static Bitmap interceptBitmap(String filePath, int w, int h) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        int xTopLeft = (widthOrg - w) / 2;
        int yTopLeft = (heightOrg - h) / 2;
        if (xTopLeft > 0 && yTopLeft > 0) {
            try {
                Bitmap result = Bitmap.createBitmap(bitmap, xTopLeft, yTopLeft, w, h);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                return result;
            } catch (OutOfMemoryError var9) {
                return null;
            }
        } else {
            Log.w("Util", "ignore intercept [" + widthOrg + ", " + heightOrg + ":" + w + ", " + h + "]");
            return bitmap;
        }
    }

    public static Bitmap getThumbBitmap(Context context, Uri uri, int sizeLimit, int minSize) throws IOException {
        String path;
        if (uri.getScheme().equals("file")) {
            path = uri.toString().substring(5);
        } else {
            if (!uri.getScheme().equals("content")) {
                return null;
            }

            Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(0);
            cursor.close();
        }

        ExifInterface exifInterface = new ExifInterface(path);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int orientation = exifInterface.getAttributeInt("Orientation", 0);
        int width = options.outWidth;
        int height = options.outHeight;
        int longSide = width > height ? width : height;
        int shortSide = width > height ? height : width;
        float scale = (float) longSide / (float) shortSide;
        int sampleW = 1;
        int sampleH = 1;
        int sampleSize = 1;
        if (scale > (float) sizeLimit / (float) minSize) {
            while (shortSide / 2 > minSize) {
                shortSide /= 2;
                sampleSize <<= 1;
            }

            options = new Options();
            options.inSampleSize = sampleSize;
        } else {
            while (width / 2 > sizeLimit) {
                width /= 2;
                sampleW <<= 1;
            }

            while (height / 2 > sizeLimit) {
                height /= 2;
                sampleH <<= 1;
            }

            options = new Options();
            sampleSize = Math.max(sampleW, sampleH);
            options.inSampleSize = sampleSize;
        }

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError var27) {
            var27.printStackTrace();
            options.inSampleSize <<= 1;
            bitmap = BitmapFactory.decodeFile(path, options);
        }

        Matrix matrix = new Matrix();
        if (bitmap == null) {
            return bitmap;
        } else {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
                int tmp = w;
                w = h;
                h = tmp;
            }

            switch (orientation) {
                case 2:
                    matrix.preScale(-1.0F, 1.0F);
                    break;
                case 3:
                    matrix.setRotate(180.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 4:
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 5:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 6:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 7:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 8:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
            }

            float sS = 0.0F;
            float xS = 0.0F;
            float yS = 0.0F;
            if (scale > (float) sizeLimit / (float) minSize) {
                shortSide = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getHeight() : bitmap.getWidth();
                sS = (float) minSize / (float) shortSide;
                matrix.postScale(sS, sS);
            } else {
                xS = (float) sizeLimit / (float) bitmap.getWidth();
                yS = (float) sizeLimit / (float) bitmap.getHeight();
                matrix.postScale(Math.min(xS, yS), Math.min(xS, yS));
            }

            int x = 0;
            int y = 0;

            Bitmap result;
            try {
                if (scale > (float) sizeLimit / (float) minSize) {
                    if (bitmap.getWidth() > bitmap.getHeight()) {
                        h = bitmap.getHeight();
                        w = h * sizeLimit / minSize;
                        x = (bitmap.getWidth() - w) / 2;
                        y = 0;
                    } else {
                        w = bitmap.getWidth();
                        h = w * sizeLimit / minSize;
                        x = 0;
                        y = (bitmap.getHeight() - h) / 2;
                    }
                } else {
                    w = bitmap.getWidth();
                    h = bitmap.getHeight();
                }

                result = Bitmap.createBitmap(bitmap, x, y, w, h, matrix, true);
            } catch (OutOfMemoryError var28) {
                var28.printStackTrace();
                Log.d("ResourceCompressHandler", "OOMHeight:" + bitmap.getHeight() + "Width:" + bitmap.getHeight() + "matrix:" + sS + " " + xS + " " + yS);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                return null;
            }

            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }

            return result;
        }
    }
}
