package com.equipe17.biblioteca.activities.start;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.TabsActivity;
import com.equipe17.biblioteca.users.UsersManager;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView textTeamSevenTeen = findViewById(R.id.textTeamSevenTeen);
        ImageView ufcaLogo = findViewById(R.id.ufcaLogo);

        textTeamSevenTeen.setAlpha(0f);
        textTeamSevenTeen.setVisibility(View.VISIBLE);
        ufcaLogo.setAlpha(0f);
        ufcaLogo.setVisibility(View.VISIBLE);
        RelativeLayout relativeLayout = findViewById(R.id.banner);
        relativeLayout.setAlpha(0f);
        relativeLayout.setVisibility(View.VISIBLE);

        float halfHeight = Resources.getSystem().getDisplayMetrics().heightPixels / 2f;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(relativeLayout, View.TRANSLATION_Y, 0, (-1) * halfHeight + (halfHeight / 2f));
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();
            }
        }, 500);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(textTeamSevenTeen, View.ALPHA, 0f, 1f);
                alpha.setInterpolator(new LinearInterpolator());
                alpha.setDuration(1500);
                alpha.start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(textTeamSevenTeen, View.TRANSLATION_Y, 0, (-1) * (halfHeight / 10f));
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(2500);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                        UsersManager usersManager = UsersManager.getInstance(FirstActivity.this);
                        //if (usersManager.hasLoggedUser()) startActivity(new Intent(FirstActivity.this, MainActivity.class));
                        if (usersManager.hasLoggedUser()) startActivity(new Intent(FirstActivity.this, TabsActivity.class));
                        else  startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                });
                animator.start();
            }
        }, 1000);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(relativeLayout, View.ALPHA, 0f, 1f);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.setDuration(500);
        alpha.start();
        alpha = ObjectAnimator.ofFloat(ufcaLogo, View.ALPHA, 0f, 1f);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.setDuration(2500);
        alpha.start();
    }
}