package com.abdullojon.quizapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularScoreView extends View {
    private int correct = 8;
    private int total = 10;

    private Paint innerBgPaint;
    private Paint trackPaint;
    private Paint progressPaint;
    private Paint textPaint;

    private RectF arcBounds = new RectF();

    public CircularScoreView(Context context) {
        super(context);
        init();
    }

    public CircularScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        innerBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerBgPaint.setStyle(Paint.Style.FILL);

        trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trackPaint.setStyle(Paint.Style.STROKE);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
    }

    public void setScore(int correct, int total) {
        this.correct = correct;
        this.total = total > 0 ? total : 10;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width <= 0 || height <= 0) return;

        float strokeWidth = width * 0.10f;
        if (strokeWidth < 6f) strokeWidth = 6f;

        float padding = strokeWidth / 2f + 2f;
        arcBounds.set(padding, padding, width - padding, height - padding);

        int progressColor;
        int trackColor;
        int innerBgColor;
        int textColor;

        if (correct < 6) {
            // Red (< 6)
            progressColor = Color.parseColor("#EF4444");
            trackColor = Color.parseColor("#FEE2E2");
            innerBgColor = Color.parseColor("#FEF2F2");
            textColor = Color.parseColor("#991B1B");
        } else if (correct <= 8) {
            // Yellow (6..8)
            progressColor = Color.parseColor("#F59E0B");
            trackColor = Color.parseColor("#FEF3C7");
            innerBgColor = Color.parseColor("#FFFBEB");
            textColor = Color.parseColor("#92400E");
        } else {
            // Green (9..10)
            progressColor = Color.parseColor("#10B981");
            trackColor = Color.parseColor("#D1FAE5");
            innerBgColor = Color.parseColor("#ECFDF5");
            textColor = Color.parseColor("#065F46");
        }

        // 1. Draw Inner Circle Background
        innerBgPaint.setColor(innerBgColor);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f - strokeWidth, innerBgPaint);

        // 2. Draw Outer Track Ring
        trackPaint.setColor(trackColor);
        trackPaint.setStrokeWidth(strokeWidth);
        canvas.drawArc(arcBounds, 0, 360, false, trackPaint);

        // 3. Draw Outer Progress Arc
        float percentage = (float) correct / (float) total;
        if (percentage > 1.0f) percentage = 1.0f;
        float sweepAngle = 360f * percentage;

        if (sweepAngle > 0) {
            progressPaint.setColor(progressColor);
            progressPaint.setStrokeWidth(strokeWidth);
            canvas.drawArc(arcBounds, -90, sweepAngle, false, progressPaint);
        }

        // 4. Draw Center Text "correct/total"
        float textSize = width * 0.28f;
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

        String scoreText = correct + "/" + total;
        float textY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f);
        canvas.drawText(scoreText, width / 2f, textY, textPaint);
    }
}