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

    @GetMapping("/books/{title}")
    public Book getBookByTitle(@PathVariable(name = "title") String title) {
        return bookstoreService.getBookByTitle(title);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookstoreService.addBook(book);
    }

    @PutMapping("/books/{title}")
    public Book updateBook(@PathVariable(name = "title") String title, @RequestBody Book book) {
        return bookstoreService.updateBook(title, book);
    }

    @DeleteMapping("/books/{title}")
    public void deleteBook(@PathVariable(name = "title") String title) {
        bookstoreService.removeBook(title);
    }
}
