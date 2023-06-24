package com.transform.main.dao;

import com.transform.main.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Integer> {
    Contacts findByAccessName(String accessName);

    Contacts findByAccessEmail(String accessEmail);
}
