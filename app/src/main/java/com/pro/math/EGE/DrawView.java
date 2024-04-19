package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/*class Stroke {
    public int strokeWidth;
    public Path path;
    public Stroke(int strokeWidth, Path path) {
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}*/
public class DrawView extends View {
    private static final double TOUCH_TOLERANCE = 16; // 4
    private static final double MOVE_TOLERANCE = 1; // 1
    private static final int DEFAULT_STROKE_WIDTH = 4;
    private float touchX, touchY;
    private boolean drawMode = true;
    private boolean drawing = false;
    private Path path;
    private final Paint paint;
    private static final ArrayList<Path> strokesPath = new ArrayList<>();
    private static final ArrayList<Integer> strokesWidth = new ArrayList<>();
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    public void ChangeDrawMode() {
        if (!drawing)
            drawMode = !drawMode;
    }
    public DrawView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.TextColor));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        paint.setAlpha(0xff);
    }
    public void init(int height, int width) {
        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
        mCanvas = new Canvas(mBitmap);
        strokeWidth = DEFAULT_STROKE_WIDTH;
    }
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }
    public void undo() {
        if (!strokesPath.isEmpty()) {
            final int i = strokesPath.size() - 1;
            strokesPath.remove(i);
            strokesWidth.remove(i);
            invalidate();
        }
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        //canvas.save();
        mCanvas.drawColor(getResources().getColor(R.color.Background));
        for (int i = 0; i < strokesPath.size();i++) {
            paint.setStrokeWidth(strokesWidth.get(i));
            mCanvas.drawPath(strokesPath.get(i), paint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        //canvas.restore();
    }
    private void touchStart(float x, float y) {
        drawing = true;
        if (drawMode) {
            path = new Path();
            strokesPath.add(path);
            strokesWidth.add(strokeWidth);
            path.reset();
            path.moveTo(x,y);
        }
        touchX = x;
        touchY = y;
    }
    private void touchMove(float x, float y) {
        final double TOLERANCE = (Math.pow(Math.abs(x - touchX),2) + Math.pow(Math.abs(y - touchY),2));
        if (!drawMode && TOLERANCE > MOVE_TOLERANCE) {
            for (Path path : strokesPath) {
                path.offset(x - touchX,y - touchY);
            }
        } else if (TOLERANCE > TOUCH_TOLERANCE) {
            path.quadTo(touchX, touchY, (x + touchX) / 2, (y + touchY) / 2);
        } else {return;}
        touchX = x;
        touchY = y;
    }
    private void touchUp() {
        drawing = false;
        if (drawMode) {
            path.lineTo(touchX, touchY);
        }
    }
    void clearDrawing() {
        strokesPath.clear();
        strokesWidth.clear();
        invalidate();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX(), y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
                return true;
        }
        invalidate();
        return true;
    }
}

