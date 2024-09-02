package com.equipe17.biblioteca.activities.content.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.activities.content.CardListAdapter;
import com.equipe17.biblioteca.activities.content.DebitsActivity;
import com.equipe17.biblioteca.activities.start.LoginActivity;
import com.equipe17.biblioteca.book.Library;
import com.equipe17.biblioteca.book.LibraryProvider;
import com.equipe17.biblioteca.book.OnLibraryAvailableListener;
import com.equipe17.biblioteca.slider.FragmentView;
import com.equipe17.biblioteca.users.User;
import com.equipe17.biblioteca.users.UsersManager;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class FragmentHome extends FragmentView {

    private PopupMenu popupMenu;

    public FragmentHome(Activity activity) {
        super(activity);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_home);

        UsersManager usersManager = UsersManager.getInstance(getContext());
        User user = usersManager.getLoggedUser();
        String name;
        if (user.name.contains(" ")) {
            String[] segments = user.name.split(" ");
            if (segments.length > 2) {
                name = segments[0] + " " + segments[1].charAt(0) + ". " + segments[1];
            } else {
                name = user.name;
            }
        } else name = user.name;
        TextView textUserName = findViewById(R.id.textUserName);
        textUserName.setText(name);

        ImageButton buttonMenu = findViewById(R.id.buttonUser);
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.buttonBack) {
                    finish();
                } else if (id == R.id.buttonUser) {
                    popupMenu.show();
                } else if (id == R.id.card) {
                    startActivity(new Intent(getActivity(), DebitsActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else if (id == R.id.card2) {
                    Toast.makeText(getContext(), "Card clicado 2 :D", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.card3) {
                    Toast.makeText(getContext(), "Card clicado 3 :D", Toast.LENGTH_SHORT).show();
                }
            }
        };
        findViewById(R.id.card).setOnClickListener(onClick);
        findViewById(R.id.card2).setOnClickListener(onClick);
        findViewById(R.id.card3).setOnClickListener(onClick);
        buttonMenu.setOnClickListener(onClick);
        popupMenu = new PopupMenu(getContext(), buttonMenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {
                    usersManager.setLoggedUser(null);
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else if (item.getItemId() == R.id.menu_another) {
                    Toast.makeText(getContext(), getString(R.string.menu_item_another), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, popupMenu.getMenu());

        ListView listView = findViewById(R.id.listView);
        LibraryProvider libraryProvider = LibraryProvider.getInstance(getContext());
        libraryProvider.readLibrary("books", new OnLibraryAvailableListener() {
            @Override
            public void onAvailable(Library library) {
                listView.setAdapter(new CardListAdapter(getContext(), library.getBooks()));
            }
        });
    }
}