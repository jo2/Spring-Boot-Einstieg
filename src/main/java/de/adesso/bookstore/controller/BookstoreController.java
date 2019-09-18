package de.adesso.bookstore.controller;

import de.adesso.bookstore.entitites.Book;
import de.adesso.bookstore.services.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookstoreService.getAllBooks();
    }

    @GetMapping("/books/title/{title}")
    public Book getBookByTitle(@PathVariable(name = "title") String title) {
        return bookstoreService.getBookByTitle(title);
    }

    @GetMapping("/books/{id}")
    public Book getBookByTitle(@PathVariable(name = "id") Long id) {
        return bookstoreService.findById(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookstoreService.addBook(book);
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {
        return bookstoreService.updateBook(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable(name = "id") Long id) {
        bookstoreService.removeBook(id);
    }
}
