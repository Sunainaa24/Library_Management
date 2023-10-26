package com.example.Library.Management.controller;
import com.example.Library.Management.model.User;
import com.example.Library.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/get-user")
    public String getAllUsers(Model model) {
        List<User> users=userService.getAllUsers();
        model.addAttribute("user",users);
        return "fetchUser";
    }
    @GetMapping("/user/get-user")
    public String getAllUsers1(Model model) {
        List<User> users=userService.getAllUsers();
        model.addAttribute("user",users);
        return "fetchUser_user";
    }

    @GetMapping("/user/adduser")
    public String addUser1(Model model) {
        User users=new User();
        model.addAttribute("user",users);
        return "createUser";
    }

    @PostMapping(path = "/user/adduser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/app/users/user/get-user";
    }

    @RequestMapping(path ="/admin/deleteUser/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/app/users/admin/get-user";
    }
}