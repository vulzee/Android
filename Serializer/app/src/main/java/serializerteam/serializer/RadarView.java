package serializerteam.serializer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
    private final int POINT_ARRAY_SIZE = 25;
    private final int FPS = 100;

    float alpha = 0;
    Point latestPoint[] = new Point[POINT_ARRAY_SIZE];
    Paint latestPaint[] = new Paint[POINT_ARRAY_SIZE];

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Paint localPaint = new Paint();
        localPaint.setColor(Color.GREEN);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1.0F);
        localPaint.setAlpha(0);

        int alpha_step = 255 / POINT_ARRAY_SIZE;
        for (int i = 0; i < latestPaint.length; i++) {
            latestPaint[i] = new Paint(localPaint);
            latestPaint[i].setAlpha(255 - (i * alpha_step));
        }

        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }


    android.os.Handler mHandler = new android.os.Handler();
    Runnable mTick = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 1000 / FPS);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int r = Math.min(width, height) / 2;

        int j = r - 1;
        Paint localPaint = latestPaint[0];

        canvas.drawCircle(r, r, j, localPaint);
        canvas.drawCircle(r, r, j * 3 / 4, localPaint);
        canvas.drawCircle(r, r, j >> 1, localPaint);
        canvas.drawCircle(r, r, j >> 2, localPaint);

        alpha -= 0.5;
        if (alpha < -360) alpha = 0;
        double angle = Math.toRadians(alpha);
        int offsetX = (int) (r + (float) (r * Math.cos(angle)));
        int offsetY = (int) (r - (float) (r * Math.sin(angle)));

        latestPoint[0] = new Point(offsetX, offsetY);

        System.arraycopy(latestPoint, 0, latestPoint, 1, POINT_ARRAY_SIZE - 1);

        for (int x = 0; x < POINT_ARRAY_SIZE; x++) {
            Point point = latestPoint[x];
            if (point != null) {
                canvas.drawLine(r, r, point.x, point.y, latestPaint[x]);
            }
        }
    }
}
