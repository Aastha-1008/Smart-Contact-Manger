package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;


@Repository
public interface ContactRepo extends JpaRepository<Contact,String>  {

    Page<Contact> findByUser(User user,Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(String userId);

    Page<Contact> findByNameContaining(String nameKeyword,Pageable pageable);
    Page<Contact> findByEmailContaining(String emailkeyword, Pageable pageable);
    Page<Contact> findByPhoneContaining(String phoneKeyword, Pageable pageable);

}
