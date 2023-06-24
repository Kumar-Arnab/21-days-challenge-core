package com.transform.main.service;

import com.transform.main.entity.Contacts;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ContactsService {

    Contacts findById(Integer id);

    List<Contacts> findAll();

    Contacts save(Contacts contacts);

    Contacts update(Contacts contacts);

    void deleteById(Integer id);

    void deleteAll();

    Contacts findByAccessName(String accessName);

    Contacts findByAccessEmail(String accessEmail);

}
