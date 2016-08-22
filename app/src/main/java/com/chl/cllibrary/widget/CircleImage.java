package com.chl.cllibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chl.cllibrary.R;

/**
 * Created by jh on 2016/8/21.
 */
public class CircleImage extends ImageView {

    private Paint mPaint;
    private Drawable mDrawable;

    public CircleImage(Context context) {
        super(context);
        init(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImage);
        mDrawable = typedArray.getDrawable(0);
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable != null) {
            Bitmap bitmap = drawableToBitmap3(mDrawable);
            Bitmap rawBitmap = dealRawBitmap(bitmap);
            Bitmap scaleBitmap = scaleBitmap(rawBitmap);
            Bitmap roundCorner = toRoundCorner(scaleBitmap, 0);
            mPaint.reset();
            Rect rect = new Rect(0,0,roundCorner.getWidth(),roundCorner.getHeight());
            canvas.drawBitmap(roundCorner,rect,rect,mPaint);
//            canvas.drawBitmap(0,0,roundCorner.getWidth(),roundCorner.getHeight(),15,15,mPaint);

        }
    }

    private Bitmap drawableToBitmap1(Drawable drawable) {
        BitmapDrawable drawable1 = (BitmapDrawable) drawable;
        Bitmap bitmap = drawable1.getBitmap();
        return bitmap;
    }
    //有点问题
    private Bitmap drawableToBitmap2(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private Bitmap drawableToBitmap3(Drawable drawable) {
        Bitmap bitmap;
        measure(0, 0);
        int w = getWidth();
        int h = getHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private Bitmap dealRawBitmap(Bitmap bitmap){
        measure(0,0);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int minWidth = width > height ? height : width;
        int leftTopX = (width - minWidth)/2;
        int leftTopY = (height - minWidth)/2;
        Bitmap dstBitmap = Bitmap.createBitmap(bitmap, leftTopX, leftTopY, minWidth, minWidth,null,false);
        return  dstBitmap;
    }

    private Bitmap scaleBitmap(Bitmap bitmap){
        int width = getWidth();
        int height = getHeight();
        float widthScale = (float)width/bitmap.getWidth();
//        float heightScale = (float)height/bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale,widthScale);
        Bitmap dstBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return dstBitmap;
    }
    private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF  rectF = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
        mPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        mPaint.setColor(color);
        int x = bitmap.getWidth();
//        int radius = (int) Math.sqrt(Math.pow(x/2,2)+Math.pow(x/2,2));
        canvas.drawCircle(x / 2, x / 2,x/2, mPaint);
//        canvas.drawRoundRect(rectF,45,45,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, mPaint);
        mPaint.setXfermode(null);
        return output;
    }
}
