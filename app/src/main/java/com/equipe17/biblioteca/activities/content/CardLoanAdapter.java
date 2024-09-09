package com.equipe17.biblioteca.activities.content;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.equipe17.biblioteca.R;
import com.equipe17.biblioteca.book.Book;
import com.equipe17.biblioteca.utils.ImageTreatment;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class CardLoanAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;
    private final View[] views;
    private final Book[] books;
    private final Bitmap[] bitmaps;

    public CardLoanAdapter(Context context, Book[] books) {
        super(context, 0);
        this.books = books;
        this.bitmaps = new Bitmap[books.length];
        Resources resources = context.getResources();
        int roundPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.margin_4), resources.getDisplayMetrics());
        views = new View[books.length];
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < books.length; i++) {
            bitmaps[i] = ImageTreatment.roundImageCorners(books[i].cover, roundPixels);
        }
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (views[position] == null) {
            View view = inflater.inflate(R.layout.list_item_loan, null, false);
            ImageView bookCover = view.findViewById(R.id.imageBook);
            TextView textTitle = view.findViewById(R.id.textTitle);
            bookCover.setImageBitmap(bitmaps[position]);
            textTitle.setText(books[position].title);
            views[position] = view;
        }
        return views[position];

    }
}