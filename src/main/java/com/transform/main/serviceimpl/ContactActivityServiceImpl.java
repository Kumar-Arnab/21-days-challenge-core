package com.transform.main.serviceimpl;

import com.transform.main.dao.ContactActivityRepository;
import com.transform.main.entity.ContactActivity;
import com.transform.main.entity.Contacts;
import com.transform.main.service.ContactActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactActivityServiceImpl implements ContactActivityService {

    private final ContactActivityRepository contactActivityRepository;

    @Autowired
    public ContactActivityServiceImpl(ContactActivityRepository contactActivityRepository) {
        this.contactActivityRepository = contactActivityRepository;
    }

    @Override
    public ContactActivity findById(Integer id) {
        Optional<ContactActivity> result = contactActivityRepository.findById(id);

        ContactActivity contactActivity = null;

        if (result.isPresent()) {
            contactActivity = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find contact activity id - " + id);
        }

        return contactActivity;
    }

    @Override
    public List<ContactActivity> findAll() {
        return contactActivityRepository.findAll();
    }

    @Override
    public ContactActivity save(ContactActivity contactActivity) {
        return contactActivityRepository.save(contactActivity);
    }

    @Override
    public ContactActivity findByContactId(Integer contactId) {
        return contactActivityRepository.findByContactId(contactId);
    }
}
