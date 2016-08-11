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
public class DrawDCButton extends View {

    private Bitmap bitmapShow, bitmapDefaut, bitmapLeft, bitmapRight, bitmapStop, bitmapGray;
    private int state;
    private Paint mPaint;
    private Paint textPaint;
    private Rect rectOr, rectDst;
//    private Rect rectText;
    private Context mContext;
    private final int withMatrice;
//    private String text;
//    private String rate = "停止";
//    private final String stop = "停止";
//    private final String left = "正转";
//    private final String right = "反转";

    public void setState(int state) {
        this.state = state;
        switch (state){
            case 0:
                bitmapShow = bitmapStop;
//                rate = stop;
                break;
            case 1:
                bitmapShow = bitmapRight;
//                rate = left;
                break;
            case 2:
                bitmapShow = bitmapLeft;
//                rate = right;
                break;
            case 3:
                bitmapShow = bitmapGray;
                break;
            default:
                break;
        }
    }

    public DrawDCButton(Context context) {
        this(context, null);
    }

    public DrawDCButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("DefaultLocale")
    public DrawDCButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        withMatrice = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18 * 1024 / withMatrice);
        mPaint.setStrokeWidth(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmapDefaut = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc_motor_button_default);
        bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc_motor_button_left);
        bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc_motor_button_right);
        bitmapStop = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc_motor_button_stop);
        bitmapGray = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc_motor_button_default_gray);

        bitmapShow = bitmapDefaut;

        int oH = bitmapShow.getHeight();
        int oW = bitmapShow.getWidth();
        rectOr = new Rect(0, 0, oW, oH);

        int dW = 400 * 1024/withMatrice;
        int dH = dW * oH/oW;
        rectDst = new Rect(0,0,dW, dH);

//        textPaint = new Paint();
//        textPaint.setColor(Color.BLACK);
//        textPaint.setAntiAlias(true);
//        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        textPaint.setTextSize(22);
//        rectText = new Rect(rectDst.left, rectDst.bottom + 10,
//                rectDst.right, (int) (2 * textPaint.getTextSize() + 1) + rectDst.bottom);


//        text = String.format("当前状态：%s，当前转速：%d r/s", rate, 0);
    }

//    @SuppressLint("DefaultLocale")
//    public void setText(int speed) {
////        text = String.format("当前状态：%s，当前转速：%d r/s", rate, speed);
//        postInvalidate();
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapShow, rectOr, rectDst, mPaint);
//        canvas.drawText(text, rectText.left + 5,
//                rectText.height() / 4 + rectText.top, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (y > rectDst.top && y < rectDst.bottom) {
            if (x > rectDst.left && x < rectDst.left + rectDst.width() / 3){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        setState(3);
                        break;
                    case MotionEvent.ACTION_UP:
                        setState(2);
                        if (onButtonClicked != null){
                            onButtonClicked.onclicked(2);
                        }
                        break;
                    default:
                        break;
                }
            } else if (x > rectDst.left + rectDst.width() / 3 && x < rectDst.left + rectDst.width() * 2 / 3){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        setState(3);
                        break;
                    case MotionEvent.ACTION_UP:
                        setState(0);
                        if (onButtonClicked != null){
                            onButtonClicked.onclicked(0);
                        }
                        break;
                    default:
                        break;
                }
            }else if (x > rectDst.left + rectDst.width() * 2 /3 && x < rectDst.right){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        setState(3);
                        break;
                    case MotionEvent.ACTION_UP:
                        setState(1);
                        if (onButtonClicked != null){
                            onButtonClicked.onclicked(1);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        invalidate();
        return true;

    }

    private OnButtonClicked onButtonClicked;

    public void setOnButtonClicked(OnButtonClicked onButtonClicked) {
        this.onButtonClicked = onButtonClicked;
    }

    public interface OnButtonClicked{
        void onclicked(int which);
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
}
