package com.hexin.apicloud.ble.util;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyufei on 2017/5/12.
 * 这是一个类似于模拟LED灯效果的自定义View
 * 可以实现将文本和图片转换成LED效果
 */

public class WaterMarkUtil{
    /**
     * Led light show shape
     * 1.circle shape
     * 2.square shape
     * 3.custom shape
     */
    public static final String LED_TYPE_CIRCLE = "1";
    public static final String LED_TYPE_SQUARE = "2";

    /**
     * Content type,text or image
     */
    public static final String CONTENT_TYPE_TEXT = "1";
    public static final String CONTENT_TYPE_IMAGE = "2";

    private static Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    /**
     * Led light space
     */
    private static int ledSpace;

    /**
     * Led light radius
     */
    private static int ledRadius;
    /**
     * Led color, if content is image,this param not work
     */
    private static int ledColor;

    /**
     * Content text size
     */
    private int ledTextSize;

    /**
     * Content type, text or image
     */
    private static String ledType;

    /**
     * Custom led light drawable
     */
    private Drawable customLedLightDrawable;

    /**
     * Store the points of all px
     */
    private static List<Point> circlePoint = new ArrayList<>();

    /**
     * Content of text
     */
    private CharSequence ledText;



    /**
     * The content width and height
     */
    private int mDrawableWidth;
    private int mDrawableHeight;

    /**
     * Set text content
     * @param text content
     */
    public void setText(CharSequence text) {
        this.ledText = text;
        measureTextBound(text.toString());
    }


    public static Bitmap generateDrawable(Bitmap bitmap) {
        if (bitmap != null) {
            measureBitmap(bitmap);
            return generateLedBitmap(bitmap);
        }
        return null;
    }

    /**
     * measure the text width and height
     * @param text text content
     */
    private void measureTextBound(String text) {
        Paint.FontMetrics m = paint.getFontMetrics();
        mDrawableWidth = (int) paint.measureText(text);
        mDrawableHeight = (int) (m.bottom - m.ascent);
    }

    /**
     * Transform text to bitmap
     * @param text text content
     * @param paint paint
     * @return the bitmap of text
     */
    private Bitmap renderText(CharSequence text, Paint paint) {
        Bitmap bitmap = Bitmap.createBitmap(mDrawableWidth, mDrawableHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(text.toString(),0,yPos,paint);
        return bitmap;
    }

    /**
     * Transform a bitmap to a led bitmap
     * @param src the original bitmap
     * @return led bitmap
     */
    private static Bitmap generateLedBitmap(Bitmap src) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        for (Point point : circlePoint) {
            // Detect if the px is in range of our led position
            int color = isInRange(src, point.x, point.y);
            if (color != 0) {
                if (ledColor != 0) {
                    color = ledColor;
                }
                paint.setColor(color);

                // draw shape according to ledType
                if (LED_TYPE_CIRCLE.equals(ledType)) {
                    canvas.drawCircle(point.x, point.y, ledRadius, paint);
                } else if (LED_TYPE_SQUARE.equals(ledType)) {
                    canvas.drawRect(point.x - ledRadius, point.y - ledRadius, point.x + ledRadius, point.y + ledRadius, paint);
                }
            }
        }
        return bitmap;
    }

    public void release() {
        circlePoint.clear();
    }


    private static void measureBitmap(Bitmap bitmap) {
        measurePoint(bitmap.getWidth(), bitmap.getHeight());
    }


    /**
     * Calculate the led point position
     */
    private static void measurePoint(int width, int height) {
        int halfBound = ledRadius + ledSpace / 2;
        int x = halfBound;
        int y = halfBound;
        for (; ; ) {
            for (; ; ) {
                circlePoint.add(new Point(x, y));
                y += halfBound * 2;
                if (y > height) {
                    y = halfBound;
                    break;
                }
            }
            x += halfBound * 2;
            if (x > width) {
                break;
            }
        }
    }

    private static int isInCircleLeft(Bitmap bitmap, int x, int y) {
        if (x - ledRadius > 0 && x - ledRadius < bitmap.getWidth()
                && y > 0 && y < bitmap.getHeight()) {
            int pxL = bitmap.getPixel(x - ledRadius, y);
            if (pxL != 0){
            	return pxL;
            }
        }
        return 0;
    }

