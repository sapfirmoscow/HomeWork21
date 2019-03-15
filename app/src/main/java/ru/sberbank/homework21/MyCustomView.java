package ru.sberbank.homework21;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class MyCustomView extends View {

    public static final int DEFAULT_DRAG_COLOR = Color.RED;
    private final float DEFAULT_SIZE = convertDpToPixel(100);
    private int userColor;
    private Paint mPaint = new Paint();
    private Paint textPaint = new Paint();


    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);

        //даже если падаем то подчищаем
        try {
            userColor = typedArray.getColor(R.styleable.MyCustomView_rectColor, Color.GREEN);
            mPaint.setColor(userColor);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measuredWidth = reconcileSize(widthMeasureSpec, widthMeasureSpec);
        final int measuredHeight = reconcileSize(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return (int) DEFAULT_SIZE;
                } else {
                    return specSize;
                }
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    private float convertDpToPixel(float dp) {
        return dp * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public int getColor() {
        return userColor;
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = getMeasuredWidth();
        int canvasHeight = getMeasuredHeight();
        @SuppressLint("DrawAllocation") Rect rect = new Rect(0, 0, canvasWidth, canvasHeight);
        canvas.drawRect(rect, mPaint);
        drawRectText("x is:" + (int) getX() + " y is " + (int) getY(), canvas, rect);

    }

    private void drawRectText(String text, Canvas canvas, Rect r) {
        textPaint.setTextSize(50);//косяк
        textPaint.setTextAlign(Paint.Align.CENTER);
        int width = r.width();

        int numOfChars = textPaint.breakText(text, true, width, null);
        int start = (text.length() - numOfChars) / 2;
        canvas.drawText(text, start, start + numOfChars, r.exactCenterX(), r.exactCenterY(), textPaint);
    }
}


