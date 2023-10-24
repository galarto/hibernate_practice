package com.galarto.training.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.repository.AuthorRepository;
import com.galarto.training.repository.BookRepository;
import com.galarto.training.repository.CustomerRepository;
import org.joda.money.BigMoney;

import static sun.jvm.hotspot.debugger.win32.coff.DebugVC50X86RegisterEnums.STATUS;
import static sun.security.provider.certpath.OCSPResponse.ResponseStatus.SUCCESSFUL;

public class AdminService {
    ObjectMapper objectMapper = new ObjectMapper();
    BookRepository bookRepository = new BookRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    BigMoney balance;

    public AdminService(BookRepository bookRepository, CustomerRepository customerRepository
            , AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.authorRepository = authorRepository;
    }

    public BigMoney getBalance() {
        return balance;
    }

    public void setBalance(BigMoney balance) {
        this.balance = balance;
    }

    public String addAuthor(Author author) {
        authorRepository.saveAuthor(author);
        return createSuccessfulJSON();
    }

    public String addBook(Book book) {
        if(book.getAuthor() == null) {
            createExceptionJSON(new Exception("author does not exist"));
        }
        if (bookRepository.getBook(book.getId()) == null) {
            bookRepository.saveBook(book);
        } else {
            bookRepository.getBook(book.getId())
                    .setCount(bookRepository.getBook(book.getId()).getCount() + book.getCount());
        }
        return createSuccessfulJSON();
    }

    private String createSuccessfulJSON() {
        return "{" +
                '"' + STATUS + '"' + ':' + '"' + SUCCESSFUL + '"' +
                '}';
    }

    private String createExceptionJSON(Exception e) {
        return "{" +
                '"' + STATUS + '"' + ':' + '"' + e.getMessage() + '"' +
                '}';
    }
}

