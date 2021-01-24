package com.juniper.mist_wayfinding_v2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.math.BigDecimal;

public class CreateLine extends View {

    Paint paint = new Paint();
    float x0,y1,x2,y3 =0;


    public CreateLine(Context context,float x0,float y1,float x2, float y3) {
        super(context);
        this.x0 = x0;
        this.y1 = y1;
        this.x2 = x2;
        this.y3 = y3;
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(60); // transparent
        this.paint = paint;
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(x0,y1,x2,y3,paint); // left,top,right,bottom
    }
}
