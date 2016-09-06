package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by jiyangkang on 2016/5/9 0009.
 */
public class DrawListViewItem extends View{

    private Rect orBitmapRect, dstBitmapRect, orBgRect, dstBgRect;
    private Rect dstRect;
    private float screenHeight;
    private String name;
    private Paint mPaint;
    private boolean isChoose = false;

    private Bitmap mBitmap, light, gray, bg;

    private Context mContext;
    public static Map<String, int[]> mapList = new HashMap<>();

    public void setIsChoose(boolean isChoose){
        this.isChoose = isChoose;
    }

    private void setMapList() {
        mapList.put(mContext.getResources().getString(R.string.alcohol),
                new int[]{R.drawable.alcohol_gray, R.drawable.alcohol_light});

        mapList.put(mContext.getResources().getString(R.string.brake),
                new int[]{R.drawable.brake_gray, R.drawable.brake_light});

        mapList.put(mContext.getResources().getString(R.string.buzzer),
                new int[]{R.drawable.buzzer_gray, R.drawable.buzzer_light});

//        mapList.put(mContext.getResources().getString(R.string.compass),
//                new int[]{R.drawable.compass_gray, R.drawable.compass_light});

        mapList.put(mContext.getResources().getString(R.string.dc_motor),
                new int[]{R.drawable.dc_motor_gray, R.drawable.dc_motor_light});

        mapList.put(mContext.getResources().getString(R.string.gas),
                new int[]{R.drawable.gas_gray, R.drawable.gas_light});

        mapList.put(mContext.getResources().getString(R.string.light),
                new int[]{R.drawable.light_gray, R.drawable.light_light});

        mapList.put(mContext.getResources().getString(R.string.marix),
                new int[]{R.drawable.matrix_gray, R.drawable.matrix_light});

        mapList.put(mContext.getResources().getString(R.string.relay),
                new int[]{R.drawable.relay_gray, R.drawable.relay_light});

        mapList.put(mContext.getResources().getString(R.string.rfid),
                new int[]{R.drawable.rfid_gray, R.drawable.rfid_light});

        mapList.put(mContext.getResources().getString(R.string.servo),
                new int[]{R.drawable.servo_gray, R.drawable.servo_light});

        mapList.put(mContext.getResources().getString(R.string.steeper),
                new int[]{R.drawable.steeper_gray, R.drawable.steeper_light});

        mapList.put(mContext.getResources().getString(R.string.flame),
                new int[]{R.drawable.flame_gray, R.drawable.flame_light});

        mapList.put(mContext.getResources().getString(R.string.tube),
                new int[]{R.drawable.tube_gray, R.drawable.tube_light});

        mapList.put(mContext.getResources().getString(R.string.temp),new int[]{R.drawable.temp_gray,R.drawable.temp_light});
        mapList.put("it is for init", new int[]{R.drawable.no_name, R.drawable.no_name});
    }

    public void setName(String name) {
        this.name = name;
        light = BitmapFactory.decodeResource(mContext.getResources(),
                mapList.get(name)[1]);
        gray = BitmapFactory.decodeResource(mContext.getResources(),
                mapList.get(name)[0]);
    }

    public DrawListViewItem(Context context) {
        this(context, null);
    }

    public DrawListViewItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawListViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        setMapList();
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;

        mPaint = new Paint();
        mPaint.setTextSize(24 * screenHeight / 600);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        if (name == null){
            name = "it is for init";
        }

        light = BitmapFactory.decodeResource(context.getResources(),
                mapList.get(name)[1]);
        gray = BitmapFactory.decodeResource(context.getResources(),
                mapList.get(name)[0]);

        bg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.item_bg);

        mBitmap = gray;
        int wBitmap = mBitmap.getWidth();
        int hBitmap = mBitmap.getHeight();

        orBitmapRect = new Rect(0, 0, wBitmap, hBitmap);

        int dstHBitmap = hBitmap * 112 / 600;
        int dstWBitmap = dstHBitmap * wBitmap / hBitmap;
        dstBitmapRect = new Rect(0, 0, dstWBitmap, dstHBitmap);

        dstRect = new Rect(0, 0, dstHBitmap * 4, dstHBitmap);

        orBgRect = new Rect(0, 0, bg.getWidth(), bg.getHeight());
        dstBgRect = new Rect(dstBitmapRect.right, dstBitmapRect.top, dstRect.right, dstRect.bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isChoose){
            mBitmap = light;
            canvas.drawBitmap(bg, orBgRect, dstBgRect, mPaint);
        } else {
            mBitmap = gray;
        }
        canvas.drawBitmap(mBitmap, orBitmapRect, dstBitmapRect, mPaint);
        canvas.drawText(name, dstBitmapRect.right + mPaint.getTextSize()/2,
                dstBitmapRect.height()/2 + mPaint.getTextSize() /2, mPaint );

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
}
