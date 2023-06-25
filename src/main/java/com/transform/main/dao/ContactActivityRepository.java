package com.transform.main.dao;

import com.transform.main.entity.ContactActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactActivityRepository extends JpaRepository<ContactActivity, Integer> {

    ContactActivity findByContactId(Integer contactId);
}
