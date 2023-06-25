package com.transform.main.serviceimpl;

import com.transform.main.dao.ContactsRepository;
import com.transform.main.entity.Contacts;
import com.transform.main.service.ContactsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;

    @Autowired
    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public Contacts findById(Integer id) {
        Optional<Contacts> result = contactsRepository.findById(id);

        Contacts contact = null;

        if (result.isPresent()) {
            contact = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find contact id - " + id);
        }

        return contact;
    }

    @Override
    public List<Contacts> findAll() {
        return contactsRepository.findAll();
    }

    @Override
    @Transactional
    public Contacts save(Contacts contacts) {
        return contactsRepository.save(contacts);
    }

    @Override
    @Transactional
    public Contacts update(Contacts contacts) {
        return contactsRepository.save(contacts);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        contactsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        contactsRepository.deleteAll();
    }

    @Override
    public Contacts findByAccessName(String accessName) {
        return contactsRepository.findByAccessName(accessName);
    }

    @Override
    public Contacts findByAccessEmail(String accessEmail) {
        return contactsRepository.findByAccessEmail(accessEmail);
    }
}
