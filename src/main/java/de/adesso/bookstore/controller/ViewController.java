package de.adesso.bookstore.controller;

import de.adesso.bookstore.entitites.Book;
import de.adesso.bookstore.services.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ViewController {

    private final BookstoreService bookstoreService;

    @Autowired
    public ViewController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping({"/", "/index", "/books"})
    public String getIndex(Model model) {
        model.addAttribute("books", bookstoreService.getAllBooks());
        return "index";
    }

    @GetMapping("/books/create")
    public String getCreateBook(Model model) {
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @GetMapping("/books/{id}/info")
    public String getBookInfo(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("book", bookstoreService.findById(id));
        return "bookInfo";
    }

    @GetMapping("/books/{id}/edit")
    public String getBookEdit(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("book", bookstoreService.findById(id));
        return "updateBook";
    }

    @PostMapping("/books/create")
    public String createBook(@Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createBook";
        }
        try {
            bookstoreService.addBook(book);
        } catch (DataIntegrityViolationException ex) {
            bindingResult.addError(new ObjectError("title", "Combination of title and author must be unique."));
            return "createBook";
        }
        return "redirect:/books";
    }

    @PostMapping("/books/update/{id}")
    public String editBook(@PathVariable(name = "id") Long id, @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateBook";
        }
        try {
            bookstoreService.updateBook(id, book);
        } catch (DataIntegrityViolationException ex) {
            bindingResult.addError(new ObjectError("title", "Combination of title and author must be unique."));
            return "updateBook";
        }
        return "redirect:/books";
    }

    @DeleteMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        bookstoreService.removeBook(id);
        return "redirect:/books";
    }
}
