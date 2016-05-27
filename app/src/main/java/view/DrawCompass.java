package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hqyj.dev.procedurem4.R;

/**
 * Created by jiyangkang on 2016/5/18 0018.
 */
public class DrawCompass extends View {

    private final int withMatrice;
    private Context mContext;
    private Paint mPaint;
    private Rect rectDst, orNode, orPlat;
    private float degree;

    private Bitmap bitmapCompassNoods, bitmapCompassPlat;

    private Matrix matrix;

    public void setDegree(float degree) {
        this.degree = -90 - degree;
//        matrix.reset();
//        matrix.postRotate(this.degree, bitmapCompassPlat.getWidth()/2,
//                bitmapCompassPlat.getHeight()/2);

    }

    public DrawCompass(Context context) {
        this(context, null);
    }

    public DrawCompass(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCompass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        withMatrice = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mPaint.setTextSize(18 * 1024 / withMatrice);
        mPaint.setStrokeWidth(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);


        bitmapCompassNoods = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.compass_noode);
        bitmapCompassPlat = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.compass_plat);

        int nodeWith = bitmapCompassNoods.getWidth();
        int nodeHeight = bitmapCompassNoods.getHeight();
        orNode = new Rect(0, 0, nodeWith, nodeHeight);
        orPlat = new Rect(0, 0, bitmapCompassPlat.getWidth(), bitmapCompassPlat.getHeight());

        int w, h;
        w = h = 100 * withMatrice / 1024;
        rectDst = new Rect(0, 0, w, h);
        matrix = new Matrix();
        setDegree(30);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bitmapCompassPlat,matrix, mPaint);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.save();
        canvas.rotate(degree, rectDst.left + rectDst.width() / 2,
                rectDst.top + rectDst.height() / 2);
        canvas.drawBitmap(bitmapCompassPlat, orPlat, rectDst, mPaint);
        canvas.restore();

        canvas.drawBitmap(bitmapCompassNoods, orNode, rectDst, mPaint);

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
