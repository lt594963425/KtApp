package com.example.ktapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.ktapp.R;
import com.example.ktapp.data.ProvinceItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui
 * @ClassName: SvgView
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/17 11:17
 * @UpdateUser: LiuTao
 */
public class SvgView extends View {
    private Context mContext;
    private Paint mPaint;
    private List<ProvinceItem> itemList = new ArrayList<>();

    private ProvinceItem select;
    private RectF totalRect;
    private float scale = 1.0f;

    public SvgView(Context context) {
        super(context);
        init();
    }

    public SvgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SvgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private int[] colorArray = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA};


    private void init() {
        mContext = getContext();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        thread.start();

    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            //Dom 解析 SVG文件
            //Dom 解析 SVG文件

            InputStream inputStream = mContext.getResources().openRawResource(R.raw.chinahigh);


            DocumentBuilderFactory facotory = DocumentBuilderFactory.newInstance();

            try {
                DocumentBuilder builder = facotory.newDocumentBuilder();

                Document doc = builder.parse(inputStream);

                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");

                List<ProvinceItem> list = new ArrayList<>();

                float left = -1;
                float top = -1;
                float right = -1;
                float bottom = -1;


                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
//                    element.getAttribute("fillColor");
                    @SuppressLint("RestrictedApi")
                    Path path = PathParser.createPathFromPathData(pathData);

                    //将Path转化成矩形
                    RectF rectF = new RectF();
                    path.computeBounds(rectF, true);

                    left = left == -1 ? rectF.left : Math.min(left, rectF.left);
                    top = top == -1 ? rectF.top : Math.min(top, rectF.top);
                    right = right == -1 ? rectF.right : Math.max(right, rectF.right);
                    bottom = bottom == -1 ? rectF.bottom : Math.max(bottom, rectF.bottom);

                    ProvinceItem item = new ProvinceItem(path);
//                    item.setColor(getResources().getColor(colorArray[ii]));//随机颜色
                    item.setColor(colorArray[i % 4]);//随机颜色

                    list.add(item);
                }

                //float left, float top, float right, float bottom
                totalRect = new RectF(left, top, right, bottom);

                itemList = list;

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
                        invalidate();
                    }
                });

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //当前控件高宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (totalRect != null) {
            float mapWidth = totalRect.width();
            scale = width / mapWidth;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX() / scale, event.getY() / scale);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList == null) return;
        canvas.save();
        canvas.scale(scale, scale);
        for (ProvinceItem provinceItem : itemList) {
            if (select == provinceItem) {
                provinceItem.drawItem(canvas, mPaint, true);
            } else {
                provinceItem.drawItem(canvas, mPaint, false);
            }
        }
        canvas.restore();
    }

    private void handleTouch(float x, float y) {
        if (itemList == null) return;
        ProvinceItem selectItem = null;
        for (ProvinceItem proviceItem : itemList) {
            if (proviceItem.isTouch(x, y)) {
                selectItem = proviceItem;
            }
        }

        if (selectItem != null) {
            select = selectItem;
            postInvalidate();
        }
    }

}
