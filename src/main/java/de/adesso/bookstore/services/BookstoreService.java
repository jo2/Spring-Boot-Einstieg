package de.adesso.bookstore.services;

import de.adesso.bookstore.entitites.Book;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookstoreService {

    private List<Book> books;

    @PostConstruct
    public void inti() {
        books = new ArrayList<>();
        addBook(new Book("Book_1", "Author_1", 5.0, 2018));
        addBook(new Book("Book_2", "Author_1", 10.0, 2017));
        addBook(new Book("Book_3", "Author_1", 15.0, 2018));
        addBook(new Book("Book_4", "Author_2", 20.0, 2019));
    }

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    public Book updateBook(String title, Book book) {
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                b.setAuthor(book.getAuthor());
                b.setPrice(book.getPrice());
                b.setYear(book.getYear());
                return book;
            }
        }
        return null;
    }

    public Book getBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }

    public void removeBook(String title) {
        for (Book b : new ArrayList<>(books)) {
            if (b.getTitle().equals(title)) {
                books.remove(b);
                return;
            }
        }
    }

    public List<Book> getAllBooks() {
        return books;
    }
}
