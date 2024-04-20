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
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DrawView extends View {
    private static final double TOUCH_RESPONSIVENESS = Math.pow(4,2), MOVE_RESPONSIVENESS = Math.pow(2,2);
    private static final int DEFAULT_STROKE_WIDTH = 4;
    public static final byte DRAW = 0,MOVE = 1;
    private float touchX, touchY;
    private byte drawMode = DRAW;
    private boolean working = false;
    private Path myPath;
    private final Paint myPaint;
    private static final ArrayList<Float> pathScale = new ArrayList<>();
    private static final ArrayList<Path> strokesPath = new ArrayList<>();
    private static final ArrayList<Integer> strokesWidth = new ArrayList<>();
    private int strokeWidth;
    private Bitmap myBitmap;
    private Canvas myCanvas;
    //private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;
    private final Matrix matrix = new Matrix();
    private final Paint myBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private final ArrayList<float[]> eventPosition = new ArrayList<>();
    private boolean invalidate = false;
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
    public void init(int h, int w) {
        myBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(myBitmap);
        strokeWidth = DEFAULT_STROKE_WIDTH;
        //scaleDetector = new ScaleGestureDetector(this.getContext(), new ScaleListener());
    }
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }
    public void undo() {
        if (!strokesPath.isEmpty()) {
            final int i = strokesPath.size() - 1;
            pathScale.remove(i);
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
        //canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(myBitmap, 0, 0, myBitmapPaint);
    }
    private void touchStart(float[] position) {
        final float x = eventPosition[leaderPointer][0], y = eventPosition[leaderPointer][1];
        working = true;
        if (drawMode == DRAW) {
            myPath = new Path();
            pathScale.add(scaleFactor);
            strokesPath.add(myPath);
            strokesWidth.add(strokeWidth);
            myPath.reset();
            myPath.moveTo(x,y);
        }
        touchX = x;
        touchY = y;
        invalidate = true;
    }
    private void touchMove(float[] position) {
        final float x = eventPosition[leaderPointer][0], y = eventPosition[leaderPointer][1];
        final double TOLERANCE = (Math.pow(Math.abs(x - touchX),2) + Math.pow(Math.abs(y - touchY),2));
        if (drawMode == MOVE && TOLERANCE > MOVE_RESPONSIVENESS) {
            final float diffX = x - touchX,diffY = y - touchY;
            for (Path myPath : strokesPath) {
                myPath.offset(diffX,diffY);
            }
        } else if (drawMode == DRAW && TOLERANCE > TOUCH_RESPONSIVENESS) {
            myPath.quadTo(touchX, touchY, (x + touchX) / 2, (y + touchY) / 2);
        } else {return;}
        touchX = x;
        touchY = y;
        invalidate = true;
    }
    private void touchUp() {
        working = false;
        if (drawMode == DRAW) {
            myPath.lineTo(touchX, touchY);
            invalidate = true;
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
        invalidate = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //eventPosition.add(new float[] {event.getX(eventPosition.size()), event.getY(eventPosition.size())});
                touchStart(new float[] {event.getX(0),event.getY(0)});
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0;i < eventPosition.size();i++) {
                    if (!Arrays.equals(eventPosition.get(i),new float[]{event.getX(i),event.getY(i)})) {
                        if (i == 0) {touchUp();}
                        for (int i1 = i+1;i1 < eventPosition.size();i1++) {
                            eventPosition.add(i1-1,eventPosition.get(i1));
                        }
                        eventPosition.remove(eventPosition.size()-1);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                touchStart(new float[] {event.getX(0),event.getY(0)});
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(new float[] {event.getX(0),event.getY(0)});
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }
        for (int i = 0; i < pointers && i < 2 && leaderPointer != i; i++) {
            eventPosition[i] = new float[] {event.getX(i),event.getY(i)};
        }
        if (invalidate)
            invalidate();
        /*boolean invalidate = false;
        if (drawMode == MOVE) {
            scaleDetector.onTouchEvent(event);
            invalidate = true;
        }
        if (event.getPointerCount() == 1) {
            final float x = event.getX(0), y = event.getY(0);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate = true;
                    break;
                case MotionEvent.ACTION_UP:
                    touchUp();
                    invalidate = true;
                    break;
            }
        }
        if (invalidate)
            invalidate();*/
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor * (1f - (1f - detector.getScaleFactor())/15f), 10f));
            Console.L(scaleFactor+"");
            float scale;
            for (int i = 0;i < strokesPath.size();i++) {
                scale = scaleFactor / pathScale.get(i);
                matrix.setScale(scale,scale);
                pathScale.add(i,scale);
                strokesPath.get(i).transform(matrix);
            }
            return true;
        }
    }
}

