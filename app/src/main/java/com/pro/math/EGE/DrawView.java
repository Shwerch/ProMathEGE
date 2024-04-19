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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
public class DrawView extends View {
    private static final double TOUCH_RESPONSIVENESS = Math.pow(4,2), MOVE_RESPONSIVENESS = Math.pow(2,2);
    private static final int DEFAULT_STROKE_WIDTH = 4;
    public static final byte DRAW = 0,MOVE = 1,ZOOM = 2;
    private float touchX, touchY;
    private byte drawMode = DRAW;
    private boolean working = false;
    private Path myPath;
    private final Paint myPaint;
    private static final ArrayList<Path> strokesPath = new ArrayList<>();
    private static final ArrayList<Integer> strokesWidth = new ArrayList<>();
    private int strokeWidth;
    private Bitmap myBitmap;
    private Canvas myCanvas;
    private final Paint myBitmapPaint = new Paint(Paint.DITHER_FLAG);
    public boolean DrawMode(byte mode) {
        if (mode == drawMode || working) return false;
        drawMode = mode;
        return true;
    }
    public DrawView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        myPaint = new Paint();
        myPaint.setAntiAlias(true);
        myPaint.setDither(true);
        myPaint.setColor(getResources().getColor(R.color.TextColor));
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        myPaint.setAlpha(0xff);
    }
    public void init(int height, int width) {
        myBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(myBitmap);
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
        myCanvas.drawColor(getResources().getColor(R.color.Background));
        for (int i = 0; i < strokesPath.size();i++) {
            myPaint.setStrokeWidth(strokesWidth.get(i));
            myCanvas.drawPath(strokesPath.get(i), myPaint);
        }
        canvas.drawBitmap(myBitmap, 0, 0, myBitmapPaint);
    }
    private void touchStart(float x, float y) {
        working = true;
        if (drawMode == DRAW) {
            myPath = new Path();
            strokesPath.add(myPath);
            strokesWidth.add(strokeWidth);
            myPath.reset();
            myPath.moveTo(x,y);
        } else if (drawMode != MOVE) {
            return;
        }
        touchX = x;
        touchY = y;
    }
    private void touchMove(float x, float y) {
        final double TOLERANCE = (Math.pow(Math.abs(x - touchX),2) + Math.pow(Math.abs(y - touchY),2));
        if (drawMode == MOVE && TOLERANCE > MOVE_RESPONSIVENESS) {
            for (Path myPath : strokesPath) {
                myPath.offset(x - touchX,y - touchY);
            }
        } else if (drawMode == DRAW && TOLERANCE > TOUCH_RESPONSIVENESS) {
            myPath.quadTo(touchX, touchY, (x + touchX) / 2, (y + touchY) / 2);
        } else {return;}
        touchX = x;
        touchY = y;
    }
    private void touchUp() {
        working = false;
        if (drawMode == DRAW) {
            myPath.lineTo(touchX, touchY);
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
        if (drawMode == ZOOM && event.getPointerCount() > 1) {

        } else {
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
        }
        return true;
    }
}

