package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hqyj.dev.procedurem4.R;

/**
 * Created by jiyangkang on 2016/5/19 0019.
 */
public class DrawSteeperButton extends View {


    private Context mContext;
    private Paint mPaint;
    private final int withMatrice;

    private Bitmap bitmapDefault, bitmapPlusGray, bitmapMoinGray, bitmap;

    private Rect rectOr, rectDst;

    private int state;

    @SuppressLint("DefaultLocale")
    public void setState(int state) {
        if (state >= 0 && state <10) {
            this.state = state;
            if (onSpeedChanage != null){
                onSpeedChanage.speed(state);
            }
        }
    }


    public DrawSteeperButton(Context context) {
        this(context, null);
    }

    public DrawSteeperButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawSteeperButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        withMatrice = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18 * 1024 / withMatrice);
        mPaint.setStrokeWidth(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmapDefault = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.steeper_button_light);
        bitmapPlusGray = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.steeper_button_plus_gray);
        bitmapMoinGray = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.steeper_button_moin_gray);
        bitmap = bitmapDefault;

        int orW = bitmap.getWidth();
        int orH = bitmap.getHeight();
        rectOr = new Rect(0, 0, orW, orH);


        int dstW = 300 * 1024 / withMatrice;
        int dstH = dstW * orH / orW;

        rectDst = new Rect(0, 0, dstW, dstH);
        setState(0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, rectOr, rectDst, mPaint);
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
            width = getPaddingLeft() + getPaddingRight() + rectDst.width();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + getPaddingBottom() + rectDst.height();
        }
        setMeasuredDimension(width, height);
    }

    private OnSpeedChanage onSpeedChanage;

    public void setOnSpeedChanage(OnSpeedChanage onSpeedChanage) {
        this.onSpeedChanage = onSpeedChanage;
    }

    public interface OnSpeedChanage {
        void speed(int speed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (y > rectDst.top && y < rectDst.bottom){
            if (x > rectDst.left && x < rectDst.left + rectDst.width()/2){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bitmap = bitmapPlusGray;
                        break;
                    case MotionEvent.ACTION_UP:
                        bitmap = bitmapDefault;
                        setState(state + 1);
                        break;
                    default:
                        bitmap = bitmapDefault;
                        break;
                }
            } else if (x < rectDst.right && x > rectDst.left + rectDst.width()/2){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bitmap = bitmapMoinGray;
                        break;
                    case MotionEvent.ACTION_UP:
                        bitmap = bitmapDefault;
                        setState(state - 1);
                        break;
                    default:
                        bitmap = bitmapDefault;
                        break;
                }
            }
        }
        invalidate();
        return true;
    }
}
