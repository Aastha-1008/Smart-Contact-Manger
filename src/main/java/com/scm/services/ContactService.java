package com.scm.services;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {


    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    List<Contact> search(String name,String email,String phoneNumber);

    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page , int size,String sortBy,String direction);

    List<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String order);
    
    List<Contact> searchByEmail(String emailKeyword,int size,int page,String sortBy, String order);

    List<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy, String order);

}
