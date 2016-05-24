package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hqyj.dev.procedurem4.R;

/**
 * Created by jiyangkang on 2016/5/16 0016.
 */
public class DrawSwitcher extends View {

    private final int withMatrice;
    private Bitmap bitmap;
    private Rect rectOr;
    private Rect rectDst;

    private Context mContext;
    private Paint mPaint;


    public void setBitmap(int id) {

        this.bitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
    }

    public DrawSwitcher(Context context) {
        this(context, null);
    }

    public DrawSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        withMatrice = context.getResources().getDisplayMetrics().widthPixels;

        mPaint = new Paint();
        mPaint.setTextSize(18*1024/withMatrice);
        mPaint.setStrokeWidth(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.alcohol_bo);

        int h = 100 * withMatrice / 600;
        int w = h * bitmap.getWidth()/bitmap.getHeight();

        rectOr = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectDst = new Rect(0, 0, w, h);
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
}
