package com.example.thecosmiccode;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class SettingsDialog {
    private Context context;
    private ViewGroup root;
    private Animation fadeInAnimation, fadeOutAnimation;
    private String[] users;

    public SettingsDialog(Context context, ViewGroup root) {
        this.context = context;
        this.root = root;
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        inflate();
    }

    private void inflate() {
        final View addedView = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_settings, root, false);
        addedView.startAnimation(fadeInAnimation);
        final ImageButton infoButton = root.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(null);
        final ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(null);

        final Spinner spinner = addedView.findViewById(R.id.spinner);
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
                        currentUserSpinnerIndex = usersList.indexOf(name) + 1;
                    }
                }
            }
        }

        Button changeUser = addedView.findViewById(R.id.changeUser);
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = spinner.getSelectedItem().toString();
                if (selected.equals(newUser)) {
                    WelcomeActivity.currentUser = null;
                    if (spinner.getCount() > 1) {
                        Toast.makeText(context, "Будет создан новый пользователь", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Нет ни одного пользователя, будет создан новый", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    WelcomeActivity.currentUser = selected;
                    Toast.makeText(context, "Выбран пользователь: " + WelcomeActivity.currentUser, Toast.LENGTH_SHORT).show();
                }
            }
        });

        users = usersList.toArray(new String[0]);
        CustomAdapter adapter = new CustomAdapter(context, R.layout.spinner_item, users);
        spinner.setAdapter(adapter);
        spinner.setPromptId(R.string.change);
        spinner.setSelection(currentUserSpinnerIndex, true);

        Button dropDB = addedView.findViewById(R.id.dropDB);
        dropDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeActivity.dbConnector.deleteAll();
                WelcomeActivity.currentUser = null;
                spinner.setAdapter(new CustomAdapter(context, R.layout.spinner_item, new String[]{newUser}));
                Toast.makeText(context, "Данные всех пользователей удалены!", Toast.LENGTH_SHORT).show();
            }
        });

        CheckBox mute = addedView.findViewById(R.id.mute);
        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WelcomeActivity.player.pause();
                    Toast.makeText(context, ":(", Toast.LENGTH_SHORT).show();
                } else {
                    WelcomeActivity.player.start();
                    Toast.makeText(context, ":)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        root.addView(addedView);

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
    }

    public class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
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
            label.setText(users[position]);
            return row;
        }
    }
}
