package com.equipe17.biblioteca.activities.start;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.others.CpfMask;
import com.equipe17.biblioteca.dialogs.DialogFinalMessage;
import com.equipe17.biblioteca.users.UserProcessStatus;
import com.equipe17.biblioteca.users.User;
import com.equipe17.biblioteca.users.UsersManager;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class NewAccountActivity extends Activity {

    private Button button;
    private EditText editTextUser, editTextPassword, editTextName, editTextPd;
    private Switch aSwitch;
    private RelativeLayout rootForm, rootLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setStatusBarColor(getColor(R.color.fragment_background));

        button = findViewById(R.id.buttonNewAccount);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        RelativeLayout rootEditTextPd = findViewById(R.id.rootEditTextPd);
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (Resources.getSystem().getDisplayMetrics().density * 40)); // 40dp
        editTextPd = (EditText) inflater.inflate(R.layout.edit_text_custom, null, false);
        editTextPd.setLayoutParams(params);
        editTextPd.setHint(R.string.pd_description);
        aSwitch = findViewById(R.id.aSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rootEditTextPd.addView(editTextPd);
                } else {
                    rootEditTextPd.removeAllViews();
                }
                updateButton();
            }
        });

        editTextUser.addTextChangedListener(new CpfMask(editTextUser, new CpfMask.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                updateButton();
            }
        }));
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("main_app_preferences", MODE_PRIVATE);
        DialogFinalMessage dialogFinalMessage = new DialogFinalMessage(this);
        UsersManager usersManager = UsersManager.getInstance(this);
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonLogin) {
                    startActivity(new Intent(NewAccountActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else if (v.getId() == R.id.buttonNewAccount) {
                    IBinder token;
                    if (sharedPreferences.contains("cpf")) {
                        token = editTextUser.getWindowToken();
                    } else {
                        token = editTextPassword.getWindowToken();
                    }
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
                    changeForm(true);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("cpf", editTextUser.getText().toString());
                            editor.apply();
                            User user;
                            if (aSwitch.isChecked()) {
                                user = new User(editTextName.getText().toString(), editTextUser.getText().toString(), editTextPassword.getText().toString(), editTextPd.getText().toString(), true);
                            } else {
                                user = new User(editTextName.getText().toString(), editTextUser.getText().toString(), editTextPassword.getText().toString());
                            }
                            UserProcessStatus userProcessStatus = usersManager.addUser(user);
                            switch (userProcessStatus) {
                                case OK:
                                    dialogFinalMessage.show(getString(R.string.user_registered), getString(R.string.login), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
                                        }
                                    });
                                    break;
                                case ALREADY_EXISTS:
                                    changeForm(false);
                                    dialogFinalMessage.show(getString(R.string.user_not_registered), getString(R.string.try_using_different));
                                    break;
                            }
                        }
                    }, 1000);
                }
            }
        };
        button.setOnClickListener(onClick);

        rootLoading = findViewById(R.id.rootLoading);
        rootForm = findViewById(R.id.rootForm);
    }

    private void updateButton() {
        button.setEnabled(((aSwitch.isChecked() && !editTextPd.getText().toString().isEmpty())) || !aSwitch.isChecked() && !editTextName.getText().toString().isEmpty() && editTextPassword.getText().length() >= UsersManager.PASSWORD_MINIMUM_LENGTH && editTextUser.getText().toString().length() == 14);
    }

    @Override
    public void onBackPressed() {
        if (isFinishing() || isDestroyed()) return;
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void changeForm(boolean hide) {
        rootForm.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
        rootLoading.setVisibility(hide ? View.VISIBLE : View.INVISIBLE);
    }
}
