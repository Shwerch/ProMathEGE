package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
class Stroke {
    public int color;
    public int strokeWidth;
    public Path path;
    public Stroke(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
public class DrawView extends View {
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private final Paint mPaint;
    private final ArrayList<Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    public DrawView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(R.color.TextColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(4);
        mPaint.setAlpha(0xff);
    }
    public void init(int height, int width) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = Color.GREEN;

        strokeWidth = 20;
    }
    public void setColor(int color) {
        currentColor = color;
    }
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }
    public void undo() {
        if (!paths.isEmpty()) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }
    public Bitmap save() {
        return mBitmap;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(getResources().getColor(R.color.Background));
        for (Stroke fp : paths) {
            //mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }
    private void touchStart(float x, float y) {
        mPath = new Path();
        Stroke fp = new Stroke(currentColor, strokeWidth, mPath);
        paths.add(fp);
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }
    void clearDrawing() {
        paths.clear();
        invalidate();
    }
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}

