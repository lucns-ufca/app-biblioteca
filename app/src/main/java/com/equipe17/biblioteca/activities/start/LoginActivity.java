package com.equipe17.biblioteca.activities.start;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.TabsActivity;
import com.equipe17.biblioteca.activities.others.CpfMask;
import com.equipe17.biblioteca.dialogs.DialogFinalMessage;
import com.equipe17.biblioteca.users.UserProcessStatus;
import com.equipe17.biblioteca.users.User;
import com.equipe17.biblioteca.users.UsersManager;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class LoginActivity extends Activity {

    private RelativeLayout rootForm, rootLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        EditText editTextUser = findViewById(R.id.editTextUser);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        SharedPreferences sharedPreferences = getSharedPreferences("main_app_preferences", MODE_PRIVATE);
        if (sharedPreferences.contains("cpf")) {
            editTextUser.setText(sharedPreferences.getString("cpf", ""));
        }

        rootLoading = findViewById(R.id.rootLoading);
        rootForm = findViewById(R.id.rootForm);
        RelativeLayout fragmentTop = findViewById(R.id.fragmentTop);
        fragmentTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fragmentTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                fragmentTop.setY(fragmentTop.getHeight() * (-1));
                fragmentTop.setVisibility(View.VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(fragmentTop, View.TRANSLATION_Y, fragmentTop.getHeight() * (-1), 0);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1000);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!editTextUser.getText().toString().isEmpty()) {
                            editTextPassword.requestFocus();
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(editTextPassword, 0);
                            return;
                        }
                        editTextUser.requestFocus();
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(editTextUser, 0);
                    }
                });
                animator.start();
            }
        });

        DialogFinalMessage dialogFinalMessage = new DialogFinalMessage(this);
        UsersManager usersManager = UsersManager.getInstance(this);
        View.OnClickListener onClick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonLogin) {
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
                            User user = new User(editTextUser.getText().toString(), editTextPassword.getText().toString());
                            UserProcessStatus userProcessStatus = usersManager.verifyPassword(user);
                            switch (userProcessStatus) {
                                case OK:
                                    usersManager.setLoggedUser(user);
                                    startActivity(new Intent(LoginActivity.this, TabsActivity.class));
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                    break;
                                case NOT_REGISTERED:
                                    changeForm(false);
                                    dialogFinalMessage.show(getString(R.string.user_not_registered), getString(R.string.try_create_account));
                                    break;
                                case WRONG_PASSWORD:
                                    changeForm(false);
                                    dialogFinalMessage.show(getString(R.string.wrong_password));
                                    break;
                                default: // INVALID
                                    changeForm(false);
                                    dialogFinalMessage.show(getString(R.string.invalid_data));
                                    break;
                            }
                        }
                    }, 2500);
                } else if (v.getId() == R.id.buttonNewAccount){
                    startActivity(new Intent(LoginActivity.this, NewAccountActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        };
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(onClick);
        Button buttonNewAccount = findViewById(R.id.buttonNewAccount);
        buttonNewAccount.setOnClickListener(onClick);
        TextView textName = findViewById(R.id.textName);
        editTextUser.addTextChangedListener(new CpfMask(editTextUser, new CpfMask.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                boolean isOk = text.length() == 14;
                if (isOk) {
                    String name = usersManager.getNameFromCpf(editTextUser.getText().toString());
                    if (name == null) textName.setVisibility(View.INVISIBLE);
                    else textName.setVisibility(View.INVISIBLE);
                    textName.setText(name);
                }
            }
        }));
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isOk = s.toString().length() >= UsersManager.PASSWORD_MINIMUM_LENGTH && editTextUser.getText().toString().length() == 14;
                buttonLogin.setEnabled(isOk);
                //buttonNewAccount.setEnabled(!isOk);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeForm(boolean hide) {
        rootForm.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
        rootLoading.setVisibility(hide ? View.VISIBLE : View.INVISIBLE);
    }
}
