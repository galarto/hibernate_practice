package com.galarto.training.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import com.galarto.training.entity.Genre;
import com.galarto.training.repository.BookRepository;
import com.galarto.training.repository.CustomerRepository;
import org.joda.money.BigMoney;

import java.util.List;
import java.util.stream.Collectors;


public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public CustomerService(CustomerRepository customerRepository, BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public String logIn(String email) {
        if(customerRepository.getCustomer(email) == null){
            return createExceptionJSON(new Exception("User does not exist"));
            //Здесь должен быть json : неудачная операция - нужно зарегистрировать
        } else {
            customerRepository.getCustomer(email);
            return createSuccessfulJSON();
        }
    }

    public String getUserBooks(Customer customer) throws JsonProcessingException {
        List<Book> customerBooks = customerRepository.getCustomer(customer.getId()).getBookList();
        return mapToJSON(customerBooks);
    }

    public String getBooks() throws JsonProcessingException {
        List<Book> books = bookRepository.getAvailableBooks();
        return mapToJSON(books);
    }

    public String filter(Author author) throws JsonProcessingException {
        List<Book> books = bookRepository.getAvailableBooks().stream()
                        .filter(book -> book.getAuthor().equals(author)).collect(Collectors.toList());
        return mapToJSON(books);
    }

    public String filter(Genre genre) throws JsonProcessingException {
        List<Book> books = bookRepository.getBooks(genre);
        return mapToJSON(books);
    }

    public String filter(BigMoney price) throws JsonProcessingException {
        List<Book> books = bookRepository.getAvailableBooks().stream()
                        .filter(book -> book.getPrice().getAmount().compareTo(price.getAmount()) <= 0)
                        .collect(Collectors.toList());
        return mapToJSON(books);
    }

    public String signUp(int id, String name, String surname, BigMoney balance, String email) {
        if(customerRepository.getCustomer(id) == null) {
            customerRepository.saveCustomer(new Customer(name, surname, balance, email));
        } else {
            logIn(email);
        }
        return createSuccessfulJSON();
    }

    public String buyBook(int customerId, int bookId) {
        if (customerRepository.getCustomer(customerId) == null) {
        // неудачно - отправить на регистрацию
            return createExceptionJSON(new Exception("move to sign up first"));
        } else {
            customerRepository.getCustomer(customerId)
                    .setBalance(customerRepository.getCustomer(customerId).getBalance()
                    .minus(bookRepository.getBook(bookId).getPrice()));
            customerRepository.getCustomer(customerId).getBookList().add(bookRepository.getBook(bookId));
            bookRepository.getBook(bookId).setCount(bookRepository.getBook(bookId).getCount() - 1);
            return createSuccessfulJSON();
            // уменьшаем баланс кастомера, (добавить деньги на баланс магазина в мэйне через админский сервис),
            // добавляем книгу в список кастомера,
            // уменьшаем количество
            // книг на складе
            // удачно - покупка
        }
    }

    public String mapToJSON(Object o) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(o);
    }

    private String createSuccessfulJSON() {
        return "{" +
                " STATUS  :   SUCCESSFUL"  +
                "}";
    }

    private String createExceptionJSON(Exception e) {
        return "{" +
                "  STATUS  " + e.getMessage() +
                "}";
    }

}
