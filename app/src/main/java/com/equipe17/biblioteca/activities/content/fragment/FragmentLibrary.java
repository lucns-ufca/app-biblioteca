package com.equipe17.biblioteca.activities.content.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.slider.FragmentView;

public class FragmentLibrary extends FragmentView {

    private PopupMenu popupMenu;

    public FragmentLibrary(Activity activity) {
        super(activity);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_library);

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(R.string.library);

    }
}