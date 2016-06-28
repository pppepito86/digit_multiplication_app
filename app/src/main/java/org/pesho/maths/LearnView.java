package org.pesho.maths;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class LearnView extends View {

    private Paint mPaint;
    int x;
    int y;
    int sum;

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }





    public LearnView(Context context) {
        super(context);
        init();
    }

    public LearnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LearnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        //canvas.drawRect(200, 100, 400, 400, mPaint);
        mPaint.setTextSize(200);
        canvas.drawText(x + "." + y, 700, 400, mPaint);
        canvas.drawLine(0, 440, 1500, 440, mPaint);
        mPaint.setTextSize(100);
        sum=(x%10)*(y%10);
        canvas.drawText(x%10+"."+y%10+"="+sum,40,550,mPaint);

        canvas.drawText(sum%10+"",1300,550,mPaint);
        canvas.drawText(sum/10+"",720,200,mPaint);

    }



}
