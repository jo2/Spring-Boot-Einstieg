package de.adesso.bookstore.services;

import de.adesso.bookstore.entitites.Book;
import de.adesso.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class BookstoreService {

    private final BookRepository bookRepository;
    private final PaymentService paymentService;

    @Autowired
    public BookstoreService(BookRepository bookRepository, PaymentService paymentService) {
        this.bookRepository = bookRepository;
        this.paymentService = paymentService;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book b = optionalBook.get();
            b.setTitle(book.getTitle());
            b.setAuthor(book.getAuthor());
            b.setPrice(book.getPrice());
            b.setYear(book.getYear());
            return bookRepository.save(b);
        }
        return null;
    }

    public void buyBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            paymentService.pay(optionalBook.get().getPrice());
        } else {
            System.out.println("Book not Found");
        }
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