    private static int isInCircleTop(Bitmap bitmap, int x, int y) {
        if (y - ledRadius > 0 && y - ledRadius < bitmap.getHeight()
                && x > 0 && x < bitmap.getWidth()) {
            int pxT = bitmap.getPixel(x, y - ledRadius);
            if (pxT != 0){
            	return pxT;
            }
        }
        return 0;
    }

    private static int isInCircleRight(Bitmap bitmap, int x, int y) {
        if (x + ledRadius > 0 && x + ledRadius < bitmap.getWidth()
                && y > 0 && y < bitmap.getHeight()) {
            int pxR = bitmap.getPixel(x + ledRadius, y);
            if (pxR != 0){
            	return pxR;
            }
        }
        return 0;
    }


    private static int isInCircleBottom(Bitmap bitmap, int x, int y) {
        if (y + ledRadius > 0 && y + ledRadius < bitmap.getHeight()
                && x > 0 && x < bitmap.getWidth()) {
            int pxB = bitmap.getPixel(x, y + ledRadius);
            if (pxB != 0){
            	return pxB;
            }
               
        }
        return 0;
    }

    private static int isInCircleCenter(Bitmap bitmap, int x, int y) {
        if (y > 0 && y < bitmap.getHeight()
                && x > 0 && x < bitmap.getWidth()) {
            int pxC = bitmap.getPixel(x, y);
            if (pxC != 0){
            	return pxC;
            }
        }
        return 0;
    }

    /**
     * Measure if x and y is in range of leds
     * @param bitmap the origin bitmap
     * @param x led x
     * @param y led y
     * @return the color , if color is zero means empty
     */
    private static int isInRange(Bitmap bitmap, int x, int y) {
        if (bitmap == null){
        	return 0;
        }
        int pxL = isInCircleLeft(bitmap, x, y);
        int pxT = isInCircleTop(bitmap, x, y);
        int pxR = isInCircleRight(bitmap, x, y);
        int pxB = isInCircleBottom(bitmap, x, y);
        int pxC = isInCircleCenter(bitmap, x, y);

        int num = 0;
        if (pxL != 0) {
            num++;
        }
        if (pxT != 0) {
            num++;
        }
        if (pxR != 0) {
            num++;
        }
        if (pxB != 0) {
            num++;
        }
        if (pxC != 0) {
            num++;
        }
        if (num >= 2) {
            int a = Color.alpha(pxL) + Color.alpha(pxT) + Color.alpha(pxR) + Color.alpha(pxB) + Color.alpha(pxC);
            int r = Color.red(pxL) + Color.red(pxT) + Color.red(pxR) + Color.red(pxB) + Color.red(pxC);
            int g = Color.green(pxL) + Color.green(pxT) + Color.green(pxR) + Color.green(pxB) + Color.green(pxC);
            int b = Color.blue(pxL) + Color.blue(pxT) + Color.blue(pxR) + Color.blue(pxB) + Color.blue(pxC);
            return Color.argb(a / 5, r / 5, g / 5, b / 5);
        }

        return 0;
    }

    public int getLedSpace() {
        return ledSpace;
    }

    public int getLedRadius() {
        return ledRadius;
    }

    public int getLedColor() {
        return ledColor;
    }

    public int getLedTextSize() {
        return ledTextSize;
    }

    public void setLedTextSize(int ledTextSize) {
        if(ledText == null)
            throw new NullPointerException("Please set ledText before setLedTextSize");
        this.ledTextSize = ledTextSize;
        measureTextBound(ledText.toString());
        paint.setTextSize(ledTextSize);
    }

    public String getLedType() {
        return ledType;
    }

    public Drawable getLedLightDrawable() {
        return customLedLightDrawable;
    }

    public void setLedLightDrawable(Drawable ledLightDrawable) {
        this.customLedLightDrawable = ledLightDrawable;
    }

    public CharSequence getLedText() {
        return ledText;
    }

    public void setLedText(CharSequence ledText) {
        this.ledText = ledText;
    }
}