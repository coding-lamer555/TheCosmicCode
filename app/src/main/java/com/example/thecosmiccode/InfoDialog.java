package com.example.thecosmiccode;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thecosmiccode.utils.AnimatedTextView;

public class InfoDialog {

    private Context context;
    private ViewGroup root;
    private Animation fadeInAnimation, fadeOutAnimation;

    public InfoDialog(Context context, ViewGroup root) {
        this.context = context;
        this.root = root;
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        inflate();
    }

    private void inflate() {
        final View addedView = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_info_dialog, root, false);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) addedView.getLayoutParams();
        params.topToTop = root.getId();
        params.bottomToBottom = root.getId();
        addedView.setLayoutParams(params);
        final ImageButton infoButton = root.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(null);
        final ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(null);

        ImageButton close = addedView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedView.startAnimation(fadeOutAnimation);
                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new InfoDialog(context, root);
                    }
                });
                settingsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SettingsDialog(context, root);
                    }
                });
                root.removeView(addedView);
            }
        });

        AnimatedTextView info = addedView.findViewById(R.id.info);
        info.setDuration(3000);
        info.setIsVisible(false);
        info.toggle();

        addedView.startAnimation(fadeInAnimation);
        root.addView(addedView);
    }
}
