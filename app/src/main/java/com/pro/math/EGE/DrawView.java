package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawView extends View {
    private static final double TOUCH_RESPONSIVENESS = Math.pow(4,2);
    private static final float DEFAULT_STROKE_WIDTH = 4;
    public static final byte DRAW = 0, MOVE = 1;
    private final int BACKGROUND = getResources().getColor(R.color.Background);
    private int height, width;
    private float touchX, touchY;
    private byte drawMode = DRAW;
    private Path myPath = null;
    private final Paint myPaint;
    private static ArrayList<Path> strokesPath = new ArrayList<>();
    private static ArrayList<Float> strokesWidth = new ArrayList<>();
    private static ArrayList<Path> strokesPathBackup = null;
    private static ArrayList<Float> strokesWidthBackup = null;
    private float strokeWidth;
    private final Matrix matrix = new Matrix();
    private int activeId = -1;
    private int firstZoomId, secondZoomId;
    private double zoomDistance = -1;
    private boolean touching = false, invalidate = false;
    public boolean DrawMode(byte mode) {
        if (mode == drawMode || touching) return false;
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
        this.height = height/2;
        this.width = width/2;
        strokeWidth = DEFAULT_STROKE_WIDTH;
    }
    public void setStrokeWidth(float width) {
        strokeWidth = width;
    }
    public void undo() {
        if (!strokesPath.isEmpty()) {
            final int i = strokesPath.size() - 1;
            strokesPath.remove(i);
            strokesWidth.remove(i);
        } else if (strokesPathBackup != null) {
            strokesPath = strokesPathBackup;
            strokesWidth = strokesWidthBackup;
            strokesPathBackup = null;
            strokesWidthBackup = null;
        } else return;
        invalidate();
    }
    void clearDrawing() {
        strokesPathBackup = strokesPath;
        strokesWidthBackup = strokesWidth;
        strokesPath = new ArrayList<>();
        strokesWidth = new ArrayList<>();
        invalidate();
    }
    private final double e6 = Math.pow(10,6);
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        long time = System.nanoTime();
        canvas.drawColor(BACKGROUND);
        for (int i = 0; i < strokesPath.size();i++) {
            myPaint.setStrokeWidth(strokesWidth.get(i));
            canvas.drawPath(strokesPath.get(i),myPaint);
        }
        time = System.nanoTime() - time;
        Console.L(time/e6+"");
    }
    private void touchStart(float x,float y) {
        touching = true;
        if (drawMode == DRAW) {
            myPath = new Path();
            strokesPath.add(myPath);
            strokesWidth.add(strokeWidth);
            myPath.reset();
            myPath.moveTo(x,y);
        }
        touchX = x;
        touchY = y;
        invalidate = true;
    }
    private void touchMove(float x,float y) {
        final float diffX = x - touchX, diffY = y - touchY;
        final double RESPONSIVENESS = (Math.pow(diffX,2) + Math.pow(diffY,2));
        if (drawMode == MOVE) {
            for (Path myPath : strokesPath) {
                myPath.offset(diffX,diffY);
            }
        } else if (drawMode == DRAW && RESPONSIVENESS > TOUCH_RESPONSIVENESS) {
            myPath.quadTo(touchX, touchY, (x + touchX) / 2, (y + touchY) / 2);
        } else {
            return;
        }
        touchX = x;
        touchY = y;
        invalidate = true;
    }
    private void touchUp() {
        activeId = -1;
        touching = false;
        if (drawMode == DRAW) {
            myPath.lineTo(touchX, touchY);
            invalidate = true;
        }
    }
    private void touchFind(MotionEvent event) {
        int Id;
        int minId = Integer.MAX_VALUE;
        for (int i = 0;i < event.getPointerCount();i++) {
            Id = event.getPointerId(i);
            if (Id != activeId)
                minId = Integer.min(minId,Id);
        }
        if (touching)
            touchUp();
        activeId = minId;
        final int index = event.findPointerIndex(activeId);
        if (index != -1)
            touchStart(event.getX(index),event.getY(index));
    }
    private void zoomStart(int pointers,MotionEvent event) {
        int Id;
        firstZoomId = Integer.MAX_VALUE;
        secondZoomId = Integer.MAX_VALUE;
        for (int i = 0;i < pointers;i++) {
            firstZoomId = Integer.min(firstZoomId,event.getPointerId(i));
        }
        for (int i = 0;i < pointers;i++) {
            Id = event.getPointerId(i);
            if (Id != firstZoomId)
                secondZoomId = Integer.min(secondZoomId,event.getPointerId(i));
        }
        int firstIndex = event.findPointerIndex(firstZoomId);
        int secondIndex = event.findPointerIndex(secondZoomId);
        if (firstIndex != -1 && secondIndex != -1)
            zoomDistance = (Math.pow(event.getX(firstIndex) - event.getX(secondIndex),2) + Math.pow(event.getY(firstIndex) - event.getY(secondIndex),2));
    }
    private void zoomMove(MotionEvent event) {
        int firstIndex = event.findPointerIndex(firstZoomId);
        int secondIndex = event.findPointerIndex(secondZoomId);
        if (firstIndex == -1 || secondIndex == -1)
            return;
        double newZoomDistance = (Math.pow(event.getX(firstIndex) - event.getX(secondIndex),2) + Math.pow(event.getY(firstIndex) - event.getY(secondIndex),2));
        final float scaleFactor = (float) (1 - ((1 - newZoomDistance / zoomDistance) / 75));
        final float offsetFactor = (1 - scaleFactor) * scaleFactor;
        matrix.reset();
        matrix.postScale(scaleFactor,scaleFactor);
        for (int i = 0;i < strokesPath.size();i++) {
            strokesWidth.set(i,strokesWidth.get(i) * scaleFactor);
            strokesPath.get(i).transform(matrix);
            strokesPath.get(i).offset(offsetFactor * width,offsetFactor * height);
        }
        invalidate = true;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate = false;
        final int pointers = event.getPointerCount();
        if (pointers == 2 && drawMode == MOVE) {
            if (touching)
                touchUp();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_POINTER_DOWN:
                    zoomStart(pointers, event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (zoomDistance != -1) {
                        zoomMove(event);
                    } else
                        zoomStart(pointers, event);
                    break;
            }
        }
        int Id;
        final int index;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                activeId = 0;
                touchStart(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                index = event.getActionIndex();
                Id = event.getPointerId(index);
                if (Id < activeId) {
                    if (touching)
                        touchUp();
                    activeId = Id;
                    touchStart(event.getX(index),event.getY(index));
                }
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(event.getActionIndex()) == activeId) {
                    touchFind(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                boolean findNeeded = true;
                if (activeId != -1) {
                    index = event.findPointerIndex(activeId);
                    if (index != -1) {
                        touchMove(event.getX(index), event.getY(index));
                        findNeeded = false;
                    }
                }
                if (findNeeded)
                    touchFind(event);
                break;
        }
        if (invalidate)
            invalidate();
        return true;
    }
}