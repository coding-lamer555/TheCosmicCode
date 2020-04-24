package com.example.thecosmiccode.utils;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class MutableForegroundColorSpan extends CharacterStyle implements UpdateAppearance {

    public static final String TAG = "MutableForegroundColorSpan";
    private int color;

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
