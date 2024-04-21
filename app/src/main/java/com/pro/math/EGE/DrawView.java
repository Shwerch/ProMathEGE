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
    private static final double TOUCH_RESPONSIVENESS = Math.pow(4,2), MOVE_RESPONSIVENESS = Math.pow(2,2);
    private static final int DEFAULT_STROKE_WIDTH = 4;
    public static final byte DRAW = 0,MOVE = 1;
    private final int BACKGROUND = getResources().getColor(R.color.Background);
    private float touchX, touchY;
    private byte drawMode = DRAW;
    private Path myPath = null;
    private final Paint myPaint;
    private final Paint myBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private static final ArrayList<Float> strokesScale = new ArrayList<>();
    private static final ArrayList<Path> strokesPath = new ArrayList<>();
    private static final ArrayList<Integer> strokesWidth = new ArrayList<>();
    private int strokeWidth;
    private Bitmap myBitmap;
    private Canvas myCanvas;
    private float scaleFactor = 1;
    private final Matrix matrix = new Matrix();
    private int activeId = -1;
    private int firstZoomId, secondZoomId;
    private double zoomDistance = -1;
    private boolean touching = false;
    private boolean zooming = false;
    private boolean invalidate = false;
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
        int TEXT = getResources().getColor(R.color.TextColor);
        myPaint.setColor(TEXT);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeJoin(Paint.Join.ROUND);
        myPaint.setStrokeCap(Paint.Cap.ROUND);
        myPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        myPaint.setAlpha(0xff);
    }
    public void init(int h, int w) {
        myBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(myBitmap);
        strokeWidth = DEFAULT_STROKE_WIDTH;
    }
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }
    public void undo() {
        if (!strokesPath.isEmpty()) {
            final int i = strokesPath.size() - 1;
            strokesScale.remove(i);
            strokesPath.remove(i);
            strokesWidth.remove(i);
            invalidate();
        }
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        myCanvas.drawColor(BACKGROUND);
        for (int i = 0; i < strokesPath.size();i++) {
            myPaint.setStrokeWidth(strokesWidth.get(i));
            myCanvas.drawPath(strokesPath.get(i), myPaint);
        }
        canvas.drawBitmap(myBitmap, 0, 0, myBitmapPaint);
    }
    private void touchStart(float x,float y) {
        touching = true;
        if (drawMode == DRAW) {
            myPath = new Path();
            strokesScale.add(scaleFactor);
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
        if (drawMode == MOVE && RESPONSIVENESS > MOVE_RESPONSIVENESS) {
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
    private void zoomStart(int pointers,MotionEvent event) {
        int Id;
        zooming = true;
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
        zoomDistance = (Math.pow(event.getX(firstIndex) - event.getX(secondIndex),2) + Math.pow(event.getY(firstIndex) - event.getY(secondIndex),2));
    }
    private void zoomMove(MotionEvent event) {
        int firstIndex = event.findPointerIndex(firstZoomId);
        int secondIndex = event.findPointerIndex(secondZoomId);
        double newZoomDistance = (Math.pow(event.getX(firstIndex) - event.getX(secondIndex),2) + Math.pow(event.getY(firstIndex) - event.getY(secondIndex),2));
        scaleFactor = (float) (newZoomDistance / zoomDistance);
        scaleFactor = 1f - ((1f - scaleFactor) / 1000f);
        Console.L(scaleFactor+"");
        for (int i = 0;i < strokesPath.size();i++) {
            final float newScaleFactor = scaleFactor / strokesScale.get(i);
            matrix.postScale(newScaleFactor,newScaleFactor);
            strokesScale.add(i,newScaleFactor);
            strokesPath.get(i).transform(matrix);
        }
        invalidate = true;
    }
    void clearDrawing() {
        strokesPath.clear();
        strokesWidth.clear();
        invalidate();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate = false;
        final int pointers = event.getPointerCount();
        if (pointers > 2) {
            if (touching)
                touchUp();
            if (zooming) {
                zoomDistance = -1;
                zooming = false;
            }
        } else if (pointers == 2 && drawMode == MOVE) {
            if (touching)
                touchUp();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_POINTER_DOWN:
                    zoomStart(pointers,event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (zoomDistance == -1) {
                        zoomStart(pointers,event);
                    } else {
                        zoomMove(event);
                    }
                    break;
            }
        } else {
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
                        int minId = Integer.MAX_VALUE;
                        for (int i = 0;i < event.getPointerCount();i++) {
                            Id = event.getPointerId(i);
                            if (Id != activeId)
                                minId = Integer.min(minId,Id);
                        }
                        if (touching)
                            touchUp();
                        activeId = minId;
                        index = event.findPointerIndex(activeId);
                        touchStart(event.getX(index),event.getY(index));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (activeId != -1) {
                        index = event.findPointerIndex(activeId);
                        touchMove(event.getX(index),event.getY(index));
                        break;
                    }
            }
        }
        if (invalidate)
            invalidate();
        return true;
    }
}

