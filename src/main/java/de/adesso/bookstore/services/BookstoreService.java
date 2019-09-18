package de.adesso.bookstore.services;

import de.adesso.bookstore.entitites.Book;
import de.adesso.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BookstoreService {

    private final BookRepository bookRepository;

    @Autowired
    public BookstoreService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
}
