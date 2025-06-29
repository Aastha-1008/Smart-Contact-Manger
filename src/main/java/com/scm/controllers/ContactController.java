package com.scm.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);


    @Autowired
    private ContactService contactService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ImageService imageService;



    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result ,Authentication authentication, HttpSession session){

        if(result.hasErrors()){
            session.setAttribute("message", Message.builder().content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getUserByEmail(username);

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getProfileImage(),filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);
        contact.setProfileImage(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        contactService.save(contact);
        System.out.println(contactForm);

        session.setAttribute("message", 
            Message.builder()
            .content("You have sucessfully added a new contact")
            .type(MessageType.green)
            .build()
        );

        session.setAttribute("message", "You have sucessfully added a new contact");
        return "redirect:/user/contacts/add";

    }

    @RequestMapping
    public String ViewContacts(@RequestParam(value = "page" , defaultValue = "0") int page, @RequestParam(value = "size" , defaultValue = "10") int size, @RequestParam(value = "sortBy" , defaultValue = "name") String sortBy, @RequestParam(value= "direction" , defaultValue = "asc") String direction, Model model ,Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getUserByEmail(username);
        
        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);



        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(@RequestParam("field")String field, @RequestParam("keyword") String value ){
        return "user/search";
    }

}
