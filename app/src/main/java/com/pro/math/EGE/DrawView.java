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
    private final int TEXT = getResources().getColor(R.color.TextColor);
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
            pathScale.remove(i);
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
    private void touchMove(float x,float y) {
        final double RESPONSIVENESS = (Math.pow(x - touchX,2) + Math.pow(y - touchY,2));
        if (drawMode == MOVE && RESPONSIVENESS > MOVE_RESPONSIVENESS) {
            final float diffX = x - touchX,diffY = y - touchY;
            for (Path myPath : strokesPath) {
                myPath.offset(diffX,diffY);
            }
        } else if (drawMode == DRAW && RESPONSIVENESS > TOUCH_RESPONSIVENESS) {
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
        final int index = event.getActionIndex();
        final float x = event.getX(index), y = event.getY(index);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Console.L("ACTION_DOWN");
                if (index == 0 && !working) {
                    touchStart(x, y);
                    Console.L("ACTION_DOWN touchStart");
                }
                eventPosition.add(index,new float[] {x,y});
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                Console.L("ACTION_UP");
                if (index == 0) {
                    Console.L("ACTION_UP index");
                    touchUp();
                    if (eventPosition.size() > 1) {
                        touchStart(eventPosition.get(1)[0], eventPosition.get(1)[1]);
                        Console.L("ACTION_UP touchStart");
                    }
                }
                for (int i = index+1;i < eventPosition.size();i++) {
                    eventPosition.set(i-1,eventPosition.get(i));
                }
                eventPosition.remove(eventPosition.size()-1);
                break;
            case MotionEvent.ACTION_MOVE:
                if (index == 0)
                    touchMove(x,y);
                eventPosition.set(index,new float[] {x,y});
                break;
            /*case MotionEvent.ACTION_POINTER_DOWN:
                index = event.getActionIndex();
                x = event.getX(index); y = event.getY(index);
                if (index == 0)
                    touchStart(x,y);
                eventPosition.add(new float[] {x,y});
                pointers += 1;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                index = event.getActionIndex();
                pointers -= 1;
                break;
            case MotionEvent.ACTION_DOWN:
                Console.L("ACTION_DOWN "+index+" "+pointers);
                if (index == 0)
                    touchStart(x,y);
                eventPosition.add(new float[] {x,y});
                break;
            case MotionEvent.ACTION_UP:
                Console.L("ACTION_UP "+index+" "+pointers);
                if (index == 0) {
                    touchUp();
                    if (eventPosition.size() > 1)
                        touchStart(eventPosition.get(1)[0],eventPosition.get(1)[1]);
                }
                for (int i = index+1;i < eventPosition.size();i++) {
                    eventPosition.set(i-1,eventPosition.get(i));
                }
                eventPosition.remove(eventPosition.size()-1);
                break;
            case MotionEvent.ACTION_MOVE:
                index = event.getActionIndex();
                Console.L("ACTION_MOVE "+index+" "+pointers);
                x = event.getX(index); y = event.getY(index);
                if (index == 0)
                    touchMove(x,y);
                eventPosition.set(index,new float[] {x,y});
                break;*/
        }
        if (invalidate)
            invalidate();
        return true;
    }
    /*private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
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
    }*/
}

