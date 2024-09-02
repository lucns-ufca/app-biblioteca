package com.equipe17.biblioteca.activities.content;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.fragment.FragmentHome;
import com.equipe17.biblioteca.activities.content.fragment.FragmentLibrary;
import com.equipe17.biblioteca.activities.content.fragment.FragmentProfile;
import com.equipe17.biblioteca.activities.content.fragment.FragmentSearch;
import com.equipe17.biblioteca.slider.SliderView;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class TabsActivity extends Activity {

    private SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sliderView = findViewById(R.id.sliderView);
        sliderView.disableScroll(true);
        sliderView.addFragment(new FragmentHome(TabsActivity.this));
        sliderView.addFragment(new FragmentLibrary(TabsActivity.this));
        sliderView.addFragment(new FragmentSearch(TabsActivity.this));
        sliderView.addFragment(new FragmentProfile(TabsActivity.this));
        sliderView.setOnSliderChangedListener(new SliderView.OnSliderChangedListener() {
            @Override
            public void onSlidePositionChanged(int index) {

            }
        });

        ImageButton tabOne = findViewById(R.id.tabOne);
        tabOne.setActivated(true);
        ImageButton tabTwo = findViewById(R.id.tabTwo);
        ImageButton tabThree = findViewById(R.id.tabThree);
        ImageButton tabFour = findViewById(R.id.tabFour);
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.tabOne) {
                    tabOne.setActivated(true);
                    tabTwo.setActivated(false);
                    tabThree.setActivated(false);
                    tabFour.setActivated(false);
                    sliderView.goToIndex(0, 300);
                } else if (v.getId() == R.id.tabTwo) {
                    tabOne.setActivated(false);
                    tabTwo.setActivated(true);
                    tabThree.setActivated(false);
                    tabFour.setActivated(false);
                    sliderView.goToIndex(1, 300);
                } else if (v.getId() == R.id.tabThree) {
                    tabOne.setActivated(false);
                    tabTwo.setActivated(false);
                    tabThree.setActivated(true);
                    tabFour.setActivated(false);
                    sliderView.goToIndex(2, 300);
                } else { // tabFour
                    tabOne.setActivated(false);
                    tabTwo.setActivated(false);
                    tabThree.setActivated(false);
                    tabFour.setActivated(true);
                    sliderView.goToIndex(3, 300);
                }
            }
        };
        tabOne.setOnClickListener(onClick);
        tabTwo.setOnClickListener(onClick);
        tabThree.setOnClickListener(onClick);
        tabFour.setOnClickListener(onClick);
    }
}
