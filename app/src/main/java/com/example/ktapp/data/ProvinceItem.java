package com.example.ktapp.data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

public class ProvinceItem {

    private Path path;
    private int color;

    public ProvinceItem(Path path) {
        this.path = path;
    }

    public ProvinceItem(Path path, int color) {
        this.path = path;
        this.color = color;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        Log.e("ProvinceItem","setColor -->"+color);
        this.color = color;
    }
    public void drawItem(Canvas canvas, Paint paint, boolean isSelect) {

        if(isSelect){
            paint.clearShadowLayer();

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawPath(path,paint);

            paint.clearShadowLayer();
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xFFD0E8F4);
            canvas.drawPath(path,paint);

        }else{

            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setShadowLayer(8,0,0,0xffffff);
            canvas.drawPath(path,paint);

            paint.clearShadowLayer();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            paint.setStrokeWidth(2);
            canvas.drawPath(path,paint);

        }
    }


    public boolean isTouch(float x, float y) {

        Region region = new Region();
        //将Path转化为RectF矩形
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        return region.contains((int) x, (int) y);
    }
}
