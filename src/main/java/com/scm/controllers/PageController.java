package com.scm.controllers;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String homePage(Model model) {
        System.out.println("Home page");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        System.out.println("About the page");
        return "about";
    }

    @RequestMapping("/services")
    public String servicePage(Model model) {
        System.out.println("Services page");
        return "services";
    }

    @RequestMapping("/login")
    public String loginPage(Model model) {
        System.out.println("Login page");
        return "login";
    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        System.out.println("Contact page");
        return "contact";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult ,  HttpSession session) {
        System.out.println(userForm);

        if(rBindingResult.hasErrors()){
            return "register";
        }



        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setPassword(userForm.getPassword());
        user.setProfilePic(
                "https://www.pngkey.com/png/detail/115-1150152_default-profile-picture-avatar-png-green.png");

        User savedUser = userServices.saveUser(user);

        Message message = Message.builder()
                .content("Registration Successful")
                .type(MessageType.red) // returns "green"
                .build();

        session.setAttribute("message", message);

        System.out.println("user saved : ");

        return "redirect:/register";
    }
}
