package com.equipe17.biblioteca.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.equipe17.biblioteca.R;

public class DialogFinalMessage extends Dialog {

    public DialogFinalMessage(Activity activity) {
        super(activity, R.style.DialogTheme);
        setCancelable(false);
    }

    public void show(String title) {
        setContentView(R.layout.dialog_message);
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(title);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        findViewById(R.id.buttonNegative).setOnClickListener(onClickListener);
        show();
    }

    public void show(String title, String buttonText, View.OnClickListener onButtonClick) {
        setContentView(R.layout.dialog_message);
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(title);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onButtonClick.onClick(v);
            }
        };
        Button button = findViewById(R.id.buttonNegative);
        button.setText(buttonText);
        button.setOnClickListener(onClickListener);
        show();
    }

    public void show(String title, String description) {
        setContentView(R.layout.dialog_message_with_description);
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(title);
        TextView textDescription = findViewById(R.id.textDescription);
        textDescription.setText(description);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        findViewById(R.id.buttonNegative).setOnClickListener(onClickListener);
        show();
    }
}
