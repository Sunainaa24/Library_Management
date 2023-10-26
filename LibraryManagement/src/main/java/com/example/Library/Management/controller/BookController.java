package com.example.Library.Management.controller;
import com.example.Library.Management.model.Book;
import com.example.Library.Management.model.User;
import com.example.Library.Management.repository.BookRepository;
import com.example.Library.Management.service.BookService;
import com.example.Library.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@Controller

//@RequestMapping("/app/library")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/home")
    public String home(Model model){return "homepage";}

    @GetMapping("/user/home")
    public String homeUser(Model model){return "homepageUser";}

    @GetMapping("/admin/list-books")
    public String getAllBooks(Model model) {
        List<Book> books=bookService.findAll();
        model.addAttribute("book",books);
        return "fetchBook";
    }
    @GetMapping("/user/list-books")
    public String getAllBooksUser(Model model) {
        List<Book> books=bookService.findAll();
        model.addAttribute("book",books);
        return "fetchBookUser";
    }
//    @GetMapping("/admin/add")
//    public String addBookUrl(Model model) {
//        Book book=new Book();
//        model.addAttribute("book",book);
//        return "newBook";
//    }

    @GetMapping("/admin/add")
    public String addBookUrl(Model model) {
        List<User> users=userService.getAllUsers();
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("users",users);
        model.addAttribute("user", new User()); // Add an empty User object to resolve the binding error
        return "newBook";
    }

//    @PostMapping("/admin/addBook")
//    public String addBook(@ModelAttribute("book") Book book ) {
//        bookService.save(book);
//        return "redirect:/admin/list-books";
//    }

    @PostMapping("/admin/addBook")
    public String addBook(@ModelAttribute("book") Book book, @RequestParam("selectedUserId") Long selectedUserId) {
        User user = userService.findById(selectedUserId); // Retrieve the User based on the selected user's ID
        book.setUser(user); // Set the User in the Book entity
        bookService.save(book);
        return "redirect:/admin/list-books";
    }


//    @GetMapping("/admin/update/{id}")
//    public String updatePage(@PathVariable("id") Long id, Model model) {
//       Book books = bookRepository.findById(id).get();
//            model.addAttribute("book", books);
//            return "updateBook";
//    }

    @GetMapping("/admin/update/{id}")
    public String updatePage(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id).get();
        List<User> users = userService.getAllUsers(); // Retrieve the list of users
        model.addAttribute("book", book);
        model.addAttribute("users", users); // Add the 'users' attribute to the model
        return "updateBook";
    }


//    @PostMapping("/admin/update")
//    public String updateBook(@ModelAttribute("book") Book book) {
//        System.out.println(book);
//        bookService.update(book);
//        return "redirect:/admin/list-books";
//    }
@PostMapping("/admin/update")
public String updateBook(@ModelAttribute("book") Book book, @RequestParam("selectedUserId") Long selectedUserId) {
    User user = userService.findById(selectedUserId); // Retrieve the User based on the selected user's ID
    book.setUser(user); // Set the User in the Book entity
    bookService.update(book); // Update the book
    return "redirect:/admin/list-books";
}

    @RequestMapping("/admin/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/admin/list-books";
    }
}
