package com.equipe17.biblioteca.activities.content.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.CardListAdapter;
import com.equipe17.biblioteca.activities.content.CardLoanAdapter;
import com.equipe17.biblioteca.book.Library;
import com.equipe17.biblioteca.book.LibraryProvider;
import com.equipe17.biblioteca.book.OnLibraryAvailableListener;
import com.equipe17.biblioteca.slider.FragmentView;

public class FragmentLoan extends FragmentView {



    public FragmentLoan(Activity activity) {
        super(activity);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_loan);

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(R.string.my_loan);
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setVisibility(View.INVISIBLE);
//
//        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
//        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
//                Toast.makeText(getActivity(), "Botão clicado :D", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        ListView listView = findViewById(R.id.listView);
        LibraryProvider libraryProvider = LibraryProvider.getInstance(getContext());
        libraryProvider.readLibrary("books", new OnLibraryAvailableListener() {
            @Override
            public void onAvailable(Library library) {
                listView.setAdapter(new CardLoanAdapter(getContext(), library.getBooks()));
            }
        });
    }
}