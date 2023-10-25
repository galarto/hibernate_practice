package com.galarto.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import com.galarto.training.repository.AuthorRepository;
import com.galarto.training.repository.BookRepository;
import com.galarto.training.repository.CustomerRepository;
import com.galarto.training.services.AdminService;
import com.galarto.training.services.CustomerService;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        CustomerRepository customerRepository = new CustomerRepository();
        BookRepository bookRepository = new BookRepository();
        AuthorRepository authorRepository = new AuthorRepository();
        CustomerService customerService = new CustomerService(customerRepository, bookRepository);
        AdminService adminService = new AdminService(bookRepository, customerRepository, authorRepository);
        // json : { \"operation\" : \"addAuthor\", \"name\" : \"qqq\", \"surname\" : \"wwww\", \"patronymic\" : \"zzzzz\" }
        // {"operation" : "addAuthor", "Author" : {"name" : "aaa", "surname" : "ssss", "patronymic" : "ggg"}}
        Scanner scanner = new Scanner(System.in);
        ObjectMapper om = new ObjectMapper();
        while (true) {
            String json = scanner.nextLine();
            Map<String, Object> m1 = om.readValue(json, Map.class);
            System.out.println(m1.get("operation"));
            String op = (String) m1.get("operation");

            switch (op) {
                case "addAuthor":
                    Author author = om.readValue((om.writeValueAsString(m1.get("Author"))), Author.class);
                    adminService.addAuthor(author);
                    // AdminService.addAuthor метод
                    break;
                case "addBook":
                    Book book = om.readValue((om.writeValueAsString(m1.get("Book"))), Book.class);
                    adminService.addBook(book);
                    // AdminService.addBook, проверить существуют ли книга и автор -> вернуть результат
                    break;
                case "buyBook":
                    Book b = om.readValue((om.writeValueAsString(m1.get("Book"))), Book.class);
                    Customer customer = om.readValue((om.writeValueAsString(m1.get("Customer"))), Customer.class);
                    customerService.buyBook(b.getId(), customer.getId());
                    adminService.setBalance(adminService.getBalance().plus(b.getPrice()));
                    //CustomerService.buyBook
                    break;
                case "signUp":
                    Customer c = om.readValue((om.writeValueAsString(m1.get("Customer"))), Customer.class);
                    customerService.signUp(c.getId(), c.getName(), c.getSurname(), c.getBalance(), c.getEmail());
                    //CustomerService.signUp
                    break;
                case "logIn":
                    Customer c2 = om.readValue((om.writeValueAsString(m1.get("Customer"))), Customer.class);
                    customerService.logIn(c2.getEmail());
                    //CustomerService.logIn
                    break;
                default:
                    System.out.println("{JSON : wrong operation}");// json wrong operation
            }
        }

    }
}
