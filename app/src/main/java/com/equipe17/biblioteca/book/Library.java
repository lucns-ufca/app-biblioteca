package com.equipe17.biblioteca.book;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private String name;
    private List<Book> books;

    public Library(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public Book[] getBooks() {
        return books.toArray(new Book[0]);
    }

    public Book[] search(String text) {
        List<Book> selected = new ArrayList<>();
        for (Book book : books) if (book.title.contains(text)) selected.add(book);
        return selected.toArray(new Book[0]);
    }
}
