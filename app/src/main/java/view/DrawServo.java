package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiyangkang on 2016/5/22 0022.
 */
public class DrawServo extends View {

    private final int screenHeight;
    private Paint mPaint;
    private Context mContext;
    private OnDegreeChanged onDegreeChanged;

    private float degree = 0;

    private Rect dstRect;
    private Rect rectPoint;

    public void setDegree(float degree) {
        this.degree = degree;
        if (onDegreeChanged != null){
            onDegreeChanged.onDegreeChanged((int) degree);
        }
    }

    public DrawServo(Context context) {
        this(context, null);
    }

    public DrawServo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawServo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;

        mPaint = new Paint();
        mPaint.setTextSize(20 * screenHeight / 600);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int h = 110 * screenHeight / 600 + (int) mPaint.getTextSize();
        int w = 2 * h;

        dstRect = new Rect(0, 0, w, h);

        rectPoint = new Rect(0, 0, (int) mPaint.getTextSize(), (int)mPaint.getTextSize());


    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(20 * screenHeight / 600);


//        canvas.clipRect(dstRect.left + dstRect.width()/2, dstRect.top, dstRect.right, dstRect.bottom);
        canvas.save();
        canvas.rotate(degree, dstRect.left + dstRect.width() / 2,
                dstRect.bottom - mPaint.getTextSize());
        canvas.drawLine(dstRect.left + dstRect.width() / 2 - 110 * screenHeight / 600,
                dstRect.bottom - mPaint.getTextSize(), dstRect.left + dstRect.width() / 2,
                dstRect.bottom - mPaint.getTextSize(), mPaint);
        canvas.restore();

        mPaint.setColor(Color.BLACK);

        canvas.drawLine(dstRect.left + mPaint.getTextSize(), dstRect.bottom - mPaint.getTextSize(),
                dstRect.right - mPaint.getTextSize(), dstRect.bottom - mPaint.getTextSize(), mPaint);
        canvas.drawText(String.format("%d", (int) degree), dstRect.left + dstRect.width() / 2
                - mPaint.getTextSize(), dstRect.bottom, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        rectPoint.offsetTo(dstRect.left, (int)((dstRect.height() -
                rectPoint.height()) * degree / 180 + dstRect.top) );

        canvas.drawRect(rectPoint,mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + dstRect.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + dstRect.height() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (x > rectPoint.left && x < rectPoint.right && y > rectPoint.top &&
                        y < rectPoint.bottom){

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (x > rectPoint.left && x < rectPoint.right &&
                        y > dstRect.top + rectPoint.height() / 2 &&
                        y < dstRect.bottom - rectPoint.height()/2){
                    rectPoint.offsetTo(rectPoint.left, (int)(y - rectPoint.height()/2));
                    degree = (y - rectPoint.height()/2 - dstRect.top ) * 180/ (dstRect.height() -
                            rectPoint.height());
                    if (onDegreeChanged != null){
                        onDegreeChanged.onDegreeChanged((int)degree);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        invalidate();
        return true;
    }


    public void setOnDegreeChanged(OnDegreeChanged onDegreeChanged) {
        this.onDegreeChanged = onDegreeChanged;
    }

    public interface OnDegreeChanged{
        void onDegreeChanged(int degree);
    }
}

