package com.equipe17.biblioteca.activities.content.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.CardListAdapter;
import com.equipe17.biblioteca.book.Book;
import com.equipe17.biblioteca.book.Library;
import com.equipe17.biblioteca.book.LibraryProvider;
import com.equipe17.biblioteca.book.OnLibraryAvailableListener;
import com.equipe17.biblioteca.slider.FragmentView;

public class FragmentSearch extends FragmentView {

    private PopupMenu popupMenu;
    private String searchString;
    private Book[] books;

    public FragmentSearch(Activity activity) {
        super(activity);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_search);

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(R.string.search_book);

        ListView listView = findViewById(R.id.booksList);
        LibraryProvider libraryProvider = LibraryProvider.getInstance(getContext());

        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));

                EditText searchField = findViewById(R.id.SearchTerm);
                String searchText = searchField.getText().toString();

                //searchString = "O Senhor";
                libraryProvider.readLibrary("books", new OnLibraryAvailableListener() {
                    @Override
                    public void onAvailable(Library library) {
                        // Supõe-se que Library possui um método search que retorna um array de Book
                        listView.setAdapter(new CardListAdapter(getContext(), library.search(searchText)));
                    }
                });

                Toast.makeText(getActivity(), "Botão clicado :D", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
