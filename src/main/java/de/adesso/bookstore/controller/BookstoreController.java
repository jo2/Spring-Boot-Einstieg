package de.adesso.bookstore.controller;

import de.adesso.bookstore.entitites.Book;
import de.adesso.bookstore.services.BookstoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @Operation(summary = "Get all books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class))) })
    })
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookstoreService.getAllBooks();
    }

    @Operation(summary = "Get a book by its title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })
    })
    @GetMapping("/books/title/{title}")
    public Book getBookByTitle(@PathVariable(name = "title") String title) {
        return bookstoreService.getBookByTitle(title);
    }

    @Operation(summary = "Get a book by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })
    })
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable(name = "id") Long id) {
        return bookstoreService.findById(id);
    }

    @Operation(summary = "Add a book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })
    })
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookstoreService.addBook(book);
    }

    @Operation(summary = "Update a book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the book.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })
    })
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        return bookstoreService.updateBook(id, book);
    }

    @Operation(summary = "Delete a book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the book.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })
    })
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable(name = "id") Long id) {
        bookstoreService.removeBook(id);
    }
}
