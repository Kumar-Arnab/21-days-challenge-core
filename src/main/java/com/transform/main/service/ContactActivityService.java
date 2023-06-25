package com.transform.main.service;

import com.transform.main.entity.ContactActivity;
import java.util.List;

public interface ContactActivityService {

    ContactActivity findById(Integer id);

    List<ContactActivity> findAll();

    ContactActivity save(ContactActivity contactActivity);

    ContactActivity findByContactId(Integer contactId);
}
