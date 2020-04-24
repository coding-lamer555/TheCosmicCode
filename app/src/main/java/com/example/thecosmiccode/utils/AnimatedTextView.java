package com.example.thecosmiccode.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;

public class AnimatedTextView extends androidx.appcompat.widget.AppCompatTextView {
    ValueAnimator animator;
    private String textString;
    private SpannableString spannableString;
    private double[] alphas;
    private MutableForegroundColorSpan[] spans;
    private boolean isVisible;
    private boolean isTextResetting = false;
    ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float percent = (Float) valueAnimator.getAnimatedValue();
            resetSpannableString(isVisible ? percent : 2.0f - percent);
        }
    };
    private int duration = 2500;

    public AnimatedTextView(Context context) {
        super(context);
        init();
    }

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.isVisible = false;
        animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.addUpdateListener(listener);
        animator.setDuration(duration);
    }

    public void toggle() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        isVisible = true;
        animator.start();
    }

    public void hide() {
        isVisible = false;
        animator.start();
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        resetSpannableString(isVisible ? 2.0f : 0.0f);
    }

    private void resetSpannableString(double percent) {
        isTextResetting = true;

        int color = getCurrentTextColor();
        for (int i = 0; i < this.textString.length(); i++) {
            MutableForegroundColorSpan span = spans[i];
            span.setColor(Color.argb(clamp(alphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color)));
        }

        setText(spannableString);

        isTextResetting = false;
    }

    private void resetAlphas(int length) {
        alphas = new double[length];
        for (int i = 0; i < alphas.length; i++) {
            alphas[i] = Math.random() - 1;
        }
    }

    private void resetIfNeeded() {
        if (!isTextResetting) {
            textString = getText().toString();
            spannableString = new SpannableString(this.textString);
            spans = new MutableForegroundColorSpan[this.textString.length()];
            for (int i = 0; i < this.textString.length(); i++) {
                MutableForegroundColorSpan span = new MutableForegroundColorSpan();
                spannableString.setSpan(span, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spans[i] = span;
            }
            resetAlphas(textString.length());
            resetSpannableString(isVisible ? 2.0f : 0);
        }
    }

    public void setText(String text) {
        super.setText(text);
        resetIfNeeded();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        resetIfNeeded();
    }

    private int clamp(double f) {
        return (int) (255 * Math.min(Math.max(f, 0), 1));
    }

    public void setDuration(int duration) {
        this.duration = duration;
        animator.setDuration(duration);
    }
}
