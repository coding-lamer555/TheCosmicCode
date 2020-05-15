package com.example.thecosmiccode;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

import static com.example.thecosmiccode.WelcomeActivity.player;

public class SettingsDialog {

    private Context context;
    private ViewGroup root;
    private Animation fadeInAnimation, fadeOutAnimation;

    public SettingsDialog(Context context, ViewGroup root) {
        this.context = context;
        this.root = root;
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        inflate();
    }

    private void inflate() {
        final View addedView = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_settings_dialog, root, false);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) addedView.getLayoutParams();
        params.topToTop = root.getId();
        params.bottomToBottom = root.getId();
        addedView.setLayoutParams(params);
        final ImageButton infoButton = root.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(null);
        final ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(null);

        final Spinner userSpinner = addedView.findViewById(R.id.userSpinner);
        ArrayList<String> usersList = new ArrayList<>();
        final String newUser = context.getResources().getString(R.string.new_user);
        usersList.add(newUser);
        int currentUserSpinnerIndex = 0;
        ArrayList<Voyage> list = WelcomeActivity.dbConnector.selectAll();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getUser();
                if (usersList.indexOf(name) == -1) {
                    usersList.add(name);
                    if (name.equals(WelcomeActivity.currentUser)) {
                        currentUserSpinnerIndex = usersList.indexOf(name);
                    }
                }
            }
        }
        String[] users = usersList.toArray(new String[0]);
        SpinnerAdapter userAdapter = new SpinnerAdapter(context, R.layout.spinner_item, users);
        userSpinner.setAdapter(userAdapter);
        userSpinner.setSelection(currentUserSpinnerIndex, true);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = userSpinner.getSelectedItem().toString();
                if (selected.equals(newUser)) {
                    WelcomeActivity.currentUser = null;
                    if (userSpinner.getCount() > 1) {
                        Toast.makeText(context, R.string.create_new_user, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.no_user, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    WelcomeActivity.currentUser = selected;
                    Toast.makeText(context, context.getResources().getString(R.string.chosen_user) + " " + WelcomeActivity.currentUser, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button dropDB = addedView.findViewById(R.id.dropDB);
        dropDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity.dbConnector.deleteAll();
                WelcomeActivity.currentUser = null;
                userSpinner.setAdapter(new SpinnerAdapter(context, R.layout.spinner_item, new String[]{newUser}));
                Toast.makeText(context, R.string.deleted_data, Toast.LENGTH_SHORT).show();
            }
        });

        Spinner musicSpinner = addedView.findViewById(R.id.musicSpinner);
        String[] musicTitles = new String[]{
                "beautiful_mess",
                "earthlings",
                "nights_out",
                "the_legend_about_death_god",
                "undertale_ruins",
                "words_from_a_book"
        };
        final int[] musicIds = new int[]{
                R.raw.beautiful_mess,
                R.raw.earthlings,
                R.raw.nights_out,
                R.raw.the_legend_about_death_god,
                R.raw.undertale_ruins,
                R.raw.words_from_a_book
        };
        SpinnerAdapter musicAdapter = new SpinnerAdapter(context, R.layout.spinner_item, musicTitles);
        musicSpinner.setAdapter(musicAdapter);
        musicSpinner.setSelection(WelcomeActivity.currentMusicSpinnerIndex, true);
        musicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WelcomeActivity.currentMusicSpinnerIndex = position;
                player.stop();
                player = MediaPlayer.create(context, musicIds[position]);
                player.setLooping(true);
                player.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CheckBox mute = addedView.findViewById(R.id.mute);
        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    player.pause();
                    Toast.makeText(context, ":(", Toast.LENGTH_SHORT).show();
                } else {
                    player.start();
                    Toast.makeText(context, ":)", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        ImageButton quit = addedView.findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });

        addedView.startAnimation(fadeInAnimation);
        root.addView(addedView);
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {

        private String[] objects;

        public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = ((Activity) context).getLayoutInflater().inflate(R.layout.spinner_item, parent, false);
            TextView label = row.findViewById(R.id.spinnerText);
            label.setText(objects[position]);
            return row;
        }
    }
}
